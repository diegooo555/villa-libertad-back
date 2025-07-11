# Sistema de Pagos y Reservas - Backend

Este proyecto es un backend construido con **Spring Boot MVC** que ofrece un sistema de **autenticación segura**, **gestión de reservas** y procesamiento de **pagos en línea** mediante integración con **Wompi (Bancolombia)** usando **webhooks**.

## 🧰 Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring MVC
- Spring Security (Autenticación JWT)
- JPA / Hibernate
- PostgreSQL (o tu base de datos preferida)
- Wompi Webhooks (Bancolombia)
- Maven
- Swagger (Documentación de la API)

---

## ⚙️ Funcionalidades principales

- 🔐 Autenticación y autorización con Spring Security (JWT)
- 📅 Gestión de reservas (CRUD)
- 💳 Integración de pagos con Wompi (enlace de pago y confirmación por webhook)
- 📬 Recepción y manejo de webhooks seguros desde Wompi
- 📝 Registro de pagos exitosos o fallidos
- 📦 API REST documentada con Swagger

---

## 🔑 Autenticación

El sistema usa JWT para autenticar a los usuarios registrados. Se incluyen endpoints para:

- Registro (`/api/auth/register`)
- Login (`/api/auth/login`)
- Renovación de token (opcional)

---

## 💵 Integración con Wompi

Este proyecto permite a los usuarios realizar pagos mediante el servicio de Wompi:

1. Generación de enlaces de pago únicos.
2. Confirmación de pagos a través de **webhooks** recibidos desde Wompi.
3. Validación y registro de transacciones exitosas/fallidas.

> Nota: Para la integración, asegúrate de configurar tu llave pública y privada de Wompi en el archivo `application.properties` o como variables de entorno.

---
