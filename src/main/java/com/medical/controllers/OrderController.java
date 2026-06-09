package com.medical.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.Services.OrderServices;
import com.medical.entities.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderService;

    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request.getUserId(), request.getProductId(), request.getQuantity());
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersForUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    public static class OrderRequest {
        private Long userId;
        private Long productId;
        private Integer quantity;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
