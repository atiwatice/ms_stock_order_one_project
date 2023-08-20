package com.ms.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
