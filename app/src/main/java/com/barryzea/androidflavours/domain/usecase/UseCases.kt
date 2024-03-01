package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.Cast
import com.barryzea.androidflavours.data.entities.CharacterMovie
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.data.entities.TrailerMovie
import com.barryzea.androidflavours.data.entities.Trailers
import com.barryzea.androidflavours.domain.entities.DomainMovie

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
interface UseCases {
    suspend fun fetchMovies( genreId: Int?,page:Int):TmdbResponse<TmdbResult>
    suspend fun searchMovie(searchValue:String,page:Int?):TmdbResponse<TmdbResult>
    suspend fun fetchMoviesSortedBy(sortValue:String, page:Int):TmdbResponse<TmdbResult>
    suspend fun fetchGenres(): TmdbResponse<Genres>
    suspend fun fetchCredits(idMovie:Int):TmdbResponse<Cast>
    suspend fun fetchTrailers(idMovie:Int):TmdbResponse<Trailers>

}