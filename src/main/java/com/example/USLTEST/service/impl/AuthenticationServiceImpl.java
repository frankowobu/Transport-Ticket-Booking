package com.example.USLTEST.service.impl;


import com.example.USLTEST.Exception.EmailNotFoundException;
import com.example.USLTEST.Exception.PasswordMismatchException;
import com.example.USLTEST.Exception.UserNotFoundException;
import com.example.USLTEST.domain.DTO.SignInRequest;
import com.example.USLTEST.domain.DTO.SignInResponse;
import com.example.USLTEST.domain.DTO.SignUpDto;
import com.example.USLTEST.domain.DTO.UserDto;
import com.example.USLTEST.domain.entity.Role;
import com.example.USLTEST.domain.entity.UserEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import com.example.USLTEST.repository.UserRepository;
import com.example.USLTEST.service.AuthenticationService;
import com.example.USLTEST.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final Mapper<UserEntity,SignUpDto> signUpMapper;

    private final JWTService jwtService;

    private final Mapper<UserEntity, UserDto> userMapper;

    @Override 
    public ResponseEntity signUp(SignUpDto signUpDto) {
        try {
            if(userRepository.existsByEmail(signUpDto.getEmail())){
                throw new UserNotFoundException("There is an account associated with this email already");
            }

            if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
                throw new PasswordMismatchException("Password does not match");
            }

            signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            signUpDto.setConfirmPassword(passwordEncoder.encode(signUpDto.getConfirmPassword()));
            signUpDto.setRole(Role.PASSENGER);
            UserEntity userEntity = signUpMapper.mapFrom(signUpDto);

            if(userEntity.getRole() != Role.ADMIN)
          {
              userRepository.save(userEntity);
            }else{
                throw new UserNotFoundException("Not Permitted to create Admin Account");
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserNotFoundException error) {
            System.out.println(error);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new EmailNotFoundException("Invalid email or password");
        }
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("User doesn't not exist"));

        var jwt = jwtService.generateToken(user);

        UserDto signedInUser = userMapper.mapTo(user);
        return new SignInResponse(signedInUser, jwt);
    }

    @Override
    public UserEntity findById(Long id) {

        return userRepository.findById(id).get();
    }

    @Override
    public String checkMail(String email) {
        return userRepository.findByEmail(email).map(
                existingUser -> {
                    String foundEmail = Optional.ofNullable(existingUser.getEmail()).orElse(null);
                    return foundEmail;
                }).orElseThrow(
                () -> new EmailNotFoundException("Email Not Found!!!")
        );
    }}


