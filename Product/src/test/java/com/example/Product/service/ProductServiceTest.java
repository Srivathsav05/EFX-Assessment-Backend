package com.example.Product.service;

import com.example.Product.exception.ProductNotFoundException;
import com.example.Product.model.Category;
import com.example.Product.model.Product;
import com.example.Product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        product = new Product( "1",
                "Sample Product",
                "Sample Brand",
                Category.Electronics,
                "Sample Description",
                100.0,
                "sample-image-url",
                4
        );
    }

    @AfterEach
    void TearDown(){
        productService = null;
        productRepository = null;
    }

    @Test
    @DisplayName("Add Product")
    void addProduct(){
        when(productRepository.save(product)).thenReturn(product);
        Product createdProduct = productService.createProduct(product);
        assertNotNull(createdProduct);
        assertEquals("Sample Product", createdProduct.getProductName());
    }

    @Test
    @DisplayName("Add Product with null name should throw exception")
    void productNameIsNull() {
        product.setProductName(null);
        Exception thrown = assertThrows(ProductNotFoundException.class, () -> productService.createProduct(product));
        assertEquals("Product name cannot be null or empty",thrown.getMessage());
    }

    @Test
    @DisplayName("Add Product with price greater than 0")
    void productPriceIsNotZero(){
        product.setPrice(0.0);
        Exception thrown = assertThrows(ProductNotFoundException.class, () -> productService.createProduct(product));
        assertEquals("Product price must be greater than 0",thrown.getMessage());
    }

    @Test
    @DisplayName("Get All Products")
    void getAllProductsTest() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> products = productService.getAllProduct();

        assertNotNull(products);
        assertEquals("Sample Product", products.get(0).getProductName());
    }

    @Test
    @DisplayName("Get All Products throw Exception")
    void getAllProductsExceptionTest(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        Exception thrown = assertThrows(ProductNotFoundException.class, productService::getAllProduct);

        assertEquals("Products not found", thrown.getMessage());
    }

    @Test
    @DisplayName("Get Products with Filter")
    void getProductsWithFilterTest(){
        List<String> brands = List.of("Sample Brand");
        when(productRepository.findProductsByFilters( Category.Electronics,brands, 50.0, 150.0, 4.0)).thenReturn(List.of(product));
        List<Product> products = productService.getProducts(Category.Electronics, brands, 50.0, 150.0, 4.0);

        assertNotNull(products);
        assertEquals("Sample Product", products.get(0).getProductName());
    }

    @Test
    @DisplayName("Get Products with Filter throws Exception")
    void getProductsWithFilterExceptionTest(){
        List<String> brands = List.of("Brand");
        when(productRepository.findProductsByFilters(Category.Furniture, brands, 50.0, 150.0, 4.0)).thenReturn(Collections.emptyList());

        Exception thrown= assertThrows(ProductNotFoundException.class, () ->
                productService.getProducts(Category.Furniture, brands, 50.0, 150.0, 4.0)
        );

        assertEquals("Product not found with this fields", thrown.getMessage());
    }

    @Test
    @DisplayName("Get Product by id")
    void getProductByIdTest(){
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        Product found = productService.getProductById("1");

        assertNotNull(found);
        assertEquals("Sample Product", found.getProductName());
    }

    @Test
    @DisplayName("Get Product by Id throws exception")
    void getProductByIdExceptionTest(){
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        Exception thrown = assertThrows(ProductNotFoundException.class, () -> productService.getProductById("1"));
        assertEquals("Product Not Found", thrown.getMessage());
    }

    @Test
    @DisplayName("Get Product by name")
    void getProductsByNameTest(){
        when(productRepository.findByProductName("Sample Product")).thenReturn(List.of(product));
        List<Product> products = productService.getProductByName("Sample Product");

        assertNotNull(products);
        assertEquals("Sample Product", products.get(0).getProductName());
    }

    @Test
    @DisplayName("Update Product")
    void updateProductTest(){
        Product updated = new Product(
                "1",
                "Product",
                "Brand",
                Category.Furniture,
                "Description",
                150.0,
                "image-url",
                4.8
        );

        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(updated);
        Product updatedProduct = productService.updateProduct("1", updated);

        assertNotNull(updatedProduct);
        assertEquals("Product", updatedProduct.getProductName());
        assertEquals("Brand", updatedProduct.getBrand());
        assertEquals(150.0, updatedProduct.getPrice());
        assertEquals("Description", updatedProduct.getDescription());
    }

    @Test
    @DisplayName("Update Product throws Exception")
    void updateProductExceptionTest() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());
        Product updated= new Product();

        Exception thrown = assertThrows(ProductNotFoundException.class, () -> productService.updateProduct("1", updated));
        assertEquals("Product Not Found", thrown.getMessage());
    }

    @Test
    @DisplayName("Delete Product")
    void deleteProductTest() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct("1");
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProductExceptionTest() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct("1"));
        assertEquals("Product with id 1 Not Found", exception.getMessage());
    }

}
