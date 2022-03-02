package com.example.reliatest.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    val networkError = MutableLiveData<Pair<String, Int?>>()

}