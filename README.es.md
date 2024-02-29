# AndroidFlavoursApp
<img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/app/src/free/res/mipmap-xxxhdpi/ic_launcher.webp"  alt="drawing" width="24%" height="24%"/>|<img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/app/src/paid/res/mipmap-xxxhdpi/ic_launcher.webp"  alt="drawing" width="24%" height="24%"/>

Aplicación de Android escrita en Kotlin que usa la [API TMDB movies](https://developer.themoviedb.org/reference/intro/getting-started) para mostrar una lista de películas. Implementa diferentes variantes (flavours) para adaptarse a diversas necesidades y preferencias de los usuarios. 

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/README.md)

## Objetivo
Aprender el uso y funcionamiento de variantes en una aplicación Android que comparten código en común y administrar las diferencias entre las variantes de la aplicación.
## Paquetes :card_file_box:
* Main
* Free (flavour)
* Paid (flavour) el nombre solo es descriptivo, no hay que pagar nada para usarlo :D
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

|<p align="center">Free(splash screen)</p>|<p align="center">Paid(splash screen)</p>|<p align="center">Free</p>|
|--|--|--|
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen1.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen2.jpg" alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen3.jpg"  alt="drawing" width="70%" height="70%"/></p>
|<p align="center">Paid</p>|<p align="center">Free/Paid</p>|<p align="center">Free/Paid</p>|
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen4.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen5.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen6.jpg"  alt="drawing" width="70%" height="70%"/></p>
|<p align="center">Free/Paid</p>|<p align="center">Paid</p>|<p align="center">Paid</p>|
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen7.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen8.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen9.jpg"  alt="drawing" width="70%" height="70%"/></p>
|<p align="center">Paid</p>|<p align="center">Paid</p>||
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen10.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/screen11.jpg"  alt="drawing" width="70%" height="70%"/></p>
