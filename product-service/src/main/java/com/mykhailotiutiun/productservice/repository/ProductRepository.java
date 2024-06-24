package com.mykhailotiutiun.productservice.repository;

import com.mykhailotiutiun.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
