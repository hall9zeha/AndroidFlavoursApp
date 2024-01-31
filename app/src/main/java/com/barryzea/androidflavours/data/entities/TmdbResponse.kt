package com.barryzea.androidflavours.data.entities

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

sealed class TmdbResponse {
    class Success(val tmdbResult: TmdbResult): TmdbResponse()
    class Error(val msg:String): TmdbResponse()
}