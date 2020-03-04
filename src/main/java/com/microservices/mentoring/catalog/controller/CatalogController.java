package com.microservices.mentoring.catalog.controller;

import com.microservices.mentoring.catalog.domain.Product;
import com.microservices.mentoring.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam(name = "id", required = false) List<String> ids) {
        if (null == ids) {
            return catalogService.getAllProducts();
        }
        return catalogService.getProductsByIds(ids);
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable String id) {
        return catalogService.getProductById(id);
    }

    @GetMapping("/products/price")
    public Map<String, BigDecimal> getProductsPrice(@RequestParam(name = "id") List<String> ids) {
        return catalogService.getProductsByIds(ids).stream()
                .collect(Collectors.toMap(Product::getId, Product::getPrice));
    }

    @PutMapping("/reload")
    public String reload() {
        catalogService.reloadCatalog();
        return "Reloaded";
    }

}
