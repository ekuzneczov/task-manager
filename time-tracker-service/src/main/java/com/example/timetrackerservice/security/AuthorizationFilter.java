package com.example.timetrackerservice.security;

import com.example.jwtparser.JwtParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final String HEADER_NAME = "Authorization";
    private final String BEARER_PREFIX = "Bearer ";
    private Environment env;

    public AuthorizationFilter(AuthenticationManager authManager, Environment env) {
        super(authManager);
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String authorizationHeader = req.getHeader(HEADER_NAME);

        if (authorizationHeader == null
                || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String authorizationHeader = req.getHeader(HEADER_NAME);

        String jwtToken = authorizationHeader.replace(BEARER_PREFIX, "").trim();

        JwtParser jwtParser = new JwtParser(jwtToken, env.getProperty("jwt.signing.key"));

        String username = jwtParser.extractUsername();

        if (username == null) return null;

        return new UsernamePasswordAuthenticationToken(username, null, jwtParser.extractAuthorities());
    }
}
