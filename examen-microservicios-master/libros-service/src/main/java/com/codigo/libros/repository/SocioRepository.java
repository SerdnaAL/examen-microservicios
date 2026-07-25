package com.codigo.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codigo.libros.model.Socio;

public interface SocioRepository extends JpaRepository<Socio, String> {
}