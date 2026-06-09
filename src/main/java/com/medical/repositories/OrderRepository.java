package com.medical.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.medical.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
