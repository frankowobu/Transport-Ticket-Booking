package com.example.USLTEST.domain.mapper.impl;

import com.example.USLTEST.domain.DTO.OperatorList;
import com.example.USLTEST.domain.entity.BusEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class OperatorListMapperImpl implements Mapper<BusEntity, OperatorList> {

    private final ModelMapper modelMapper;

 @Override
    public OperatorList mapTo(BusEntity operator) {
        OperatorList operatorDto = new OperatorList();

        operatorDto.setDriverName(operator.getDriverName());
        operatorDto.setBusModel(operator.getBusModel());
        operatorDto.setBusNumber(operator.getBusNumber());
        operatorDto.setCapacity(operator.getCapacity());
        operatorDto.setPhoneNumber(operator.getPhoneNumber());
        operatorDto.setOrigin(operator.getRoute().getOrigin());
        operatorDto.setDestination(operator.getRoute().getDestination());

		return operatorDto;
    }

    @Override
    public BusEntity mapFrom(OperatorList operatorDto) {
        return modelMapper.map(operatorDto, BusEntity.class);
    }

    @Override
    public List<OperatorList> mapListTo(Iterable<BusEntity> operatorsIterable) {
        return StreamSupport.stream(operatorsIterable.spliterator(), false)
                .map(operators -> modelMapper.map(
                        operators, OperatorList.class
                )).collect(Collectors.toList());
    }


}

