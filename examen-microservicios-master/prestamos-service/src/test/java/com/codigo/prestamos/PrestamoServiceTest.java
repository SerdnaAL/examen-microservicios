package com.codigo.prestamos;

import com.codigo.prestamos.client.LibrosClient;
import com.codigo.prestamos.client.NotificacionesClient;
import com.codigo.prestamos.dto.*;
import com.codigo.prestamos.repository.PrestamoRepository;
import com.codigo.prestamos.service.PrestamoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrestamoServiceTest {

    @Mock
    private LibrosClient librosClient;

    @Mock
    private NotificacionesClient notificacionesClient;

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    @Test
    public void testPrestamoRechazadoPorSocioInactivo() {
        PrestamoRequest request = new PrestamoRequest("LIB-01", "SOC-99");
        SocioDTO socioInactivo = new SocioDTO("SOC-99", false);
        EjemplarDTO ejemplarValido = new EjemplarDTO("LIB-01", true);

        when(librosClient.obtenerSocio("SOC-99")).thenReturn(socioInactivo);
        when(librosClient.obtenerEjemplar("LIB-01")).thenReturn(ejemplarValido);

        PrestamoResponse response = prestamoService.registrarPrestamo(request);

        assertEquals("RECHAZADA", response.getEstado());
        assertEquals("Socio inactivo", response.getMotivoRechazo());
    }
}