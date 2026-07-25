package com.codigo.libros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ejemplar {

    @Id
    private String codigoEjemplar;
    private String titulo;
    private String autor;
    private boolean disponible;
}