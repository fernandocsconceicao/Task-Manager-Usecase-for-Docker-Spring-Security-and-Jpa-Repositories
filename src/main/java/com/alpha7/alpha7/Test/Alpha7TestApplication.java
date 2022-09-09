package com.alpha7.alpha7.Test;

import com.alpha7.alpha7.Test.controller.UserController;
import com.alpha7.alpha7.Test.dto.AddRoleToUserDto;
import com.alpha7.alpha7.Test.dto.UserCreationRequestDto;
import com.alpha7.alpha7.Test.entity.Role;
import com.alpha7.alpha7.Test.entity.User;
import com.alpha7.alpha7.Test.exception.StandardException;
import com.alpha7.alpha7.Test.security.service.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@SpringBootApplication

public class Alpha7TestApplication implements CommandLineRunner {
	@Autowired
	UserServiceImp userServiceImp;

	@Autowired
	UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(Alpha7TestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(!userServiceImp.isThereAdminUser()){
			log.info("Creating admin account");

			try {
				userController.saveUser(new UserCreationRequestDto("admin@admin.com","123"));
				userController.saveRole(new Role(null, "ADMIN"));
				userController.addToUser(new AddRoleToUserDto("admin@admin.com","ADMIN" ));
			} catch (StandardException e) {
				e.printStackTrace();
			}
			log.info("Admin account created");

		}
	}
}
