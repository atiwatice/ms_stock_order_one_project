package com.ms.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.order.entity.OrderLineItems;

public interface OrderItemsRepository extends JpaRepository<OrderLineItems, Long> {

}
