package com.example.Product.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
