package com.alpha7.alpha7.Test.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("trying to athorize");
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/token/refresh/**") ) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {

                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // TODO cryptograph
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorityCollection = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorityCollection.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorityCollection);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    logger.error(" Error when logging in: " + e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(403);
                    Map<String, String> token = new HashMap();
                    Map<String, String> error = new HashMap<>();
                    error.put("access_token", e.getMessage());
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), token);

                }

            } else {
                filterChain.doFilter(request, response);

            }
        }
    }
}
