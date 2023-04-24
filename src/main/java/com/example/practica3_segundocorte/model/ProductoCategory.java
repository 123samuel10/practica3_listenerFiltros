package com.example.practica3_segundocorte.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductoCategory {
    private  Long id;
    private String nombre;
    private Double precio;
    private LocalDate fechaRegistro;

    private Category category;
}
