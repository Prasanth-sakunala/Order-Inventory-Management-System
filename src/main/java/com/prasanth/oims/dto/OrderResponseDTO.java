package com.prasanth.oims.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.prasanth.oims.entity.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponseDTO {

    private Long id;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;

}
