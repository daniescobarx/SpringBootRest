package com.application.rest.controllers;

import com.application.rest.controllers.DTO.ProductDTO;
import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.service.IProductService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.servlet.http.PushBuilder;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private IProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testfindById(){
        Product product = new Product();
        product.setId(4L);
        when(productService.findById(any())).thenReturn(Optional.of(product));

        ResponseEntity<?> result = productController.findById(2L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    public void testNotFindById(){
        when(productService.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<?> result = productController.findById(2L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void testFindAll(){
        Maker maker1 = new Maker();
        maker1.setId(3L);
        maker1.setName("dani");

        Product product1 = new Product(5L, "Kauan", new BigDecimal(10.00), maker1);
        Product product2 = new Product(5L, "Cliente", new BigDecimal(20.00), maker1);
        Product product3 = new Product(5L, "Cliente", new BigDecimal(20.00), maker1);

        when(productService.findAll()).thenReturn(List.of(product1, product2, product3));
        ResponseEntity<?> result = productController.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    public void testSave() throws Exception{
        Maker maker1 = new Maker();
        maker1.setId(3L);
        maker1.setName("dani");

        ProductDTO productDTO = ProductDTO.builder()
                .id(2L)
                .name("dani")
                .price(new BigDecimal(10.0))
                .maker(maker1)
                .build();

        doNothing().when(productService).save(any());
        ResponseEntity<?> result = productController.save(productDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(201, result.getStatusCode().value());
    }

    @Test
    public void testNotSave() throws URISyntaxException {
        doNothing().when(productService).save(any());

//        ProductDTO invalidProductDTO = ProductDTO.builder().build();
        ResponseEntity<?> result = productController.save(ProductDTO.builder().name("").build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(400, result.getStatusCode().value());
    }

    @Test
    public void update(){
        ProductDTO productDTO = ProductDTO.builder().name("dani").build();
        Long expectedId = 3L;

        when(productService.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<?> result = productController.update(expectedId,productDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(404, result.getStatusCode().value());
    }


    @Test
    public void testDeleteById(){
        Long expectedId = 2L;

        doNothing().when(productService).deleteById(any());
        ResponseEntity<?> result = productController.deleteById(expectedId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("Registro deletado", result.getBody().toString());
    }

    @Test
    public void testNotDeleteById(){
        Long expectedId = null;

        ResponseEntity<?> result = productController.deleteById(expectedId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(400, result.getStatusCode().value());
    }

    @Test
    public void testCountProducts(){
        long expectedCount = 3L;
        when(productService.countProducts()).thenReturn(expectedCount);

        ResponseEntity<Long> result = productController.countProducts();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals(expectedCount, result.getBody());
    }

}