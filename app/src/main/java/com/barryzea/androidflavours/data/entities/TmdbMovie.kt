package com.barryzea.androidflavours.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 03/02/2024.
 **/

/*
* Si utilizamos ofuscación de código con minifyEnabled=true en el proyecto, debemos evitar que se ofusque esta clase
* ya que es utilizada como referencia para nuestro tipo de argumento a ser enviado por Safe Args en nuestro NavGraph:
*
* app:argType="com.barryzea.androidflavours.data.entities.TmdbMovie"
*
* al ofuscar el código el nombre de esta clase cambiará y la referencia hacia la misma se perderá provocando que nuestra
* app se rompa.
* Por eso debemos evitar que sea ofuscada agregándola a proguard-rules.pro o con la anotación @Keep
*
* */

@Parcelize
data class TmdbMovie(
    @SerializedName("adult")val adult:Boolean=false,
    @SerializedName("backdrop_path") val backdropPath:String?="",
    @SerializedName("genre_ids")val genreIds:List<Int> = listOf(),
    @SerializedName("id")val id: Int=0,
    @SerializedName("original_language") val originalLanguage: String="",
    @SerializedName("original_title") val originalTitle: String="",
    @SerializedName("overview")val overview: String="",
    @SerializedName("popularity")val popularity: Double=0.0,
    @SerializedName("poster_path") val posterPath: String?="",
    @SerializedName("release_date") val releaseDate: String="",
    @SerializedName("title")val title: String="",
    @SerializedName("video")val video: Boolean=false,
    @SerializedName("vote_average") val voteAverage: Double=0.0,
    @SerializedName("vote_count") val voteCount: Int=0
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TmdbMovie

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}