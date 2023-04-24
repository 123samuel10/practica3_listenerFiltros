package com.example.practica3_segundocorte.filter;

import com.example.practica3_segundocorte.ConexionBaseDatos;
import com.example.practica3_segundocorte.serviceImpl.ServiceJdbcException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
//
@WebFilter("/hello-servlet")
public class ConexionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
//Si la modalidad de confirmación automática (autocommit) está activada, elgestor de bases de datos ejecuta una operación de confirmación después de laejecución de cada sentencia de SQL. (se entenderá mejor en la siguientepractica este concepto)
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                request.setAttribute("conn", conn);
                chain.doFilter(request, response);
                conn.commit();
            } catch (SQLException | ServiceJdbcException e) {
                conn.rollback();
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}