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
public class Socio {

    @Id
    private String codigoSocio;
    private String nombre;
    private String email;
    private boolean activo;
}