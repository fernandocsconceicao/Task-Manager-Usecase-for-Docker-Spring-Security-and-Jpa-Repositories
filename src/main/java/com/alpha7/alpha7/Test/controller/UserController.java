package com.alpha7.alpha7.Test.controller;

import com.alpha7.alpha7.Test.dto.AddRoleToUserDto;
import com.alpha7.alpha7.Test.dto.UserCreationRequestDto;
import com.alpha7.alpha7.Test.entity.Role;
import com.alpha7.alpha7.Test.entity.User;
import com.alpha7.alpha7.Test.security.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ArrayList<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers()) ;
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveTasks(@RequestBody UserCreationRequestDto userDto) {
        return ResponseEntity.ok().body(userService.saveUser(userDto.toUser(userDto)));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<User> addToUser(@RequestBody AddRoleToUserDto dto) {
        userService.addRoleToUser(dto.getEmail(), dto.getRoleName());
        return ResponseEntity.ok().build();
    }

}