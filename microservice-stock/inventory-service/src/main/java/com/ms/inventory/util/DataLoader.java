package com.ms.inventory.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ms.inventory.entity.Inventory;
import com.ms.inventory.repository.InventoryRepository;

@Component
public class DataLoader implements CommandLineRunner {
	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public void run(String... args) throws Exception {
		if (inventoryRepository.count() < 1) {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone_13_red");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		}

	}
}
