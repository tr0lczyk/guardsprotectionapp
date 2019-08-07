package com.example.guardsprotectionapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    val Access_token: String,
    val Eid: String,
    val Expires_in: Long,
    val UserName: String
) : Parcelable