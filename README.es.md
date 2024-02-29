# AndroidFlavoursApp
Aplicación de Android que usa la [API TMDB movies](https://developer.themoviedb.org/reference/intro/getting-started) para mostrar una lista de películas. Implementa diferentes variantes (flavours) para adaptarse a diversas necesidades y preferencias de los usuarios. 
## Paquetes :card_file_box:
* Main
* Free (flavour)
* Paid (flavour)
## Pre-requisitos :gear:
* Java jdk 17
* Android sdk 34

Importante: no olvide poner su clave de API en el archivo ```local.properties``` en el proyecto, de la siguiente manera:
```xml
    MY_API_KEY = "Reemplazar-este-texto-por-tu-clave"
```

## Estado del proyecto
![Badge Terminado](https://img.shields.io/badge/STATUS-%20TERMINADO-green)
## Download demo 📂 [click here](https://github.com/hall9zeha/Modularized-Android-App/raw/main/docs/demo/notepad-release.apk)

## Se utilizó :wrench:

* [Arquitectura MVVM](https://developer.android.com/jetpack/guide)
* [Android-splash-screen](https://developer.android.com/develop/ui/views/launch/splash-screen)
* [API TMDB movies](https://developer.themoviedb.org/reference/intro/getting-started)
* [Clean code](https://developer.android.com/topic/architecture)
* [ColorPickerView](https://github.com/skydoves/ColorPickerView)
* [Data-store-preferences](https://developer.android.com/topic/libraries/architecture/datastore)
* [Glide](https://developer.android.com/training/dependency-injection/hilt-android)
* [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines)
* [Lottie-Animations](https://lottiefiles.com/blog/working-with-lottie/getting-started-with-lottie-animations-in-android-app)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Navigation-component](https://developer.android.com/guide/navigation/navigation-getting-started)
* [OkHttp](https://square.github.io/okhttp/)
* [Retrofit](https://square.github.io/retrofit/)
* [Shimmer layout](https://github.com/facebookarchive/shimmer-android)
* [ViewModel](https://developer.android.com/jetpack/androidx/releases/lifecycle)

## Funciones :memo:
* Free(flavour)
    - Muestra películas (recientes, populares, mejor valorados, etc.)
    - Posibilidad de filtrar por géneros
    - Detalle de película
    - Buscar película
    - Configuración
* Paid(flavour)
    - Todo el contenido de la variante Free
    - Iniciar sesión en tmdb movies
    - Lista de favoritos de la cuenta de usuario
    - Lista de películas por ver de la cuenta de usuario
    - Agregar a lista de favoritos 
    - Agregar a la lista de películas por ver
## Capturas :framed_picture:
