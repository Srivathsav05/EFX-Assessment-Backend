package com.example.Product.controller;

import com.example.Product.model.Category;
import com.example.Product.model.Product;
import com.example.Product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("Test to create a Product")
    void testForCreateProduct() throws Exception {
        Product product = new Product();
        product.setId("1");
        product.setProductName("Watch");
        product.setBrand("Apple");
        product.setCategoryName(Category.Electronics);
        product.setPrice(9999.0);
        product.setDescription("The apple watch is a good product in US");
        product.setRating(5);
        product.setImageUrl("https://apple.com/watch");

        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(product);

        String requestBody =
                """
                {
                    "id": "1",
                    "productName": "Watch",
                    "brand": "Apple",
                    "categoryName": "Electronics",
                    "price": 9999.0,
                    "description": "The apple watch is a good product in US",
                    "rating": 5,
                    "imageUrl": "https://apple.com/watch"
                }
                """;

        // Perform POST request and validate response
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        """
                        {
                            "id": "1",
                            "productName": "Watch",
                            "brand": "Apple",
                            "categoryName": "Electronics",
                            "price": 9999.0,
                            "description": "The apple watch is a good product in US",
                            "rating": 5,
                            "imageUrl": "https://apple.com/watch"
                        }
                        """));
    }

    @Test
    @DisplayName("Test to filtering products")
    void testGetProductsWithFilters() throws Exception {
        // Mock data
        Product product1 = new Product();
        product1.setId("1");
        product1.setProductName("Laptop");
        product1.setBrand("Dell");
        product1.setCategoryName(Category.Electronics);
        product1.setPrice(50000.0);
        product1.setRating(4.5);
        product1.setDescription("High-performance laptop");
        product1.setImageUrl("https://example.com/laptop");

        Product product2 = new Product();
        product2.setId("2");
        product2.setProductName("Smartphone");
        product2.setBrand("Samsung");
        product2.setCategoryName(Category.Electronics);
        product2.setPrice(20000.0);
        product2.setRating(4.7);
        product2.setDescription("Feature-packed smartphone");
        product2.setImageUrl("https://example.com/smartphone");

        List<Product> productItems = Arrays.asList(product1, product2);

        // Mock service behavior
        Mockito.when(productService.getProducts(
                Category.Electronics,
                Arrays.asList("Dell", "Samsung"),
                15000.0,
                60000.0,
                4.0
        )).thenReturn(productItems);

        // Perform GET request
        mockMvc.perform(get("/products/filters")
                        .param("categoryName", "Electronics")
                        .param("brand", "Dell", "Samsung")
                        .param("minPrice", "15000")
                        .param("maxPrice", "60000")
                        .param("minRating", "4.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": "1",
                                "productName": "Laptop",
                                "brand": "Dell",
                                "categoryName": "Electronics",
                                "price": 50000.0,
                                "rating": 4.5,
                                "description": "High-performance laptop",
                                "imageUrl": "https://example.com/laptop"
                            },
                            {
                                "id": "2",
                                "productName": "Smartphone",
                                "brand": "Samsung",
                                "categoryName": "Electronics",
                                "price": 20000.0,
                                "rating": 4.7,
                                "description": "Feature-packed smartphone",
                                "imageUrl": "https://example.com/smartphone"
                            }
                        ]
                        """));
    }

    @Test
    @DisplayName("Test to Get All Products")
    void testGetAllProducts() throws Exception{
        Product product = new Product("1","T-shirt","H&M",Category.Clothing,"good looking shirt",1000.0,"https://h&m//clothing",4);
        List<Product> productItems=Arrays.asList(product);
        Mockito.when(productService.getAllProduct()).thenReturn(productItems);
        mockMvc.perform(get("/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        """
                        [
                        {
                            "id": "1",
                            "productName": "T-shirt",
                            "brand": "H&M",
                            "categoryName": "Clothing",
                            "price": 1000.0,
                            "description": "good looking shirt",
                            "rating": 4,
                            "imageUrl": "https://h&m//clothing"
                        }]
                        """));

    }
    @Test
    @DisplayName("Test to search ProductName in ProductList")
    void searchProductName() throws Exception
    {
        Product product = new Product("1","T-shirt","H&M",Category.Clothing,"good looking shirt",1000.0,"https://h&m//clothing",4);
        List<Product> productsItems = Arrays.asList(product);
        Mockito.when(productService.getProductByName("T-shirt")).thenReturn(productsItems);
        mockMvc.perform(get("/products/search/T-shirt")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(

                        """
                [
                    {
                        "id": "1",
                        "productName": "T-shirt",
                        "brand": "H&M",
                        "categoryName": "Clothing",
                        "price": 1000.0,
                        "description": "good looking shirt",
                        "rating": 4,
                        "imageUrl": "https://h&m//clothing"
                    }
                ]
                """));

    }

    @Test
    @DisplayName("Test to Update a Product")
    void testUpdateProductWithoutEq() throws Exception {
        // Mock the product data
        Product updatedProduct = new Product(
                "1",
                "Smartphone",
                "Samsung",
                Category.Electronics,
                "Updated description for the smartphone",
                1200.0,
                "https://samsung.com/smartphone",
                4.5
        );

        // Mocking the service layer
        Mockito.when(productService.updateProduct(anyString(), any(Product.class)))
                .thenReturn(updatedProduct);

        // JSON request body for the update
        String requestBody = """
        {
            "id": "1",
            "productName": "Smartphone",
            "brand": "Samsung",
            "categoryName": "Electronics",
            "price": 1200.0,
            "description": "Updated description for the smartphone",
            "rating": 4.5,
            "imageUrl": "https://samsung.com/smartphone"
        }
        """;

        // Expected JSON response
        String expectedResponse = """
        {
            "id": "1",
            "productName": "Smartphone",
            "brand": "Samsung",
            "categoryName": "Electronics",
            "price": 1200.0,
            "description": "Updated description for the smartphone",
            "rating": 4.5,
            "imageUrl": "https://samsung.com/smartphone"
        }
        """;

        // Perform PUT request and validate the response
        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("Test to Delete a Product")
    void testDeleteProduct() throws Exception {
        // Define the product ID to be deleted
        String productId = "1";

        // Mock the delete method (assuming it doesn't return anything)
        Mockito.doNothing().when(productService).deleteProduct(Mockito.anyString());

        // Perform the DELETE request and validate the response
        mockMvc.perform(delete("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(content().string(""))  // Ensure the response body is empty
                .andExpect(header().doesNotExist("Content-Length"));  // Ensure no content length header
    }





}
