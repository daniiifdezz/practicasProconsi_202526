# Tu Ciudad De Cerca

## Descripción del Proyecto

**Tu Ciudad De Cerca** es una aplicación móvil desarrollada con **Kotlin Multiplatform (KMP)** y **Compose Multiplatform**, diseñada para explorar los puntos de interés de una ciudad. La aplicación permite a los usuarios visualizar una lista de lugares, ver los detalles de cada uno, gestionar una lista de favoritos y obtener información de contacto del desarrollador.

## Contexto del Proyecto

Esta aplicación ha sido desarrollada como parte del programa de prácticas de la empresa **Proconsi**. El proyecto ha servido como un caso práctico para aplicar y consolidar conocimientos avanzados en desarrollo de aplicaciones multiplataforma modernas, siguiendo las mejores prácticas de arquitectura y diseño de software.

##  Tecnologías y Librerías Utilizadas

- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **Framework UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) para una interfaz de usuario declarativa y compartida entre Android y Escritorio.
- **Arquitectura:**
  - **Clean Architecture:** Separación en capas (Presentación, Dominio, Datos) para un código más mantenible, escalable y testeable.
  - **MVVM (Model-View-ViewModel):** Patrón utilizado en la capa de Presentación para separar la lógica de la UI de su estado.
- **Red (Networking):**
  - **[Ktor Client](https://ktor.io/docs/client.html):** Para realizar las llamadas a la API REST y obtener los datos de los puntos de interés.
  - **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization):** Para parsear las respuestas JSON de la API (DTOs) a objetos Kotlin.
- **Persistencia de Datos (Base de Datos Local):**
  - **[Room](https://developer.android.com/training/data-storage/room):** Utilizado como base de datos local para gestionar la persistencia de los elementos favoritos del usuario, asegurando que se mantengan entre sesiones.
- **Asincronía:**
  - **[Corrutinas de Kotlin](https://kotlinlang.org/docs/coroutines-overview.html):** Para gestionar todas las operaciones asíncronas, como llamadas de red y accesos a la base de datos.
  - **[Kotlin Flows](https://kotlinlang.org/docs/flow.html):** Para manejar streams de datos de forma reactiva, permitiendo que la UI se actualice automáticamente cuando los datos (como la lista de favoritos) cambian.
- **Carga de Imágenes:**
  - **[Kamel](https://github.com/Kamel-Media/Kamel):** Una librería KMP para cargar imágenes desde URLs de forma asíncrona y eficiente.
- **Gestión de Dependencias:**
  - **Inyección de Dependencias Manual:** Se utiliza un enfoque de inyección manual en el punto de entrada de la aplicación (`App.kt`) para proporcionar las dependencias (Repositorios, UseCases) a los ViewModels.
  - **Gradle Version Catalogs (TOML):** Para una gestión centralizada y limpia de las versiones de las librerías.

##  Funcionalidades Implementadas

1.  **Pantalla de Bienvenida:**
    - Una pantalla de inicio visualmente atractiva con una imagen de fondo semitransparente.
    - Un botón de entrada que da paso a la aplicación principal.

2.  **Listado de Puntos de Interés:**
    - Muestra una lista de elementos obtenidos de una API remota.
    - Utiliza una `LazyVerticalGrid` para un diseño adaptable que se ve bien tanto en pantallas pequeñas (móvil) como grandes (escritorio).
    - Indica visualmente qué elementos han sido marcados como favoritos.

3.  **Pantalla de Detalle:**
    - Muestra información detallada de un elemento seleccionado, incluyendo una galería de imágenes y una descripción larga.
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
    - La aplicación utiliza un sistema de navegación manual basado en un `sealed class` (`Pantalla`) y un `when` en el Composable principal. Esto proporciona un control total sobre el flujo de navegación sin necesidad de librerías externas.

##  Patrones de Diseño y Arquitectura Aplicados

- **MVVM (Model-View-ViewModel):** Cada pantalla con lógica de negocio tiene su propio `ViewModel` (`ListadoVM`, `DetalleVM`, `FavoritosVM`) que expone el estado a la vista (Composable) a través de `StateFlow`.
- **Patrón Repositorio:** El `ElementoRepository` actúa como única fuente de verdad para los `UseCases`. Abstrae el origen de los datos, decidiendo si obtenerlos de la red (`ApiService`) o de la base de datos local (`FavoritoDAO`).
- **Casos de Uso (Use Cases / Interactors):** Cada acción de negocio está encapsulada en su propia clase (`GetElementosUseCase`, `GestionarFavoritoUseCase`, etc.). Esto hace que la lógica de dominio sea clara, reutilizable e independiente de la UI y de las fuentes de datos.
- **Inyección de Dependencias (DI):** Aunque de forma manual, se aplica el principio de DI al proporcionar las dependencias (como el `Repository`) a las clases que las necesitan (como los `UseCases` y `ViewModels`) desde un punto centralizado (`App.kt`).
- **Mapeo de Datos (Mapper):** Se utilizan funciones de extensión (`toDomain()`) para convertir los modelos de la capa de datos (DTOs) en los modelos de la capa de dominio (`Elemento`). Esto desacopla el modelo de negocio de la implementación de la API.
- **Programación Reactiva:** El uso extensivo de `Kotlin Flows` permite que la aplicación reaccione a los cambios de datos. Por ejemplo, cuando se añade un favorito, los `Flows` notifican automáticamente a los `ViewModels`, que a su vez actualizan la UI sin necesidad de llamadas manuales.
- **Gestión de Estado de UI:** Se utiliza una `data class` de estado (ej: `ElementoUI`, `DetalleUiState`) por cada pantalla para representar de forma inmutable todo lo que la vista necesita saber (si está cargando, si hay un error, o los datos a mostrar).

## Nota sobre el Proceso de Aprendizaje

El desarrollo de esta aplicación se ha complementado con la realización de ejercicios de iniciación previos. Estos ejercicios han sido fundamentales para comprender los conceptos básicos y la sintaxis del lenguaje Kotlin antes de abordar la arquitectura y los patrones complejos implementados en este proyecto.
