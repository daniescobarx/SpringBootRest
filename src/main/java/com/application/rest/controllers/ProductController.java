package com.application.rest.controllers;


import com.application.rest.controllers.DTO.ProductDTO;
import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()){
            Product product = productOptional.get();
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<ProductDTO> productList = productService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                        .price(product.getPrice())
                                .maker(product.getMaker())
                                .build()
                ).toList();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/findByPriceInRange")
    public ResponseEntity<?> findByPriceInRange(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice){
        List<ProductDTO> productList = productService.findByPriceInRange(minPrice, maxPrice)
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                ).toList();
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if (productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() ==  null){
            return ResponseEntity.badRequest().build();
        }

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build();

        productService.save(product);

        return ResponseEntity.created(new URI("/api/Product/save")).build();
        }


        @PutMapping("/update/{id}")
        public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDTO productDTO){
            Optional<Product> productOptional = productService.findById(id);

            if (productOptional.isPresent()){
                Product product = productOptional.get();
                product.setName(productDTO.getName());
                product.setPrice(productDTO.getPrice());
                product.setMaker(productDTO.getMaker());
                productService.save(product);
                return ResponseEntity.ok("Registro atualizado");
            }

            return ResponseEntity.notFound().build();
        }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (id != null){
            productService.deleteById(id);
            return ResponseEntity.ok("Registro deletado");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProducts(){
        long totalProducts = productService.countProducts();
        return ResponseEntity.ok(totalProducts);
    }

    @GetMapping("/findByMaker")
    public ResponseEntity<?> findByMaker(@RequestParam Maker maker){
        List<ProductDTO> productList = productService.findByMaker(maker)
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                ).toList();
        return ResponseEntity.ok(productList);
    }



}
