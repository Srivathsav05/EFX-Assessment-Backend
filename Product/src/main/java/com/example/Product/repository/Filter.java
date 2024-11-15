package com.example.Product.repository;

import com.example.Product.model.Category;
import com.example.Product.model.Product;

import java.util.List;

public interface Filter {
    List<Product> findProductsByFilters(Category categoryName, List<String> brand, Double minPrice, Double maxPrice, Double minRating);
}
