package com.codigo.prestamos.service;

import com.codigo.prestamos.client.LibrosClient;
import com.codigo.prestamos.client.NotificacionesClient;
import com.codigo.prestamos.dto.EjemplarDTO;
import com.codigo.prestamos.dto.SocioDTO;
import com.codigo.prestamos.dto.PrestamoRequest;
import com.codigo.prestamos.dto.PrestamoResponse;
import com.codigo.prestamos.model.Prestamo;
import com.codigo.prestamos.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibrosClient librosClient;
    private final NotificacionesClient notificacionesClient;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LibrosClient librosClient,
                           NotificacionesClient notificacionesClient) {
        this.prestamoRepository = prestamoRepository;
        this.librosClient = librosClient;
        this.notificacionesClient = notificacionesClient;
    }

    public PrestamoResponse registrarPrestamo(PrestamoRequest request) {
        SocioDTO socio;
        EjemplarDTO ejemplar;

        try {
            socio = librosClient.obtenerSocio(request.getCodigoSocio());
            ejemplar = librosClient.obtenerEjemplar(request.getCodigoEjemplar());
        } catch (Exception e) {
            return registrarFallo(request, "Ejemplar o Socio no existe / Error de comunicacion");
        }

        if (!socio.isActivo()) {
            return registrarFallo(request, "Socio inactivo");
        }

        if (!ejemplar.isDisponible()) {
            return registrarFallo(request, "Ejemplar no disponible");
        }

        librosClient.actualizarDisponibilidad(request.getCodigoEjemplar(), false);

        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoSocio(request.getCodigoSocio());
        prestamo.setCodigoEjemplar(request.getCodigoEjemplar());
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(7));
        prestamo.setEstado("APROBADO");
        prestamoRepository.save(prestamo);

        notificacionesClient.enviarAviso(
                request.getCodigoSocio(),
                "Prestamo registrado exitosamente para el ejemplar: " + request.getCodigoEjemplar()
        );

        return new PrestamoResponse("APROBADO", "Prestamo registrado correctamente");
    }

    private PrestamoResponse registrarFallo(PrestamoRequest request, String motivo) {
        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoSocio(request.getCodigoSocio());
        prestamo.setCodigoEjemplar(request.getCodigoEjemplar());
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstado("RECHAZADO");
        prestamo.setMotivoRechazo(motivo);
        prestamoRepository.save(prestamo);

        return new PrestamoResponse("RECHAZADO", motivo);
    }

    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    public Prestamo obtenerPorId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    public PrestamoResponse registrarDevolucion(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado("DEVUELTO");
        prestamoRepository.save(prestamo);

        librosClient.actualizarDisponibilidad(prestamo.getCodigoEjemplar(), true);

        return new PrestamoResponse("DEVUELTO", "Devolución registrada correctamente");
    }
}