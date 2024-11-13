package com.example.Product.repository;

import com.example.Product.model.Product;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> findProductsByFilters(String categoryName, String brand, Double minPrice, Double maxPrice, Double minRating);
}
