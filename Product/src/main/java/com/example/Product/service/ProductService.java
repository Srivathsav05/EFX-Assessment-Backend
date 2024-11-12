package com.example.Product.service;
import com.example.Product.exception.ProductNotFoundException;
import com.example.Product.model.Product;
import com.example.Product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Create a Product POST
    public Product createProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new ProductNotFoundException("Product name cannot be null or empty");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new ProductNotFoundException("Product price must be greater than 0");
        }
        return productRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    // Get Product by ID
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    // Get Product by Name
    public List<Product> getProductByName(String name) {
        return productRepository.findByProductName(name);
    }

    // Get Products by CategoryName
    public List<Product> getCategoryName(String name) {
        return productRepository.findByCategoryName(name);
    }

    // Get Products by Brand
    public List<Product> getBrand(String name) {
        return productRepository.findByBrand(name);
    }

    // Update Product by ID
    public Product updateProduct(String id, Product updatedProductData) {
        Product existingProduct = getProductById(id);

        existingProduct.setProductName(updatedProductData.getProductName());
        existingProduct.setBrand(updatedProductData.getBrand());
        existingProduct.setPrice(updatedProductData.getPrice());
        existingProduct.setDescription(updatedProductData.getDescription());
        existingProduct.setCategoryName(updatedProductData.getCategoryName());
        existingProduct.setRating(updatedProductData.getRating());
        existingProduct.setImageUrl(updatedProductData.getImageUrl());

        return productRepository.save(existingProduct);
    }

    // Delete Product by ID
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " Not Found"));
        productRepository.delete(product);
    }
}
