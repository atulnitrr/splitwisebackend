package com.splitwise.splitwise.security;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.splitwise.splitwise.AppConsts;
import io.jsonwebtoken.Jwts;


public class AuthorizeFilter extends BasicAuthenticationFilter {


    public AuthorizeFilter(
            final AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        final String header = request.getHeader(AppConsts.HEADER_PREFIX);

        if (header == null || !header.startsWith(AppConsts.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        final UsernamePasswordAuthenticationToken token  = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);

        doFilter(request, response, chain);

    }

    private UsernamePasswordAuthenticationToken getToken(final String header) {


        final String token = header.replace(AppConsts.TOKEN_PREFIX, "");
        if (StringUtils.isBlank(token)) {
            return null;
        }

        final String userName = Jwts
                .parser()
                .setSigningKey(AppConsts.TOKEN_SEC)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return new UsernamePasswordAuthenticationToken(userName, "", Collections.emptyList());
    }
}
