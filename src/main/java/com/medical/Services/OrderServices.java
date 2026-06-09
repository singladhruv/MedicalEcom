package com.medical.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.entities.Order;
import com.medical.entities.Product;
import com.medical.entities.User;
import com.medical.kafka.KafkaProducerService;
import com.medical.kafka.events.OrderEvent;
import com.medical.repositories.OrderRepository;
import com.medical.repositories.ProductRepository;
import com.medical.repositories.UserRepository;

@Service
public class OrderServices {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public Order placeOrder(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (product.getStock() == null || product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        kafkaProducerService.sendOrderEvent(new OrderEvent(
                savedOrder.getId(),
                user.getId(),
                product.getId(),
                quantity,
                savedOrder.getTotalPrice(),
                savedOrder.getOrderDate()
        ));
        return savedOrder;
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
