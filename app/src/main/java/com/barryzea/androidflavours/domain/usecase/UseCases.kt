package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.domain.entities.DomainMovie

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
interface UseCases {
    suspend fun fetchMovies(page:Int):TmdbResponse<DomainMovie>
    suspend fun searchMovie(searchValue:String):TmdbResponse<DomainMovie>
    suspend fun fetchGenres(): TmdbResponse<Genres>
    suspend fun fetchMoviesByGenre(genreId:Int, page:Int):TmdbResponse<DomainMovie>
}