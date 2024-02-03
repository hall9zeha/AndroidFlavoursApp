package com.barryzea.androidflavours.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.data.entities.TmdbMovie
import com.google.android.material.textview.MaterialTextView

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 03/02/2024.
 * Copyright (c)  All rights reserved.
 **/

class MovieDetailInfo @JvmOverloads constructor(
    context:Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int=0
): MaterialTextView(context,attrs, defStyleAttr) {
    fun setMovieInfo(movie:TmdbMovie)=with(movie){
        text= buildSpannedString {
            bold{append(context.getString(R.string.original_language))}
            appendLine(originalLanguage)

            bold{append(context.getString(R.string.original_title))}
            appendLine(originalTitle)

            bold{append(context.getString(R.string.release_date))}
            appendLine(releaseDate)

            bold{append(context.getString(R.string.popularity))}
            appendLine(popularity.toString())

            bold{append(context.getString(R.string.vote_average))}
            appendLine(voteAverage.toString())
        }
    }
}