package com.kunsas.grabgoods.order.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunsas.grabgoods.order.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    
}
