package com.example.reliatest.param

data class UpdateProductParam(
    val sku: String,
    val product_name: String,
    val qty: Int,
    val price: Int,
    val unit: String,
    val status: Int
)