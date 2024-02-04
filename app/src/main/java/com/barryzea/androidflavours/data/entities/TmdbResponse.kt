package com.barryzea.androidflavours.data.entities

import com.barryzea.androidflavours.domain.entities.DomainMovie

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/

sealed class TmdbResponse {
    class Success(val tmdbResult: DomainMovie): TmdbResponse()
    class Error(val msg:String): TmdbResponse()
}