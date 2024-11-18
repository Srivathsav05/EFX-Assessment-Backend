package com.example.Product.service;

import com.example.Product.exception.ProductNotFoundException;
import com.example.Product.model.Product;
import com.example.Product.model.Category; // Import the new enum
import com.example.Product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Get Products by Filters
    public List<Product> getProducts(Category categoryName, List<String> brand, Double minPrice, Double maxPrice, Double minRating) {
        return productRepository.findProductsByFilters(categoryName, brand, minPrice, maxPrice, minRating);
    }

    // Get Product by ID
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    // Get Product by Name
    public List<Product> getProductByName(String name) {
        List<Product> searchResults= productRepository.findByProductName(name);
        if (!searchResults.isEmpty())
            return searchResults;
        throw new ProductNotFoundException("No product Found");
    }

    // Update Product by ID
    public Product updateProduct(String id, Product updatedProductData) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isPresent())
        {
            existingProduct.get().setProductName(updatedProductData.getProductName());
            existingProduct.get().setBrand(updatedProductData.getBrand());
            existingProduct.get().setPrice(updatedProductData.getPrice());
            existingProduct.get().setDescription(updatedProductData.getDescription());
            existingProduct.get().setCategoryName(updatedProductData.getCategoryName());
            existingProduct.get().setRating(updatedProductData.getRating());
            existingProduct.get().setImageUrl(updatedProductData.getImageUrl());
            return productRepository.save(existingProduct.get());

        }
        throw new ProductNotFoundException("Product is not Found id" + id);

    }

    // Delete Product by ID
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " Not Found"));
        productRepository.delete(product);
    }
}
