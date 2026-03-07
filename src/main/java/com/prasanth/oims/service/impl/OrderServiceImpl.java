package com.prasanth.oims.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.prasanth.oims.exception.ResourceNotFoundException;
import com.prasanth.oims.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prasanth.oims.dto.CreateOrderRequest;
import com.prasanth.oims.dto.OrderItemRequest;
import com.prasanth.oims.dto.OrderItemResponseDTO;
import com.prasanth.oims.dto.OrderResponseDTO;
import com.prasanth.oims.entity.Order;
import com.prasanth.oims.entity.OrderItem;
import com.prasanth.oims.entity.OrderStatus;
import com.prasanth.oims.entity.Product;
import com.prasanth.oims.repository.OrderRepository;
import com.prasanth.oims.repository.ProductRepository;
import com.prasanth.oims.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository){ 
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequest request, Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setItems(new ArrayList<>());

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemRequest itemReq : request.getItems()) {

            int updated = productRepository.reserveInventory(
                itemReq.getProductId(),
                itemReq.getQuantity()
            );

            if (updated == 0) {
                throw new BadRequestException("Insufficient inventory");
            }

            Product product = productRepository
                .findById(itemReq.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());

            BigDecimal itemPrice =
                product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            item.setPriceAtPurchase(itemPrice);

            order.getItems().add(item);
            totalAmount = totalAmount.add(itemPrice);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder =orderRepository.save(order);
        return convertToResponseDTO(savedOrder);

    }

    @Override
    public Page<OrderResponseDTO> getMyOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
            .map(this::convertToResponseDTO);
    }

    @Override
    public OrderResponseDTO getMyOrderById(Long orderId, Long userId) {

        return orderRepository.findByIdAndUserId(orderId, userId)
            .map(this::convertToResponseDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));        
    }

    @Override
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
            .map(this::convertToResponseDTO);
    }

    private OrderResponseDTO convertToResponseDTO(Order order){
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setId(order.getId());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setTotalAmount(order.getTotalAmount());
        responseDTO.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();
        
        for(OrderItem item : order.getItems()){
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPriceAtPurchase(item.getPriceAtPurchase());
            itemDTOs.add(itemDTO);
        }
        responseDTO.setItems(itemDTOs);
        return responseDTO;
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus){
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        validateStatusTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return convertToResponseDTO(updatedOrder);
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next){
        if(current == OrderStatus.COMPLETED || current == OrderStatus.CANCELLED){
            throw new BadRequestException("Orders cannot be modified once completed or cancelled");
        }

        if(current == OrderStatus.CREATED && next == OrderStatus.COMPLETED){
            throw new BadRequestException("Order must be PROCESSING before COMPLETED");
        }

    }

}
