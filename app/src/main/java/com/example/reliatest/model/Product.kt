package com.example.reliatest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val sku: String? = null,
    val product_name: String? = null,
    val qty: Int? = null,
    val price: Int? = null,
    val unit: String? = null,
    val image: String? = null,
    val status: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null,

    val success: Boolean? = null,
    val message: String? = null,
) : Parcelable