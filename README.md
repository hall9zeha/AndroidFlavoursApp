# AndroidFlavoursApp
<img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/app/src/free/res/mipmap-xxxhdpi/ic_launcher.webp"  alt="drawing" width="24%" height="24%"/>|<img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/app/src/paid/res/mipmap-xxxhdpi/ic_launcher.webp"  alt="drawing" width="24%" height="24%"/>

Android application written in Kotlin that utilizes the [API TMDB movies](https://developer.themoviedb.org/reference/intro/getting-started) to display a list of movies. It implements different variants (flavors) to cater to various user needs and preferences.

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/README.md) [![es](https://img.shields.io/badge/lang-es-yellow.svg)](https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/README.es.md)

## Objective
Learn how to use and manage variants in an Android application that share common code, and handle the differences between the application's variants.
## Packages :card_file_box:
* Main
* Free (flavor)
* Paid (flavor) - The name is descriptive only; you don't have to pay anything to use it! :D
## Requirements :gear:
* Java jdk 17
* Android sdk 34

Important: Don't forget to add your API key in the ```local.properties``` file in the project, like this:
```xml
    MY_API_KEY = "Replace-this-text-with-your-key"
```
To select a variant and work on it, you can do so from the project view, which displays all the packages. The Android view will only show the default selected variant. To display the packages and resources of a specific variant, select it from the "Build Variants" tab in Android Studio.

<p align="left" width="40%"><img src="https://github.com/hall9zeha/AndroidFlavoursApp/blob/main/docs/screenshots/select_variant.jpg"  alt="drawing" width="40%" height="40%"/></p>

## Download apk demo 📂 [click here](https://github.com/hall9zeha/AndroidFlavoursApp/raw/main/docs/demo/tmdb-pro.apk)

## Project status
![Badge Terminado](https://img.shields.io/badge/STATUS-%20FINISHED-green)

## It was used :wrench:

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

## Features :memo:
* Free(flavor)
    - Displays movies (recent, popular, top-rated, etc.)
    - Ability to filter by genres
    - Movie detail view
    - Movie search
Settings
* Paid(flavor)
    - All content from the Free variant
    - Login to TMDb Movies
    - User account favorites list
    - User account watchlist
    - Add to favorites list
    - Add to watchlist
## Screenshots :framed_picture:

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