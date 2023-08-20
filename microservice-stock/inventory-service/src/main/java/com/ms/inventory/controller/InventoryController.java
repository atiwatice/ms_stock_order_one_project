package com.ms.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.inventory.dto.response.InventoryResponse;
import com.ms.inventory.service.InventoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	// http://localhost:8082/api/inventory/iphone-13,iphone13-red

	// http://localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone13-red
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		log.info("Received inventory check request for skuCode: {}", skuCode);
		return inventoryService.isInStock(skuCode);
	}
}
