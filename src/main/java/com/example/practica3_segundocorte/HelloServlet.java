package com.example.practica3_segundocorte;

import java.io.*;
import java.sql.Connection;
import java.time.LocalDate;

import com.example.practica3_segundocorte.model.Category;
import com.example.practica3_segundocorte.model.ProductoCategory;
import com.example.practica3_segundocorte.service.ProductoService;
import com.example.practica3_segundocorte.serviceImpl.ProductoRepositoryImpl;
import com.example.practica3_segundocorte.serviceImpl.ProductoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceImpl(conn);
        ;
        //agregamos
        ProductoRepositoryImpl productoCategoryRepository=new ProductoRepositoryImpl();
        ProductoCategory producto1=new ProductoCategory();
        Category category=new Category("fruta",3);
        producto1.setCategory(category);
        producto1.setNombre("pera");
        producto1.setPrecio(2000.0);
        producto1.setFechaRegistro(LocalDate.now());
        productoCategoryRepository.save(producto1);
        //demás codigo aqui...

        //eliminar
        ProductoCategory productoUpdate=productoCategoryRepository.getById(2L);
        productoCategoryRepository.delateById(productoUpdate.getId());




    }


    public void destroy() {
    }
}