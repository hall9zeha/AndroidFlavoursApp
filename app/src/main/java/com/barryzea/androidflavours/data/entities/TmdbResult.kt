package com.barryzea.androidflavours.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 30/01/2024.
 **/
data class TmdbMovieResult(
    val pages:Int=0,
    @SerializedName("results")val movies:ArrayList<Movie> = arrayListOf<Movie>(),
    @SerializedName("total_pages")val total_pages:Int=0,
    @SerializedName("total_result")val total_result:Int=0)
@Parcelize
data class Movie(
    val adult:Boolean=false,
    @SerializedName("backdrop_path") val backdropPath:String?="",
    @SerializedName("genre_ids")val genreIds:List<Int> = listOf(),
    val id: Int=0,
    @SerializedName("original_language") val originalLanguage: String="",
    @SerializedName("original_title") val originalTitle: String="",
    val overview: String="",
    val popularity: Double=0.0,
    @SerializedName("poster_path") val posterPath: String="",
    @SerializedName("release_date") val releaseDate: String="",
    val title: String="",
    val video: Boolean=false,
    @SerializedName("vote_average") val voteAverage: Double=0.0,
    @SerializedName("vote_count") val voteCount: Int=0
):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
