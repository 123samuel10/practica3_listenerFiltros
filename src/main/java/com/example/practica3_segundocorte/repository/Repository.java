package com.example.practica3_segundocorte.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    List<T>getList();
    T getById(Long id);
    void save(T t);
    void  delateById(Long id);
    void update(T t);



}