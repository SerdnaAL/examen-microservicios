package com.codigo.libros.controller;

import com.codigo.libros.model.Ejemplar;
import com.codigo.libros.model.Socio;
import com.codigo.libros.repository.EjemplarRepository;
import com.codigo.libros.repository.SocioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LibrosController {

    private final SocioRepository socioRepository;
    private final EjemplarRepository ejemplarRepository;

    public LibrosController(SocioRepository socioRepository, EjemplarRepository ejemplarRepository) {
        this.socioRepository = socioRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    @PostConstruct
    public void cargarDatosDemo() {
        if (socioRepository.count() == 0) {
            socioRepository.save(new Socio("SOC-99", "Juan Perez", "juan@email.com", true));
        }
        if (ejemplarRepository.count() == 0) {
            ejemplarRepository.save(new Ejemplar("LIB-01", "Clean Code", "Robert C. Martin", true));
        }
    }

    @PostMapping("/libros")
    public ResponseEntity<Ejemplar> crearEjemplar(@RequestBody Ejemplar ejemplar) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ejemplarRepository.save(ejemplar));
    }

    @GetMapping("/libros")
    public List<Ejemplar> listarEjemplares() {
        return ejemplarRepository.findAll();
    }

    @GetMapping("/libros/{codigoEjemplar}")
    public Ejemplar obtenerEjemplar(@PathVariable String codigoEjemplar) {
        return ejemplarRepository.findById(codigoEjemplar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ejemplar no encontrado"));
    }

    @PutMapping("/libros/{codigoEjemplar}")
    public Ejemplar editarEjemplar(@PathVariable String codigoEjemplar, @RequestBody Ejemplar datos) {
        Ejemplar ejemplar = ejemplarRepository.findById(codigoEjemplar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ejemplar no encontrado"));

        ejemplar.setTitulo(datos.getTitulo());
        ejemplar.setAutor(datos.getAutor());
        ejemplar.setDisponible(datos.isDisponible());

        return ejemplarRepository.save(ejemplar);
    }

    @DeleteMapping("/libros/{codigoEjemplar}")
    public ResponseEntity<Void> eliminarEjemplar(@PathVariable String codigoEjemplar) {
        if (!ejemplarRepository.existsById(codigoEjemplar)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ejemplar no encontrado");
        }
        ejemplarRepository.deleteById(codigoEjemplar);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/libros/{codigoEjemplar}/disponibilidad")
    public ResponseEntity<Void> actualizarDisponibilidad(
            @PathVariable String codigoEjemplar,
            @RequestParam(required = false, defaultValue = "true") boolean disponible) {

        Ejemplar ejemplar = ejemplarRepository.findById(codigoEjemplar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ejemplar no encontrado"));

        ejemplar.setDisponible(disponible);
        ejemplarRepository.save(ejemplar);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/socios")
    public ResponseEntity<Socio> crearSocio(@RequestBody Socio socio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioRepository.save(socio));
    }

    @GetMapping("/socios")
    public List<Socio> listarSocios() {
        return socioRepository.findAll();
    }

    @GetMapping("/socios/{codigoSocio}")
    public Socio obtenerSocio(@PathVariable String codigoSocio) {
        return socioRepository.findById(codigoSocio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio no encontrado"));
    }

    @PutMapping("/socios/{codigoSocio}")
    public Socio editarSocio(@PathVariable String codigoSocio, @RequestBody Socio datos) {
        Socio socio = socioRepository.findById(codigoSocio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio no encontrado"));

        socio.setNombre(datos.getNombre());
        socio.setEmail(datos.getEmail());
        socio.setActivo(datos.isActivo());

        return socioRepository.save(socio);
    }

    @DeleteMapping("/socios/{codigoSocio}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable String codigoSocio) {
        if (!socioRepository.existsById(codigoSocio)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio no encontrado");
        }
        socioRepository.deleteById(codigoSocio);
        return ResponseEntity.noContent().build();
    }
}