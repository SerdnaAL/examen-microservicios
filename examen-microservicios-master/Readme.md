**Bases de datos requeridas: libros\_db, prestamos\_db, notificaciones\_db**

**Verificación de estado (PowerShell / CMD):**



net start | findstr -i "postgresql"



**ORDEN DE ARRANQUE:**



1. Verificamos que el servicio local de PostgreSQL esté iniciado y con las bases de datos creadas.



2\. Levantamos eureka-server.



3\. Levantamos libros-service. (microservicio base)



4\. Levantamos prestamos-service y notificaciones-service. (consumidores)



**Patrones de Diseño Usados:**



Service Registry (Eureka): Registro y localización dinámica de microservicios sin hardcodear IPs o puertos en la comunicación interservicio.



Database per Service: Persistencia aislada por servicio (librosdb, prestamosdb, notificacionesdb) sobre tu instancia local de PostgreSQL.



Declarative REST Client (OpenFeign): Comunicación HTTP síncrona declarativa entre microservicios integrada con la resolución de nombres de Eureka.



Externalized Configuration: Gestión de variables de entorno y propiedades (application.properties / application.yml) fuera del código fuente.



**Secuencia de Ejecución:**



Base de Datos (PostgreSQL local): Aseguramos que el servicio esté activo con las bases de datos libros\_db, prestamos\_db y notificaciones\_db.



Eureka Server (:8761): Iniciamos la aplicación y verificar el panel web.



Servicio Base (libros-service): Iniciamos y verificar su registro en Eureka.



Servicios Consumidores: Iniciamos prestamos-service y notificaciones-service.



Prueba End-to-End: Probamos en Postman la creación de préstamo (POST /api/v1/prestamos), listado (GET /api/v1/prestamos), devolución (POST /api/v1/prestamos/{id}/devolucion) y notificación (POST /api/v1/notificaciones).



# Examen Microservicios

\[!\[Quality Gate Status]([!\[Quality gate](https://sonarcloud.io/api/project\_badges/quality\_gate?project=SerdnaAL\_examen-microservicios)](https://sonarcloud.io/summary/new_code?id=SerdnaAL_examen-microservicios))](https://sonarcloud.io/summary/overall?id=SerdnaAL\_examen-microservicios)

## Dashboard de SonarCloud

Enlace directo al análisis del proyecto:
[Ver Dashboard en SonarCloud](https://sonarcloud.io/summary/overall?id=SerdnaAL_examen-microservicios)

