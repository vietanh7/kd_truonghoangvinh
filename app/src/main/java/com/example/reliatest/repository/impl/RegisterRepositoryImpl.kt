package com.example.reliatest.repository.impl

import androidx.lifecycle.LiveData
import com.example.reliatest.api.ApiService
import com.example.reliatest.data.remote.ReliaNetworkBoundResource
import com.example.reliatest.model.RegisterResponse
import com.example.reliatest.param.RegisterParam
import com.example.reliatest.repository.RegisterRepository
import com.example.reliatest.vo.ReliaResource
import retrofit2.Response

class RegisterRepositoryImpl(val api: ApiService) : RegisterRepository {

    override suspend fun register(param: RegisterParam): LiveData<ReliaResource<RegisterResponse>> {
        return object : ReliaNetworkBoundResource<RegisterResponse>() {
            override suspend fun createCall(): Response<RegisterResponse> = api.register(param)

        }.build().asLiveData()
    }

}