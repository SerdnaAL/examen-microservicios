package com.codigo.prestamos.controller;

import com.codigo.prestamos.model.Prestamo;
import org.springframework.web.bind.annotation.*;
import com.codigo.prestamos.dto.PrestamoRequest;
import com.codigo.prestamos.dto.PrestamoResponse;
import com.codigo.prestamos.service.PrestamoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public PrestamoResponse crearPrestamo(@RequestBody PrestamoRequest request) {
        return prestamoService.registrarPrestamo(request);
    }

    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Prestamo obtenerPorId(@PathVariable Long id) {
        return prestamoService.obtenerPorId(id);
    }

    @PostMapping("/{id}/devolucion")
    public PrestamoResponse registrarDevolucion(@PathVariable Long id) {
        return prestamoService.registrarDevolucion(id);
    }
}