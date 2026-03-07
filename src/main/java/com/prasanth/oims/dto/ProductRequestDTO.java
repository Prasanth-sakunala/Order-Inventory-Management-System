package com.prasanth.oims.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    
    @NotBlank
    private String name;

    @NotBlank
    private String description;
    
    @NotNull
    @Positive
    private BigDecimal price;
    
    @NotNull
    @Min(1)
    private Integer quantity;

}
