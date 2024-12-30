package com.example.USLTEST.service;


import com.example.USLTEST.domain.DTO.SignInRequest;
import com.example.USLTEST.domain.DTO.SignInResponse;
import com.example.USLTEST.domain.DTO.SignUpDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

ResponseEntity signUp(SignUpDto signUpDto);
SignInResponse signIn(SignInRequest signInRequest);

    Object findById(Long id);

    String checkMail(String email);


}
