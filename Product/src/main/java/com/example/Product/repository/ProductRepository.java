package com.example.Product.repository;

import com.example.Product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByName(String name);

    List<Product> findByCategoryName(String name);

    List<Product> findByBrand(String name);

    List<Product> findByProductName(String name);
}
