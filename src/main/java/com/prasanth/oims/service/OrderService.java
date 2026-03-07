package com.prasanth.oims.service;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prasanth.oims.dto.CreateOrderRequest;
import com.prasanth.oims.dto.OrderResponseDTO;
import com.prasanth.oims.entity.OrderStatus;

public interface OrderService {
    OrderResponseDTO createOrder(CreateOrderRequest request, Long userId);

    Page<OrderResponseDTO> getMyOrders(Long userId, Pageable pageable);

    OrderResponseDTO getMyOrderById(Long orderId, Long userId);

    Page<OrderResponseDTO> getAllOrders(Pageable pageable);

    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) throws BadRequestException;

}
