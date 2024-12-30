package com.example.USLTEST.domain.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignInRequest {
    private final String email;
    private final String password;
}
