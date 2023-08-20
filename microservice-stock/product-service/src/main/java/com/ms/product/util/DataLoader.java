package com.ms.product.util;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ms.product.entity.Product;
import com.ms.product.repository.ProductRepository;

@Component
public class DataLoader implements CommandLineRunner {
	@Autowired
	ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		if (productRepository.count() < 1) {
			Product product = new Product();
			product.setName("iPhone 13");
			product.setDescription("iPhone 13");
			product.setPrice(BigDecimal.valueOf(1000));

			productRepository.save(product);
		}
	}
}
