package com.alpha7.alpha7.Test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddRoleToUserDto {
    private String email;
    private String roleName;
}
