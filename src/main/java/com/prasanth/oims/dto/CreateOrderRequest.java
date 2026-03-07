package com.prasanth.oims.dto;

import java.util.List;

public class CreateOrderRequest {

    List<OrderItemRequest> items;

    public List<OrderItemRequest> getItems() {
        return items;
    }
    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

}
