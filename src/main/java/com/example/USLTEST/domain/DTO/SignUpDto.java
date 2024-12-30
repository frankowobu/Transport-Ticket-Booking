package com.example.USLTEST.domain.DTO;

import com.example.USLTEST.domain.entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SignUpDto {

    private String firstName;

    private String lastName;

    private String userName;
    @Email(message = "Input a valid email address")

    private String email;

    private String password;

    private String confirmPassword;

    private String phoneNumber;

    private String address;

    private String country;

    private String description;

    private Role role;
}
