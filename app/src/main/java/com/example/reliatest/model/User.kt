package com.example.reliatest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id : Int = 0,
    var email: String? = "",
    var username: String? = "",
    var access_token : String? = "",
    var profilePhoto: String? = "",
    var token: Token? = Token()
) : Parcelable

@Parcelize
data class Token(var token: String? = "") : Parcelable
