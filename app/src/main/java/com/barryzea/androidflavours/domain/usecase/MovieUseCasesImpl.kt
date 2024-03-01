package com.barryzea.androidflavours.domain.usecase

import com.barryzea.androidflavours.common.handleRequest
import com.barryzea.androidflavours.data.entities.Cast
import com.barryzea.androidflavours.data.entities.Genres
import com.barryzea.androidflavours.data.entities.TmdbResponse
import com.barryzea.androidflavours.data.entities.TmdbResult
import com.barryzea.androidflavours.data.entities.Trailers
import com.barryzea.androidflavours.data.repository.Repository

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 31/01/2024.
 **/
class MovieUseCasesImpl(private val repository: Repository):MovieUseCases {
    override suspend fun fetchMovies(genreId:Int?,page: Int):TmdbResponse<TmdbResult> {
      return handleRequest { repository.fetchMovies(genreId, page)}
    }
    override suspend fun searchMovie(searchValue: String,page:Int?):TmdbResponse<TmdbResult> {
      return handleRequest { repository.searchMovie(searchValue, page) }
    }
    override suspend fun fetchMoviesSortedBy(
        sortValue: String,
        page: Int
    ): TmdbResponse<TmdbResult> {
       return  handleRequest { repository.fetchMoviesSortedBy(sortValue,page) }
    }

    override suspend fun fetchGenres(): TmdbResponse<Genres> {
       return handleRequest { repository.fetchGenres() }
    }

    override suspend fun fetchCredits(idMovie: Int): TmdbResponse<Cast> {
        return handleRequest { repository.fetchCredits(idMovie) }
    }

    override suspend fun fetchTrailers(idMovie: Int): TmdbResponse<Trailers> {
        return handleRequest { repository.fetchTrailers(idMovie) }
    }

}

