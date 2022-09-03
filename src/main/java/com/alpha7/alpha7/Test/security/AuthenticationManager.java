package com.alpha7.alpha7.Test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    @Autowired
    public org.springframework.security.authentication.AuthenticationManager AuthenticationManager(
            AuthenticationManagerBuilder authenticationManagerBuilder,
            UserDetailsService userServiceImp ) throws Exception {

        this.authenticationManagerBuilder = authenticationManagerBuilder;
        authenticationManagerBuilder.userDetailsService(userServiceImp);
        return authenticationManagerBuilder.build();
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
