package com.example.guardsprotectionapp.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    @Json(name = "Access_token")val accessToken: String,
    @Json(name = "Eid")val id: String,
    @Json(name = "Expires_in")val expiresIn: Long,
    @Json(name = "UserName")val userName: String
) : Parcelable