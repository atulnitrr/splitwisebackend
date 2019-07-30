package com.splitwise.splitwise.security;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.google.gson.Gson;
import com.splitwise.splitwise.AppConsts;
import com.splitwise.splitwise.exception.SplitwiseAppException;
import com.splitwise.splitwise.model.request.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
            final HttpServletResponse response)
            throws AuthenticationException {
        final Gson gson = new Gson();
        try {
            final LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail()
                    , loginRequest.getPassword()));
        } catch (IOException e) {
            throw new SplitwiseAppException("Could not parse login input");
        }
    }



    @Override protected void successfulAuthentication(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        final String email = ((User)authResult.getPrincipal()).getUsername();
        final String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConsts.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, AppConsts.TOKEN_SEC)
                .compact();
        response.addHeader(AppConsts.HEADER_PREFIX, AppConsts.TOKEN_PREFIX + token);
        LOGGER.info("Successfully authenticated user {}" , email);

    }
}
