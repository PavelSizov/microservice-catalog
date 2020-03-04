package com.microservices.mentoring.catalog.service;

import com.microservices.mentoring.catalog.domain.Product;
import com.microservices.mentoring.catalog.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public Product getProductById(String id) {
        return catalogRepository.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return catalogRepository.getAllProducts();
    }

    public List<Product> getProductsByIds(List<String> ids) {
        return catalogRepository.getProductsByIds(ids);
    }

    public void reloadCatalog(){
        catalogRepository.reloadCatalog();
    }
}
