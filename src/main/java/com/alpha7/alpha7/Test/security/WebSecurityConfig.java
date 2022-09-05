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

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceImp userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(super.authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(STATELESS);
        httpSecurity.authorizeRequests().antMatchers("/user/save/**","/api/login/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/token/refresh/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/user/save","/api/login/**");
        httpSecurity.authorizeRequests().antMatchers("/task/save","/task/late","/task/delete", "/task/edit").hasAnyAuthority("USER");
        httpSecurity.authorizeRequests().antMatchers(
                "/task/admin/alllate**",
                "/task/admin/alltodo**",
                "user/role/addtouser",
                "/user/role/save").hasAnyAuthority("ADMIN");
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilter(customAuthenticationFilter);

    }
}
