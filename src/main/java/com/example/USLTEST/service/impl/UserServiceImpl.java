package com.example.USLTEST.service.impl;

import com.example.USLTEST.domain.DTO.ChangePasswordRequest;
import com.example.USLTEST.domain.DTO.UserDto;
import com.example.USLTEST.domain.entity.UserEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import com.example.USLTEST.repository.UserRepository;
import com.example.USLTEST.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(UserDto userDto, Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).map(existingUser -> {
                Optional.ofNullable(userDto.getFirstName()).ifPresent(existingUser::setFirstName);
                Optional.ofNullable(userDto.getLastName()).ifPresent(existingUser::setLastName);
                Optional.ofNullable(userDto.getDescription()).ifPresent(existingUser::setDescription);
                Optional.ofNullable(userDto.getCountry()).ifPresent(existingUser::setCountry);

                UserDto savedUserDto = userMapper.mapTo(userRepository.save(existingUser));
                return new ResponseEntity<>(savedUserDto, HttpStatus.OK);
            }).orElseThrow(() -> new RuntimeException("User Did Not Update"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> changePassword(ChangePasswordRequest changePasswordRequest, Long id) {
        try {
            return userRepository.findById(id).map(existingUser -> {
                existingUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(existingUser);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }).orElseThrow(() -> new RuntimeException("User Not Found"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
