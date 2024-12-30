package com.example.USLTEST.domain.DTO;

import com.example.USLTEST.domain.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String userId;

    private String firstName;

    private String userName;

    private String lastName;

    private String email;

    private String country;

    private String description;

    private Role role;
}
