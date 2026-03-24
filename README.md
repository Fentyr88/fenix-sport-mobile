# Fenix Sport (Android)

Aplicacion Android con Kotlin + Jetpack Compose basada en una tienda deportiva con tema oscuro, autenticacion simulada, catalogo, detalle, carrito y panel admin.

## Requisitos

- Android Studio reciente
- JDK 17 (recomendado para AGP moderno)
- Android SDK instalado

## Ejecutar

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar en emulador o dispositivo.

## Flujo principal

- Login: `admin@fenixsport.com` + cualquier contrasena valida (>= 6) habilita acceso Admin.
- Register: validaciones basicas de formulario.
- Home: lista de productos con animaciones.
- Detail: detalle y agregar al carrito.
- Cart: eliminar items y checkout.
- Admin: crear, editar y eliminar productos (simulado en memoria).

## Arquitectura

- UI: Compose + Navigation Compose
- Estado: MVVM + StateFlow
- Data: FakeData + Repository pattern

