package com.codigo.prestamos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NotificacionesClient {

    private final RestClient restClient;

    public NotificacionesClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://notificaciones-service").build();
    }

    public void enviarAviso(String codigoSocio, String mensaje) {
        try {
            restClient.post()
                    .uri("/api/v1/alertas?socio=" + codigoSocio + "&msg=" + mensaje)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            System.out.println("No se pudo enviar la alerta, pero el flujo del préstamo continúa sin caerse.");
        }
    }
}