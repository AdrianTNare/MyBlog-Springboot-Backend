package com.sir_ad.myBlog_backend.security;

import com.sir_ad.myBlog_backend.config.SecurityConstants;
import com.sir_ad.myBlog_backend.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityConstants securityConstants;

    @Autowired
    public AuthorizationFilter(AuthenticationManager authManager, ApplicationContext ctx) {
        super(authManager);
        this.securityConstants = ctx.getBean(SecurityConstants.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            String header = req.getHeader(securityConstants.getHeaderName());
            if (header == null) {
                chain.doFilter(req, res);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = authenticate(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (IOException e) {
            System.out.println(e);

        }
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest req) {
        String token = req.getHeader(securityConstants.getHeaderName());
        if (token != null) {
            Claims user = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(securityConstants.getAuthKey().getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            } else {
                return null;
            }
        }
        return null;
    }
}
