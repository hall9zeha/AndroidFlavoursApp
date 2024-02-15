package com.barryzea.androidflavours.domain.entities

import com.barryzea.androidflavours.data.entities.RequestToken
import com.barryzea.androidflavours.data.entities.Session

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 15/02/2024.
 **/
data class DomainAuth(
    val success:Boolean = false,
    val token:String?=""
)
fun RequestToken.toDomain() = DomainAuth(
    success = success,
    token = requestToken
)
fun Session.toDomain() = DomainAuth(
    success = success,
    token = sessionId
)
