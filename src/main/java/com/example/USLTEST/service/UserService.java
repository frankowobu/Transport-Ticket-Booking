package com.example.USLTEST.service;

import com.example.USLTEST.domain.DTO.ChangePasswordRequest;
import com.example.USLTEST.domain.DTO.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    ResponseEntity<UserDto> updateUser(UserDto userDto, Long id);
    ResponseEntity<Void> changePassword(ChangePasswordRequest changePasswordRequest, Long id);
}
