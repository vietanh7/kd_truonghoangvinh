package com.example.reliatest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feed(
    val id: Int,
    val type: String,
    val photos: List<Photo>,
    val title: String,
    val description: String
) : Parcelable

@Parcelize
data class Photo(
    val id: Int,
    val url: Url,
    val user: User
) : Parcelable

@Parcelize
data class Url(
    val original: String,
    val medium: String,
    val thumb: String
) : Parcelable