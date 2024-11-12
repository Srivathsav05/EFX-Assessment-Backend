package com.example.Product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    @Id
    private String id;
    private String productName;
    private String brand;
    private String categoryName;
    private String description;
    private Double price;
    private String imageUrl;
    private int rating;
}
