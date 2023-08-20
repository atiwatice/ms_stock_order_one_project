package com.ms.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
