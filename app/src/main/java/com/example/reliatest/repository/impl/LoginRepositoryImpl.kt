package com.example.reliatest.repository.impl

import androidx.lifecycle.LiveData
import com.example.reliatest.api.ApiService
import com.example.reliatest.data.remote.ReliaNetworkBoundResource
import com.example.reliatest.model.LoginResponse
import com.example.reliatest.param.LoginParam
import com.example.reliatest.repository.LoginRepository
import com.example.reliatest.vo.ReliaResource
import retrofit2.Response

class LoginRepositoryImpl(val api: ApiService) : LoginRepository {

    override suspend fun login(param: LoginParam): LiveData<ReliaResource<LoginResponse>> {
        return object : ReliaNetworkBoundResource<LoginResponse>() {
            override suspend fun createCall(): Response<LoginResponse> = api.login(param)

        }.build().asLiveData()
    }

}