package com.barryzea.androidflavours.domain.entities

import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.google.gson.annotations.SerializedName

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 04/02/2024.
 **/
data class DomainMovie(
    @SerializedName("page") val page:Int=0,
    @SerializedName("total_pages") val totalPages:Int=0,
    @SerializedName("total_result")val totalResult:Int=0,
    @SerializedName("results") val movies:List<TmdbMovie> = arrayListOf()
)
fun TmdbResult.toDomain()=
    DomainMovie(
        page=pages,
        totalPages = total_pages,
        totalResult = total_result,
        movies=movies
    )


