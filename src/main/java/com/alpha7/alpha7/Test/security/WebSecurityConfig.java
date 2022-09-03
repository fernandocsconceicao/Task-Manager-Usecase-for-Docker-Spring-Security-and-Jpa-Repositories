package com.alpha7.alpha7.Test.security;

import com.alpha7.alpha7.Test.security.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig  {
    @Autowired
    private UserServiceImp userService;



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests().antMatchers("/user/save","/user/role/save","/user/role/addtouser").permitAll()
                .and().csrf().disable()
                .httpBasic().and()
                .formLogin().and()
                .addFilter(null)
        ;
        return httpSecurity.build();
    }





//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder(11)
//    }
//
//    @Bean
//    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
//        httpSecurity
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .antMatchers(
//                        "/register",
//                        "/resendVerifyToken*",
//                        "/verifyRegistration"
//                )
//                .permitAll()
//
//        return httpSecurity.build();
//    }

}
