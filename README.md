# Sort It

Sort It es una aplicación Android diseñada para ayudar a los usuarios a gestionar sus tareas de manera efectiva utilizando una interfaz de calendario completa. La aplicación presenta una vista semanal para una navegación rápida y una vista detallada de la fecha que muestra las tareas obtenidas de una base de datos local Room. Además, Sort It integra servicios de Firebase para mejorar la funcionalidad y la experiencia del usuario.

## Tabla de Contenidos

- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación](#instalación)
    - [Prerrequisitos](#prerrequisitos)
    - [Pasos](#pasos)
        - [1. Clonar el Repositorio](#1-clonar-el-repositorio)
        - [2. Abrir el Proyecto en Android Studio](#2-abrir-el-proyecto-en-android-studio)
        - [3. Añadir `google-services.json`](#3-añadir-google-servicesjson)
        - [4. Construir y Ejecutar el Proyecto](#4-construir-y-ejecutar-el-proyecto)
- [Uso](#uso)
- [Configuración de Firebase](#configuración-de-firebase)
- [Contribuciones](#contribuciones)

## Características

- **Vista Semanal:** Navega a través de las semanas sin problemas con `ViewPager2`.
- **Vista Detallada de la Fecha:** Visualiza y gestiona las tareas para una fecha seleccionada utilizando un `RecyclerView`.
- **Gestión de Tareas:** Añade, edita y elimina tareas con detalles como nombre, hora de inicio y hora de finalización.
- **Integración con Firebase:** Utiliza servicios de Firebase para mejorar la funcionalidad.
- **Almacenamiento Local:** Almacena tareas localmente usando la base de datos Room para acceso sin conexión.

## Tecnologías Utilizadas

- **Kotlin**
- **Android Studio**
- **ViewPager2**
- **RecyclerView**
- **Room Database**
- **Servicios de Firebase**
- **Coroutines**

## Instalación

### Prerrequisitos

- **Android Studio:** Asegúrate de tener la última versión de Android Studio instalada.
- **Cuenta de Firebase:** Una cuenta de Firebase para configurar e integrar los servicios de Firebase.

### Pasos

#### 1. Clonar el Repositorio

```bash
git clone https://github.com/IvanTicona/SortIt.git
```

#### 2. Abrir el Proyecto en Android Studio

1. Abre Android Studio.
2. Haz clic en `File > Open`.
3. Navega hasta el directorio del repositorio clonado y selecciónalo.
4. Haz clic en `OK` para abrir el proyecto.

#### 3. Añadir `google-services.json`

Para integrar los servicios de Firebase en tu aplicación, necesitas añadir el archivo `google-services.json` a tu proyecto.

1. **Descargar `google-services.json`:**
    - Ve a la [Consola de Firebase](https://console.firebase.google.com/).
    - Selecciona tu proyecto o crea uno nuevo.
    - Haz clic en el ícono de Android para añadir una aplicación Android a tu proyecto.
    - Sigue los pasos para registrar tu aplicación (asegúrate de que el nombre del paquete coincida con tu proyecto).
    - Descarga el archivo `google-services.json` proporcionado por Firebase.

2. **Colocar `google-services.json` en el Proyecto:**
    - Mueve el archivo `google-services.json` descargado al directorio `app/` de tu proyecto.

   La ruta correcta debería ser:

   ```
   Sort-It/
   └── app/
       └── google-services.json
   ```

#### 4. Construir y Ejecutar el Proyecto

1. **Sincronizar el Proyecto con los Archivos Gradle:**
    - Después de añadir `google-services.json`, Android Studio puede pedirte que sincronices el proyecto. Si no es así, haz clic en `File > Sync Project with Gradle Files`.

2. **Construir el Proyecto:**
    - Haz clic en `Build > Make Project` o presiona `Ctrl+F9` para construir el proyecto.

3. **Ejecutar la Aplicación:**
    - Conecta un dispositivo Android o inicia un emulador.
    - Haz clic en `Run > Run 'app'` o presiona `Shift+F10`.

## Uso

1. **Navegación del Calendario:**
    - Desliza a través de las semanas en la Vista Semanal para navegar entre diferentes semanas.
    - Toca un día específico para ver y gestionar las tareas de esa fecha en la Vista Detallada de la Fecha.

2. **Gestión de Tareas:**
    - **Añadir Tarea:** Haz clic en el botón de añadir para crear una nueva tarea. Ingresa el nombre de la tarea, la hora de inicio y la hora de finalización.
    - **Editar Tarea:** Toca una tarea existente para editar sus detalles.
    - **Eliminar Tarea:** Mantén presionada una tarea para eliminarla.

## Configuración de Firebase

Sort It integra servicios de Firebase para mejorar la funcionalidad. Sigue estos pasos para configurarlo:

1. **Crear un Proyecto en Firebase:**
    - Navega a la [Consola de Firebase](https://console.firebase.google.com/) y crea un nuevo proyecto.

2. **Añadir una Aplicación Android al Proyecto:**
    - En la visión general de tu proyecto Firebase, haz clic en el ícono de Android para añadir una aplicación Android.
    - Ingresa el nombre del paquete de tu aplicación (por ejemplo, `com.example.sortit`).
    - Sigue los pasos para descargar el archivo `google-services.json` y colócalo en el directorio `app/` como se describe en la sección [Instalación](#instalación).

3. **Añadir Dependencias del SDK de Firebase:**
    - Asegúrate de que los archivos `build.gradle` de tu proyecto incluyan las dependencias necesarias de Firebase.

   **build.gradle a nivel de proyecto:**

       buildscript {
           dependencies {
               // Añade esta línea
               classpath 'com.google.gms:google-services:4.3.15'
           }
       }

   **build.gradle a nivel de app:**

       apply plugin: 'com.android.application'
       apply plugin: 'com.google.gms.google-services' // Añade esta línea al final

       dependencies {
           // Añade las dependencias de Firebase según sea necesario
           implementation 'com.google.firebase:firebase-auth:21.1.0'
           implementation 'com.google.firebase:firebase-firestore:24.2.0'
           // ... Otras dependencias ...
       }

4. **Sincronizar el Proyecto:**
    - Después de añadir las dependencias, haz clic en `Sync Now` para aplicar los cambios.

## Contribuciones

¡Las contribuciones son bienvenidas! Para contribuir a Sort It, sigue estos pasos:

1. **Haz un Fork del Repositorio:**
    - Haz clic en el botón `Fork` en la esquina superior derecha de la página del repositorio.

2. **Clona tu Fork:**

       git clone https://github.com/IvanTicona/SortIt.git

3. **Crea una Rama de Funcionalidad:**

       git checkout -b feature/nombre-de-tu-funcionalidad

4. **Haz Commit de tus Cambios:**

       git commit -m "Añade tu funcionalidad"

5. **Haz Push a tu Fork:**

       git push origin feature/nombre-de-tu-funcionalidad

6. **Crea un Pull Request:**
    - Navega a tu fork en GitHub y haz clic en `Compare & pull request`.