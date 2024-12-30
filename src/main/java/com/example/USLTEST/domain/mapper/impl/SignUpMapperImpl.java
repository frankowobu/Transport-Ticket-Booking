package com.example.USLTEST.domain.mapper.impl;


import com.example.USLTEST.domain.DTO.SignUpDto;
import com.example.USLTEST.domain.entity.UserEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Data
@Component
public class SignUpMapperImpl implements Mapper<UserEntity, SignUpDto> {

    private final ModelMapper modelMapper;
    @Override
    public SignUpDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, SignUpDto.class);
    }

    @Override
    public UserEntity mapFrom(SignUpDto signUpDto) {
        return modelMapper.map(signUpDto,UserEntity.class);
    }

    @Override
    public Iterable<SignUpDto> mapListTo(Iterable<UserEntity> a) {
        return null;
    }




}
