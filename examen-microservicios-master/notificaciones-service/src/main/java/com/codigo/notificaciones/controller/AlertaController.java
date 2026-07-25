package com.codigo.notificaciones.controller;

import com.codigo.notificaciones.model.Alerta;
import com.codigo.notificaciones.repository.AlertaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class AlertaController {

    private final AlertaRepository alertaRepository;

    public AlertaController(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    @PostMapping
    public ResponseEntity<Alerta> registrarAlerta(@RequestBody Alerta alerta) {
        Alerta guardada = alertaRepository.save(alerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @GetMapping
    public List<Alerta> listarAlertas() {
        return alertaRepository.findAll();
    }
}