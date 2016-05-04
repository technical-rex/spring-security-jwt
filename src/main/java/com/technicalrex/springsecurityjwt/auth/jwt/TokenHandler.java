package com.technicalrex.springsecurityjwt.auth.jwt;

import com.google.common.base.Preconditions;
import com.technicalrex.springsecurityjwt.support.validation.StringConditions;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class TokenHandler {

    private final String secret;
    private final UserService userService;

    public TokenHandler(String secret, UserService userService) {
        this.secret = Base64.getEncoder().encodeToString(StringConditions.checkNotBlank(secret).getBytes());
        this.userService = Preconditions.checkNotNull(userService);
    }

    public User parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.loadUserByUsername(username);
    }

    public String createTokenForUser(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1l));
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
