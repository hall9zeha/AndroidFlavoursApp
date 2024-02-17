package com.barryzea.androidflavours.common

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.barryzea.androidflavours.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/02/2024.
 **/

fun ImageView.loadUrl(url:String, placeholder:Int=R.drawable.placeholder_movie)=
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(placeholder)
        .centerCrop()
        .into(this)
fun ImageView.loadRes(resource:Int,placeholder:Int=R.drawable.placeholder_movie)=
    Glide.with(this)
        .load(resource)
        .placeholder(placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(placeholder)
        .centerCrop()
        .into(this)
fun View.showSnackbar(msg:String, duration:Int=Snackbar.LENGTH_SHORT)=
    run { Snackbar.make(this,msg,duration).show() }