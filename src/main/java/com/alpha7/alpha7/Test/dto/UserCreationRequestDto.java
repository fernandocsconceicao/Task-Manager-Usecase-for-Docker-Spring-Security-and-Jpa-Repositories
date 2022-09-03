package com.alpha7.alpha7.Test.dto;

import com.alpha7.alpha7.Test.entity.Role;
import com.alpha7.alpha7.Test.entity.User;
import lombok.AllArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class UserCreationRequestDto {

    private String email;
    private String password;

    public User toUser(UserCreationRequestDto dto) {
        return new User(null,dto.email, dto.password, new ArrayList<>());
    }

}
