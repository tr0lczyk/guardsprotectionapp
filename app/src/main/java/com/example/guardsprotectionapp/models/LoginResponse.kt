package com.example.guardsprotectionapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(val role: String,
                         val accountId: String,
                         val isLogged: Boolean,
                         val name: String,
                         val token: String?,
                         val expiryDate: String) : Parcelable