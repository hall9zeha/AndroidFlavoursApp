package com.barryzea.androidflavours.data.repository

import com.barryzea.androidflavours.data.entities.Cast
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.data.entities.Trailers
import com.barryzea.androidflavours.domain.entities.DomainMovie
import retrofit2.Response

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
interface Repository {
    suspend fun fetchMovies(genreId:Int?,page:Int):Response<TmdbResult>
    suspend fun searchMovie(searchValue:String,page:Int?):Response<DomainMovie>
    suspend fun fetchMoviesSortedBy(sortValue:String, page:Int):Response<TmdbResult>
    suspend fun fetchGenres():Response<Genres>
    suspend fun fetchCredits(idMovie:Int):Response<Cast>

    suspend fun fetchTrailers(idMovie:Int):Response<Trailers>

}