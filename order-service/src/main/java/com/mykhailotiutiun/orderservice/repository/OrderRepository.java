package com.mykhailotiutiun.orderservice.repository;

import com.mykhailotiutiun.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
