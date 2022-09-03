package com.alpha7.alpha7.Test.security;

import com.alpha7.alpha7.Test.security.filter.CustomAuthenticationFilter;
import com.alpha7.alpha7.Test.security.filter.CustomAuthorizationFilter;
import com.alpha7.alpha7.Test.security.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
    @Autowired
    private UserServiceImp userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(super.authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        httpSecurity.authorizeRequests()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/user/save","/user/role/save","/user/role/addtouser").permitAll()
                .and().csrf().disable()
                .httpBasic().and()
                .formLogin();
        httpSecurity.authorizeRequests().anyRequest().authenticated().and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
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
