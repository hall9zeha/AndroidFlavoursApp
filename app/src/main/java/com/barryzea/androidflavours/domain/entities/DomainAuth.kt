package com.barryzea.androidflavours.domain.entities

import com.barryzea.androidflavours.data.entities.PostResponse
import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session
import com.barryzea.androidflavours.data.entities.User

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/
data class DomainAuth(
    val success:Boolean = false,
    val token:String?="",
    val avatar:String?="",
    val id:Int?=0,
    val name:String?="",
    val username:String?=""
)
fun RequestToken.toDomain() = DomainAuth(
    success = success,
    token = requestToken
)
fun Session.toDomain() = DomainAuth(
    success = success,
    token = sessionId
)
fun User.toDomain()=DomainAuth(
    success = id > 0,
    avatar = avatar.avatarTmdb.avatarPath,
    id=id,
    name=name,
    username = username
)
fun PostResponse.toDomain()=DomainAuth(
    success = success
)
