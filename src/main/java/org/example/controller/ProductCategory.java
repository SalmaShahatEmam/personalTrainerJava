package org.example.controller;


import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductCategory {

    @Autowired
    public ProductRepository productRepository;

    @GetMapping
    public ResponseEntity index()
    {
        List<Product> products = productRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }
    
    

}
