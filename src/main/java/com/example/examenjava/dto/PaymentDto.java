package com.example.examenjava.dto;

import com.example.examenjava.domain.Status;
import com.example.examenjava.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    @NotBlank
    private String type;

    @NotBlank
    @Size(max = 200)
    private String customer;

    @NotNull
    @Positive
    private Double amount;

    @NotBlank
    private String status;

}
