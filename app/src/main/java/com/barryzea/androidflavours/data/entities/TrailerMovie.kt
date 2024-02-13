package com.barryzea.androidflavours.data.entities

import com.google.gson.annotations.SerializedName

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 13/02/2024.
 * Copyright (c)  All rights reserved.
 **/
data class TrailerMovie(
    @SerializedName("name")val name:String?="",
    @SerializedName("key")val key:String?="",
    @SerializedName("site")val site:String?="",
    @SerializedName("size")val size:Int=0,
    @SerializedName("id")val id:String?=""
)
