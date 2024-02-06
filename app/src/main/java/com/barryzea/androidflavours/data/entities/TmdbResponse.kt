package com.barryzea.androidflavours.data.entities

import com.barryzea.androidflavours.domain.entities.DomainMovie

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

sealed class TmdbResponse<out T:Any> {
    class Success<out T:Any>(val tmdbResult: T): TmdbResponse<T>()
    class Error(val msg:String): TmdbResponse<Nothing>()
}