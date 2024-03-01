package com.barryzea.androidflavours.domain.entities

import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.data.entities.TmdbResult

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 04/02/2024.
 **/
data class DomainMovie(
    val page:Int=0,
    val totalPages:Int=0,
    val totalResult:Int=0,
    val movies:List<TmdbMovie> = arrayListOf()
)
fun TmdbResult.toDomain()=
    DomainMovie(
        page=page,
        totalPages = totalPages,
        totalResult = totalResult,
        movies=movies
    )


