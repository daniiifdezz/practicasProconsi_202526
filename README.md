# Tu Ciudad De Cerca

## Descripción del Proyecto

**Tu Ciudad De Cerca** es una aplicación móvil desarrollada con **Kotlin Multiplatform (KMP)** y **Compose Multiplatform**, diseñada para explorar los puntos de interés de una ciudad. La aplicación permite a los usuarios visualizar una lista de lugares, ver los detalles de cada uno, gestionar una lista de favoritos y obtener información de contacto del desarrollador.

## Contexto del Proyecto

Esta aplicación ha sido desarrollada como parte del programa de prácticas de la empresa **Proconsi**. Dicho proyecto ha servido como una práctica para aplicar y consolidar conocimientos avanzados en desarrollo de aplicaciones multiplataforma modernas, siguiendo las mejores prácticas de arquitectura y diseño de software.

## Arquitectura

El proyecto está construido siguiendo **Clean Architecture** combinada con **MVVM**, lo que permite una clara separación de responsabilidades, mayor mantenibilidad.

### Capas
- **Presentación**
  - Implementa el patrón **MVVM**.
  - Los `ViewModel` exponen el estado y la lógica de la UI.
- **Dominio**
  - Contiene los **Casos de Uso** y modelos de negocio puros.
  - No depende de frameworks ni de detalles de implementación.
- **Datos**
  - Gestiona las fuentes de datos tanto la API como la base de datos local.
  - Se encarga del mapeo entre DTOs y modelos de dominio.

La arquitectura es **100% compartida** entre Android y Desktop gracias a **Kotlin Multiplatform (KMP)**.


## Navegación

La navegación se gestiona mediante **Voyager**, una librería multiplataforma diseñada para KMP.

- Cada pantalla implementa la interfaz `Screen`.
- El `Navigator`, definido en `App.kt`, administra la pila de pantallas y las transiciones.
- Los `ViewModel` se inyectan directamente en cada pantalla usando `koinViewModel()`.

Este enfoque simplifica el flujo de navegación y elimina la gestión manual de estados.


## Gestión de Errores

El proyecto implementa un manejo centralizado de errores mediante `AppError`.

- Las excepciones de red y servidor se capturan en la capa de datos.
- Los errores se propagan de forma controlada hasta la capa de presentación.
- La UI recibe estados seguros y predecibles para mostrar mensajes adecuados al usuario.


## Tecnologías y Librerías Utilizadas

### Lenguaje
- **Kotlin**

### Multiplataforma y UI
- **Kotlin Multiplatform (KMP):** Lógica de negocio y UI compartida entre Android y Desktop.
- **Compose Multiplatform:** Framework declarativo para interfaces modernas.

### Arquitectura
- **Clean Architecture**
- **MVVM (Model-View-ViewModel)**

### Navegación
- **Voyager:** Navegación basada en `Screen` y `Navigator` para KMP.

### Inyección de Dependencias
- **Koin:** Gestión de dependencias para ViewModels, Repositorios y Casos de Uso.
- **Gradle Version Catalogs (TOML):** Gestión centralizada de versiones de librerías.

### Red (Networking)
- **Ktor Client:** Cliente HTTP para consumo de la API REST.
- **Kotlinx Serialization:** Serialización y deserialización de respuestas JSON.

### Persistencia de Datos
- **Room:** Base de datos local para la persistencia de favoritos.

### Asincronía y Reactividad
- **Kotlin Flow:** Streams reactivos para actualización automática de la UI.

### Carga de Imágenes
- **Kamel:** Carga asíncrona y eficiente de imágenes desde URLs.

##  Funcionalidades Implementadas

1.  **Pantalla de Bienvenida:**
    - Una pantalla de inicio visualmente atractiva con una imagen de fondo semitransparente.
    - Un botón de entrada que da paso a la aplicación principal.

2.  **Listado de Puntos de Interés:**
    - Muestra una lista de elementos obtenidos de una API remota.
    - Utiliza una `LazyVerticalGrid` para un diseño adaptable que se ve bien tanto en pantallas pequeñas como es el caso del móvil o como grandes en el caso de escritorio.
    - Indica visualmente qué elementos han sido marcados como favoritos.
    - Un menú lateral, con el cuál puedes navegar por las distintas pantallas de la aplicación.

3.  **Pantalla de Detalle:**
    - Muestra información detallada de un elemento seleccionado, incluyendo una galería de imágenes, una descripción larga del lugar de interes, así como las caracteristicas de dicho lugar, curiosidades, un botón el cuál llevará a un vídeo ilustrativo, además de información de interes que contendrá tanto la dirección de dicho lugar, el teléfono móvil y el email.
    - Las etiquetas `<br>` en la descripción se procesan para mostrar saltos de línea correctamente.
    - Permite al usuario añadir o quitar el elemento de su lista de favoritos a través de un icono interactivo.

4.  **Gestión de Favoritos:**
    - La lista de favoritos se guarda de forma persistente en una base de datos local usando **Room**.
    - Una pantalla dedicada muestra todos los elementos que el usuario ha marcado como favoritos, también en un layout de cuadrícula.
    - El estado de "favorito" se actualiza de forma reactiva en todas las pantallas relevantes (Listado, Detalle y Favoritos).

5.  **Pantalla de Contacto:**
    - Muestra información sobre el desarrollador.
    - Los enlaces a perfiles (LinkedIn, GitHub) son **hipervínculos funcionales** que abren el navegador del sistema.

6.  **Navegación por Estado:**
    -  La aplicación utiliza la librería **Voyager** para la navegación entre pantallas. Cada pantalla es una clase que implementa la interfaz `Screen`, y la gestión de la pila de navegación se realiza mediante el componente `Navigator`.

​
 ##  Patrones de Diseño y Arquitectura Aplicados
​
 - **MVVM (Model-View-ViewModel):** Cada pantalla con lógica de negocio tiene su propio `ViewModel` (`ListadoVM`, `DetalleVM`, `FavoritosVM`) que expone el estado a la vista (Composable) a través de `StateFlow`.
 - **Patrón Repositorio:** El `ElementoRepository` actúa como única fuente de verdad para los `UseCases`. Abstrae el origen de los datos, decidiendo si obtenerlos de la red (`ApiService`) o de la base de datos local (`FavoritoDAO`).
 - **Casos de Uso (Use Cases / Interactors):** Cada acción de negocio está encapsulada en su propia clase (`GetElementosUseCase`, `GestionarFavoritoUseCase`, etc.). Esto hace que la lógica de dominio sea clara, reutilizable e independiente de la UI y de las fuentes de datos.
 - **Inyección de Dependencias (DI):** Se utiliza **Koin** como framework de DI. Los ViewModels se inyectan en las pantallas, y los casos de uso y repositorios se inyectan en los ViewModels, desacoplando completamente la construcción de objetos.
 - **Mapeo de Datos (Mapper):** Se utilizan funciones de extensión (`toDomain()`) para convertir los modelos de la capa de datos (DTOs) en los modelos de la capa de dominio (`Elemento`). Esto desacopla el modelo de negocio de la implementación de la API.
 - **Programación Reactiva:** El uso extensivo de `Kotlin Flows` permite que la aplicación reaccione a los cambios de datos. Por ejemplo, cuando se añade un favorito, los `Flows` notifican automáticamente a los `ViewModels`, que a su vez actualizan la UI sin necesidad de llamadas manuales.
 - **Gestión de Estado de UI:** Se utiliza una `data class` de estado (ej: `ElementoUI`, `DetalleUiState`) por cada pantalla para representar de forma inmutable todo lo que la vista necesita saber (si está cargando, si hay un error, o los datos a mostrar).
​

## Nota sobre el Proceso de Aprendizaje 

El desarrollo de esta aplicación se ha complementado con la realización de ejercicios de iniciación previos. Estos ejercicios han sido fundamentales para comprender los conceptos básicos y la sintaxis del lenguaje Kotlin antes de abordar la arquitectura y los patrones complejos implementados en este proyecto.


## Descargas

- [Descargar APK Android](https://github.com/daniiifdezz/practicasProconsi_202526/releases/download/v1.0.1/TuCiudadDeCerca.apk)
- [Descargar Instalador Windows (.msi)](https://github.com/daniiifdezz/practicasProconsi_202526/releases/download/v1.0.1/TuCiudadDeCerca-1.0.0.msi)