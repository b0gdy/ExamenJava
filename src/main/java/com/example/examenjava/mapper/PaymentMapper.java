package com.example.examenjava.mapper;

import com.example.examenjava.domain.Payment;
import com.example.examenjava.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto mapToDto(Payment payment);

    Payment mapToEntity(PaymentDto paymentDto);

}
