package com.splitwise.splitwise.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.google.gson.Gson;
import com.splitwise.splitwise.exception.SplitwiseAppException;
import com.splitwise.splitwise.model.request.LoginRequest;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

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
            validateLoginRequest(gson.fromJson(request.getReader(), LoginRequest.class));
        } catch (IOException e) {
            System.out.println("fff");
            throw new SplitwiseAppException("Could not parse login input");
        }
        System.out.println("tttt");
        return super.attemptAuthentication(request, response);
    }

    private void validateLoginRequest(final LoginRequest loginRequest) {
        final String email = loginRequest.getEmail();
        final String password = loginRequest.getPassword();
        if (StringUtils.isNotBlank(email) || StringUtils.isNotBlank(password)) {
            throw new SplitwiseAppException("email or password should be not empty ");
        }
    }

    @Override protected void successfulAuthentication(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
