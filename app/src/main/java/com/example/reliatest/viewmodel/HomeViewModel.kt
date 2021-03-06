package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.reliatest.base.BaseViewModel
import com.example.reliatest.model.Feed
import com.example.reliatest.repository.HomeRepository
import com.example.reliatest.vo.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {

    val feedLiveData = MediatorLiveData<ArrayList<Feed>?>()

    fun getFeeds() {
        viewModelScope.launch {
            feedLiveData.addSource(repository.getFeeds()) {
                when (it) {
                    is Resource.Success -> {
                        feedLiveData.value = it.data
                    }
                    is Resource.Error -> {
                        networkError.value = Pair(it.message, it.code)
                    }
                }
            }
        }
    }
}