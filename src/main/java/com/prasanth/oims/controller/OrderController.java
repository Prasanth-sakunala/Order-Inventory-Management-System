package com.prasanth.oims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasanth.oims.dto.CreateOrderRequest;
import com.prasanth.oims.dto.OrderResponseDTO;
import com.prasanth.oims.service.OrderService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
     }


    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDTO> createOrder(
        @RequestBody @Valid CreateOrderRequest request, 
        @Parameter(hidden = true) Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());
        OrderResponseDTO orderResponseDTO = orderService.createOrder(request, userId);
        return new ResponseEntity<>(orderResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDTO>> getMyOrders(
        Authentication authentication,
        Pageable pageable) 
    {
        Long userId = Long.valueOf(authentication.getName());
        Page<OrderResponseDTO> orders = orderService.getMyOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDTO> getMyOrders(
        @PathVariable Long orderId,
        Authentication authentication) 
    {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(orderService.getMyOrderById(userId, userId));
    }



    
    

}
