package com.technicalrex.springsecurityjwt.auth.google;

import com.technicalrex.springsecurityjwt.auth.XsrfUtils;
import com.technicalrex.springsecurityjwt.config.AppConfig;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

public class GoogleAuthorizationRequestServlet extends HttpServlet {
    private AppConfig appConfig;
    private XsrfUtils xsrfUtils;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext applicationContext = getRequiredWebApplicationContext(getServletContext());
        appConfig = applicationContext.getBean(AppConfig.class);
        xsrfUtils = applicationContext.getBean(XsrfUtils.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String state = xsrfUtils.newToken();
        request.getSession().setAttribute(XsrfUtils.XSRF_KEY, state);

        // todo https://developers.google.com/accounts/docs/OpenIDConnect#discovery
        String location = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + appConfig.getGoogleClientId()
                + "&response_type=code"
                + "&scope=openid%20email"
                + "&redirect_uri=" + request.getHeader("Referer") + "auth/google/response"
                + "&state=" + state;

        response.sendRedirect(location);
    }
}
