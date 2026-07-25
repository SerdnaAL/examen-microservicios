package com.codigo.prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EjemplarDTO {
    private String codigoEjemplar;
    private boolean disponible;
}