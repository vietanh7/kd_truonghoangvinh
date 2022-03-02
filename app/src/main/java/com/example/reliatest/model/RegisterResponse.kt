package com.example.reliatest.model

data class RegisterResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val data: RegisterData? = null,
    val email: ArrayList<String>? = null,
)

data class RegisterData(
    val email: String? = null,
    val updated_at: String? = null,
    val created_at: String? = null,
    val id: Int? = null,
)