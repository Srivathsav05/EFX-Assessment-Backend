package com.example.Product.repository;

import com.example.Product.model.Category;
import com.example.Product.model.Product;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> findProductsByFilters(Category categoryName, String brand, Double minPrice, Double maxPrice, Double minRating);
}
