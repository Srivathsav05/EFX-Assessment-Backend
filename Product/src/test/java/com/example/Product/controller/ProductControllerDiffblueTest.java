package com.example.Product.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.Product.model.Category;
import com.example.Product.model.Product;
import com.example.Product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductControllerDiffblueTest {
    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    /**
     * Test {@link ProductController#createProduct(Product)}.
     * <p>
     * Method under test: {@link ProductController#createProduct(Product)}
     */
    @Test
    @DisplayName("Test createProduct(Product)")
    void testCreateProduct() throws Exception {
        // Arrange
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategoryName(Category.Electronics);
        product.setDescription("The characteristics of someone or something");
        product.setId("42");
        product.setImageUrl("https://example.org/example");
        product.setPrice(10.0d);
        product.setProductName("Product Name");
        product.setRating(10.0d);
        when(productService.createProduct(Mockito.<Product>any())).thenReturn(product);

        Product product2 = new Product();
        product2.setBrand("Brand");
        product2.setCategoryName(Category.Electronics);
        product2.setDescription("The characteristics of someone or something");
        product2.setId("42");
        product2.setImageUrl("https://example.org/example");
        product2.setPrice(10.0d);
        product2.setProductName("Product Name");
        product2.setRating(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(product2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"productName\":\"Product Name\",\"brand\":\"Brand\",\"categoryName\":\"Electronics\",\"description\":\"The"
                                        + " characteristics of someone or something\",\"price\":10.0,\"imageUrl\":\"https://example.org/example\",\"rating"
                                        + "\":10.0}"));
    }

    /**
     * Test
     * {@link ProductController#getProducts(Category, List, Double, Double, Double)}.
     * <p>
     * Method under test:
     * {@link ProductController#getProducts(Category, List, Double, Double, Double)}
     */
    @Test
    @DisplayName("Test getProducts(Category, List, Double, Double, Double)")
    void testGetProducts() throws Exception {
        // Arrange
        when(productService.getProducts(Mockito.<Category>any(), Mockito.<List<String>>any(), Mockito.<Double>any(),
                Mockito.<Double>any(), Mockito.<Double>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/filters");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link ProductController#getAllProduct()}.
     * <p>
     * Method under test: {@link ProductController#getAllProduct()}
     */
    @Test
    @DisplayName("Test getAllProduct()")
    void testGetAllProduct() throws Exception {
        // Arrange
        when(productService.getAllProduct()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/all");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link ProductController#getProductByName(String)}.
     * <p>
     * Method under test: {@link ProductController#getProductByName(String)}
     */
    @Test
    @DisplayName("Test getProductByName(String)")
    void testGetProductByName() throws Exception {
        // Arrange
        when(productService.getProductByName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/search/{productName}",
                "Product Name");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link ProductController#updateProduct(String, Product)}.
     * <p>
     * Method under test: {@link ProductController#updateProduct(String, Product)}
     */
    @Test
    @DisplayName("Test updateProduct(String, Product)")
    void testUpdateProduct() throws Exception {
        // Arrange
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategoryName(Category.Electronics);
        product.setDescription("The characteristics of someone or something");
        product.setId("42");
        product.setImageUrl("https://example.org/example");
        product.setPrice(10.0d);
        product.setProductName("Product Name");
        product.setRating(10.0d);
        when(productService.updateProduct(Mockito.<String>any(), Mockito.<Product>any())).thenReturn(product);

        Product product2 = new Product();
        product2.setBrand("Brand");
        product2.setCategoryName(Category.Electronics);
        product2.setDescription("The characteristics of someone or something");
        product2.setId("42");
        product2.setImageUrl("https://example.org/example");
        product2.setPrice(10.0d);
        product2.setProductName("Product Name");
        product2.setRating(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(product2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"productName\":\"Product Name\",\"brand\":\"Brand\",\"categoryName\":\"Electronics\",\"description\":\"The"
                                        + " characteristics of someone or something\",\"price\":10.0,\"imageUrl\":\"https://example.org/example\",\"rating"
                                        + "\":10.0}"));
    }

    /**
     * Test {@link ProductController#deleteProduct(String)}.
     * <p>
     * Method under test: {@link ProductController#deleteProduct(String)}
     */
    @Test
    @DisplayName("Test deleteProduct(String)")
    void testDeleteProduct() throws Exception {
        // Arrange
        doNothing().when(productService).deleteProduct(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
