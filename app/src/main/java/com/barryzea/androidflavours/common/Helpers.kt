package com.barryzea.androidflavours.common

import com.barryzea.androidflavours.data.entities.TmdbResponse
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/03/2024.
 **/
/*
* Esta función handleRequest es una función genérica  que toma un bloque de código como parámetro
* y devuelve un objeto TmdbResponse<T>, donde inline es una palabra clave que se utiliza para solicitar al compilador
* que copie el cuerpo de la función en el lugar donde se llama, en lugar de llamar a la función.
* Esto se utiliza para evitar la sobrecarga de llamadas de función y mejorar el rendimiento en tiempo de ejecución.
*
* Continuando con T que es un tipo genérico que puede ser cualquier tipo (Any),
* y reified es una palabra clave que se utiliza para indicar que el tipo genérico T debe ser conocido en tiempo de ejecución.
*
* Así esta función es utilizada para no tener que repetir el bloque de código try catch, y el manejo de la respuesta
* de nuestras llamadas a la API cuando es correcta o hay error.
*  */
inline fun <reified T:Any> handleRequest(block: () -> Response<T>): TmdbResponse<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            TmdbResponse.Success(response.body()!!)
        } else {
            TmdbResponse.Error(response.message())
        }
    } catch (e: Exception) {
        TmdbResponse.Error(e.message.toString())
    }
}
fun getYoutubeVideoPath(videoPath: String): String {
    return YOUTUBE_VIDEO_URL + videoPath
}
fun getYoutubeThumbnailPath(thumbnailPath: String): String {
    return "$YOUTUBE_THUMBNAIL_URL$thumbnailPath/default.jpg"
}