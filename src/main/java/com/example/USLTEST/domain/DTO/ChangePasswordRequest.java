package com.example.USLTEST.domain.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChangePasswordRequest {
    private  String newPassword;
}
