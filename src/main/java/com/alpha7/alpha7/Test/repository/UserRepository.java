package com.alpha7.alpha7.Test.repository;

import com.alpha7.alpha7.Test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
     ArrayList<User> findAll();

}
