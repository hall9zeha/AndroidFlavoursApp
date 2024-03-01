package com.barryzea.androidflavours.common

import com.barryzea.androidflavours.data.entities.TmdbResponse
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/03/2024.
 **/

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