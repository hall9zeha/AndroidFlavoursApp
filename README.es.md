# AndroidFlavoursApp
<img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/app/src/free/res/mipmap-xxxhdpi/ic_launcher.webp"  alt="drawing" width="24%" height="24%"/>|<img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/app/src/paid/res/mipmap-xxxhdpi/ic_launcher.webp"  alt="drawing" width="24%" height="24%"/>

Aplicación de Android escrita en Kotlin que usa la [API TMDB movies](https://developer.themoviedb.org/reference/intro/getting-started) para mostrar una lista de películas. Implementa diferentes variantes (flavors) para adaptarse a diversas necesidades y preferencias de los usuarios. 

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/README.md)

## Objetivo
Aprender el uso y funcionamiento de variantes en una aplicación Android que comparten código en común y administrar las diferencias entre las variantes de la aplicación.
## Paquetes :card_file_box:
* Main
* Free (flavor)
* Paid (flavor) el nombre solo es descriptivo, no hay que pagar nada para usarlo :D
## Pre-requisitos :gear:
* Java jdk 17
* Android sdk 34

Importante: no olvide poner su clave de API en el archivo ```local.properties``` en el proyecto, de la siguiente manera:
```xml
    MY_API_KEY = "Reemplazar-este-texto-por-tu-clave"
```
Para seleccionar una variante y trabajar sobre ella puede hacerlo desde la vista de proyecto que nos muestra todos los paquetes.
La vista de Android solo mostrará la variante seleccionada por defecto, así que para mostrar los paquetes y recursos de una variante en específico selecciónela de la pestaña buil variants en Android Studio.

<p align="left" width="40%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/select_variant.jpg"  alt="drawing" width="40%" height="40%"/></p>

## Descargar apk demo 📂 [click aquí](https://github.com/hall9zeha/AndroidFlavoursApp/raw/main/docs/demo/tmdb-pro.apk)

## Estado del proyecto
![Badge Terminado](https://img.shields.io/badge/STATUS-%20TERMINADO-green)

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
* [Safe Args](https://developer.android.com/guide/navigation/use-graph/safe-args)
* [Shimmer layout](https://github.com/facebookarchive/shimmer-android)
* [ViewModel](https://developer.android.com/jetpack/androidx/releases/lifecycle)

## Funciones :memo:
* Free(flavor)
    - Muestra películas (recientes, populares, mejor valorados, etc.)
    - Posibilidad de filtrar por géneros
    - Detalle de película
    - Buscar película
    - Configuración
* Paid(flavor)
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

# License
```xml
The MIT License (MIT)

Copyright (c) 2024 Barry zea H.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
