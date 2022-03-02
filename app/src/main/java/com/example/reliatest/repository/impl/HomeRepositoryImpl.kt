package com.example.reliatest.repository.impl

import androidx.lifecycle.LiveData
import com.example.reliatest.api.ApiService
import com.example.reliatest.data.remote.NetworkBoundResource
import com.example.reliatest.model.Feed
import com.example.reliatest.model.ListResponse
import com.example.reliatest.repository.HomeRepository
import com.example.reliatest.vo.Resource
import retrofit2.Response

class HomeRepositoryImpl(val api: ApiService): HomeRepository {
    override suspend fun getFeeds(): LiveData<Resource<ArrayList<Feed>>> {
        return object : NetworkBoundResource<ListResponse<Feed>, ArrayList<Feed>>() {
            override suspend fun createCall(): Response<ListResponse<Feed>> = api.getFeeds()

            override fun processResponse(response: ListResponse<Feed>): ArrayList<Feed>? = response.data
        }.build().asLiveData()
    }
}