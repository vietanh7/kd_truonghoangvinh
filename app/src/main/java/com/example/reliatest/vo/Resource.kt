package com.example.reliatest.vo

sealed class Resource<out T> {
    class Success<T>(val data: T? = null) : Resource<T>()
    class Error(val message: String, val code: Int) : Resource<Nothing>()
}