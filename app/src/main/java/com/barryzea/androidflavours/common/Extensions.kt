package com.barryzea.androidflavours.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/02/2024.
 **/

fun ImageView.loadUrl(url:String)=
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(this)