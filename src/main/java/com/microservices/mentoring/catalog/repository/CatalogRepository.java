package com.microservices.mentoring.catalog.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.microservices.mentoring.catalog.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CatalogRepository {

    @Autowired
    private AmazonClient amazonS3Client;

    private List<Product> products = Collections.emptyList();

    @PostConstruct
    private void initCatalog() {
        reloadCatalog();
    }

    public void reloadCatalog() {
        try {
            products = amazonS3Client.downloadFile(new TypeReference<List<Product>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(String id) {

        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Product> getProductsByIds(List<String> ids) {
        return products.stream().filter(product -> ids.contains(product.getId())).collect(Collectors.toList());
    }
}
