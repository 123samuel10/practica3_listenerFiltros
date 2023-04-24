package com.example.practica3_segundocorte.serviceImpl;

import com.example.practica3_segundocorte.model.Category;
import com.example.practica3_segundocorte.model.ProductoCategory;
import com.example.practica3_segundocorte.repository.Repository;
import com.example.practica3_segundocorte.service.ProductoService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {
    private Repository<ProductoCategory> productRepository;
    private Repository<Category> categoryRepository;

    public ProductoServiceImpl(Connection conn) {
    }


    public void ProductoServiceJdbcImpl(Connection connection) {
        this.productRepository = (Repository<ProductoCategory>) new ProductoRepositoryJdbcImpl(connection);
        this.categoryRepository = (Repository<Category>) new CategoriaRepositoryImpl(connection);
    }


    @Override
    public List<ProductoCategory> listar() {
        return productRepository.getList();


    }

}
