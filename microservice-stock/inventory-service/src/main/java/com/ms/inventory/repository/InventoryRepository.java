package com.ms.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
