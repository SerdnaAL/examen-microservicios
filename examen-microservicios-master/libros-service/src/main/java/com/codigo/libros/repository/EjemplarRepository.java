package com.codigo.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codigo.libros.model.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, String> {
}