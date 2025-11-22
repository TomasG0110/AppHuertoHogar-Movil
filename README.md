# Proyecto App Móvil - HuertoHogar (DSY1105)

Aplicación móvil Android desarrollada en Jetpack Compose para el caso "HuertoHogar". El proyecto simula una tienda online de productos agrícolas, permitiendo a los usuarios registrarse, ver un catálogo de productos, gestionar un carrito de compras y actualizar su perfil.

Este proyecto fue desarrollado como parte de la Evaluación Parcial 2 de la asignatura "Desarrollo de Aplicaciones Móviles" (DSY1105).

## 👥 Integrantes

* [Nombre Tomas Guajardo]


## ✅ Funcionalidades Implementadas (Según Rúbrica DSY1105)

A continuación se detallan las funcionalidades implementadas, alineadas con los Indicadores de Evaluación de la rúbrica.

### IE 2.1.1: Interfaz Coherente y Navegación Funcional
* **Interfaz Coherente:** Se implementó el tema visual completo de HuertoHogar, respetando la paleta de colores y la guía de tipografías (Montserrat y Playfair Display) definida en el caso.
* **Navegación Funcional:** Se implementó una navegación robusta:
    * **Navegación Principal (NavHost):** Controla el flujo inicial (Splash -> Autenticación -> App Principal).
    * **Navegación Anidada (Bottom Nav Bar):** Dentro de la app principal, se implementó una barra de navegación inferior para las secciones: "Inicio" (Catálogo), "Carrito" y "Perfil".

### IE 2.1.2: Formularios Validados con Retroalimentación
Se implementaron todos los formularios requeridos por el caso, con validación de campos, íconos y mensajes de error visuales:
* **Formulario de Login:** Validación de formato de email y contraseña no vacía.
* **Formulario de Registro:** Validación de email, contraseña (mín. 6 caracteres) y confirmación de contraseña.
* **Formulario de Perfil:** Validación de dirección no vacía y teléfono (9 dígitos).

### IE 2.2.1: Lógica de Validación Desacoplada
* **Arquitectura MVVM:** Toda la lógica de la aplicación está desacoplada de la UI.
* **Lógica en ViewModels:** La lógica de validación de todos los formularios (Login, Registro, Perfil) reside en sus respectivos `ViewModels` (`LoginViewModel`, `RegisterViewModel`, `ProfileViewModel`), asegurando que la UI (Composable) solo se encargue de mostrar el estado.

### IE 2.2.2: Animaciones y Retroalimentación Funcional
* **Splash Screen Animado:** Una pantalla de carga inicial con una animación de zoom-in del logo.
* **Retroalimentación de Carrito:** Al añadir un producto al carrito, se muestra un `Snackbar` con el mensaje "Producto añadido".
* **Retroalimentación de Compra:** Al "Pagar" en el carrito, se dispara una notificación nativa del sistema.

### IE 2.3.1: Estructura Modular y Persistencia Local
* **Estructura Modular:** El proyecto está organizado en paquetes que separan responsabilidades: `ui` (Vistas), `viewmodel` (Lógica), `repository` (Datos), `model` (Entidades), `db` (Room) y `navigation` (Flujo).
* **Persistencia Local (Room):** Se implementó una base de datos local (Room) para gestionar el Carrito de Compras. Los usuarios pueden añadir, ver y eliminar productos, y estos persisten incluso si la app se cierra.
* **Persistencia Local (SharedPreferences):** Se utiliza SharedPreferences para guardar y cargar los datos del formulario de Perfil del usuario (dirección y teléfono).

### IE 2.3.2: Herramientas de Colaboración
* **Control de Versiones (Git):** El proyecto se desarrolló utilizando Git, con *commits* distribuidos y mensajes descriptivos que documentan el progreso.

### IE 2.4.1: Acceso a Recursos Nativos (2)
Se implementó el acceso a dos recursos nativos del dispositivo:
1.  **Cámara:** En la pantalla de Perfil, el usuario puede tocar un ícono para solicitar permiso de cámara, abrir la app de cámara, tomar una foto y mostrarla en su perfil.
2.  **Notificaciones:**
    * Al entrar a la app (post-login), se solicita el permiso de `POST_NOTIFICATIONS` (Android 13+).
    * Al presionar "Ir a Pagar" en el carrito, se envía una notificación nativa del sistema confirmando el pedido.

## 🛠️ Stack Tecnológico

* Kotlin
* Jetpack Compose
* Arquitectura MVVM (Model-View-ViewModel)
* Navigation Compose (Navegación anidada)
* Room (Base de Datos Local para Carrito)
* SharedPreferences (Datos de Perfil)
* Corutinas y Flow (Programación asíncrona)
* Coil (Carga de imágenes)
* Material 3

## 🚀 Pasos para Ejecutar

1.  Clonar el repositorio:
    ```bash
    git clone [URL_DE_TU_REPOSITORIO]
    ```
2.  Abrir el proyecto en Android Studio.
3.  Asegurarse de que el `applicationId` en el archivo `build.gradle.kts (Module :app)` coincida con el `android:authorities` del `<provider>` en el `AndroidManifest.xml`. (Requerido para el `FileProvider` de la cámara).
    * Ej: `authorities="${applicationId}.provider"`
4.  Ejecutar la aplicación en un emulador o dispositivo físico.
    
