package com.barryzea.androidflavours.data.entities

import androidx.annotation.Keep
import com.barryzea.androidflavours.domain.entities.DomainMovie

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

/*
* A partir de  Android Gradle plugin 8.x  "android.enableR8.fullMode" está habilitado por defecto haciendo una ofuscación
* de código más agresiva, por eso debemos evitar que las clases que tendrán cambios en tiempo de ejecución (Reflection)
* así como las llamadas a cuaquier API que tengan el mismo comportamiento no sean ofuscadas.
*
* O simplemente puede colocarse "android.enableR8.fullMode = false" en gradle.properties
*
* (revisar el archivo de proguard para ver las reglas agregadas)
* */
@Keep
sealed class TmdbResponse<out T:Any> {
    class Success<out T:Any>(val tmdbResult: T): TmdbResponse<T>()
    class Error(val msg:String): TmdbResponse<Nothing>()
}