package com.alpha7.alpha7.Test.security.service;

import com.alpha7.alpha7.Test.entity.Role;
import com.alpha7.alpha7.Test.entity.User;
import com.alpha7.alpha7.Test.repository.RoleRepository;
import com.alpha7.alpha7.Test.repository.UserRepository;
import com.alpha7.alpha7.Test.security.service.interfaces.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User saveUser(User user) {
        log.info("Saving user - email s%".format(user.getEmail()));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean isThereAdminUser() {
        ArrayList<User> all = userRepository.findAll();
        for (User user:all) {
            for (Role role: user.getRoles())
            if(role.getName().equals("ADMIN")){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        if (user == null) {
            log.warn("User not Found");
        }
        return user;
    }

    public String getEmailFromRequestServlet(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {

                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // TODO cryptograph
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                return decodedJWT.getSubject();
            } catch (Exception e) {
                log.error(" Error when getting Email: " + e.getMessage());
                return null;

            }
        }else{
            return  null;

        }


    }
}
