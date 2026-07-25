package com.codigo.prestamos.client;

import com.codigo.prestamos.dto.EjemplarDTO;
import com.codigo.prestamos.dto.SocioDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LibrosClient {

    private final RestClient restClient;

    public LibrosClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://libros-service").build();
    }

    public SocioDTO obtenerSocio(String codigoSocio) {
        return restClient.get()
                .uri("/api/v1/socios/" + codigoSocio)
                .retrieve()
                .body(SocioDTO.class);
    }

    public EjemplarDTO obtenerEjemplar(String codigoEjemplar) {
        return restClient.get()
                .uri("/api/v1/libros/" + codigoEjemplar)
                .retrieve()
                .body(EjemplarDTO.class);
    }

    public void actualizarDisponibilidad(String codigoEjemplar, boolean disponible) {
        restClient.patch()
                .uri("/api/v1/libros/" + codigoEjemplar + "/disponibilidad?disponible=" + disponible)
                .retrieve()
                .toBodilessEntity();
    }
}