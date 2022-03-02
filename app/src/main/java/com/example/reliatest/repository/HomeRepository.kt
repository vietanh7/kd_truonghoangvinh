package com.example.reliatest.repository

import androidx.lifecycle.LiveData
import com.example.reliatest.model.Feed
import com.example.reliatest.vo.Resource

interface HomeRepository {
    suspend fun getFeeds(): LiveData<Resource<ArrayList<Feed>>>
}