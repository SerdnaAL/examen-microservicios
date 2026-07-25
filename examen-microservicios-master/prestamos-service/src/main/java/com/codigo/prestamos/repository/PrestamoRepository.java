package com.codigo.prestamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codigo.prestamos.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}