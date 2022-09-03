package com.alpha7.alpha7.Test.security.service.interfaces;

import com.alpha7.alpha7.Test.entity.Role;
import com.alpha7.alpha7.Test.entity.User;

import java.util.ArrayList;

public interface UserService {
    User saveUser(User user);
    Role saveRole (Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    ArrayList<User> getUsers();
}
