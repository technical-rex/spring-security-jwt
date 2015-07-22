package com.technicalrex.springsecurityjwt.auth.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Sets;
import com.technicalrex.springsecurityjwt.auth.XsrfUtils;
import com.technicalrex.springsecurityjwt.auth.jwt.TokenAuthenticationService;
import com.technicalrex.springsecurityjwt.auth.jwt.UserAuthentication;
import com.technicalrex.springsecurityjwt.auth.jwt.UserService;
import com.technicalrex.springsecurityjwt.config.AppConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

public class GoogleAuthorizationResponseServlet extends HttpServlet {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final String ERROR_URL_PARAM_NAME = "error";
    private static final String CODE_URL_PARAM_NAME = "code";
    public static final String URL_MAPPING = "/auth/google/response";

    private AppConfig appConfig;
    private XsrfUtils xsrfUtils;
    private UserService userService;
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext applicationContext = getRequiredWebApplicationContext(getServletContext());
        appConfig = applicationContext.getBean(AppConfig.class);
        xsrfUtils = applicationContext.getBean(XsrfUtils.class);
        userService = applicationContext.getBean(UserService.class);
        tokenAuthenticationService = applicationContext.getBean(TokenAuthenticationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for a valid XSRF token
        String expectedToken = (String) request.getSession().getAttribute(XsrfUtils.XSRF_KEY);
        String actualToken = request.getParameter("state");
        if (!xsrfUtils.isValid(expectedToken, actualToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Check for no errors in the OAuth process
        String[] error = request.getParameterValues(ERROR_URL_PARAM_NAME);
        if (error != null && error.length > 0) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        // Check for the presence of the response code
        String[] code = request.getParameterValues(CODE_URL_PARAM_NAME);
        if (code == null || code.length == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Get the email address
        String requestUrl = getOAuthCodeCallbackHandlerUrl(request);
        GoogleTokenResponse tokenResponse = exchangeCodeForAccessAndRefreshTokens(code[0], requestUrl);
        String email = tokenResponse.parseIdToken().getPayload().getEmail();

        String token = establishUserAndLogin(response, email);

        request.setAttribute("email", email);
        request.setAttribute("authToken", token);
        getServletConfig().getServletContext().getRequestDispatcher("/home.jsp").forward(request,response);
    }

    private GoogleTokenResponse exchangeCodeForAccessAndRefreshTokens(String code, String currentUrl) throws IOException {
        return new GoogleAuthorizationCodeTokenRequest(HTTP_TRANSPORT, JSON_FACTORY, appConfig.getGoogleClientId(),
                appConfig.getGoogleClientSecret(), code, currentUrl).execute();
    }

    private static String getOAuthCodeCallbackHandlerUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        String servletPath = URL_MAPPING;
        String pathInfo = request.getPathInfo() == null ? "" : request.getPathInfo();
        return scheme + serverName + serverPort + contextPath + servletPath + pathInfo;
    }

    private String establishUserAndLogin(HttpServletResponse response, String email) {
        // Find user, create if necessary
        User user;
        try {
            user = userService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            user = new User(email, UUID.randomUUID().toString(), Sets.<GrantedAuthority>newHashSet());
            userService.addUser(user);
        }

        // Login that user
        UserAuthentication authentication = new UserAuthentication(user);
        return tokenAuthenticationService.addAuthentication(response, authentication);
    }
}
