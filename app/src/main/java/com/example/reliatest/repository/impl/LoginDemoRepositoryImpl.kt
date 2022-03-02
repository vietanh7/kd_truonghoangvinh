package com.example.reliatest.repository.impl

import androidx.lifecycle.LiveData
import com.example.reliatest.api.ApiService
import com.example.reliatest.data.remote.NetworkBoundResource
import com.example.reliatest.model.ObjectResponse
import com.example.reliatest.model.User
import com.example.reliatest.param.LoginDemoParam
import com.example.reliatest.repository.LoginDemoRepository
import com.example.reliatest.vo.Resource
import retrofit2.Response

class LoginDemoRepositoryImpl(val api: ApiService) : LoginDemoRepository {

    override suspend fun login(param: LoginDemoParam): LiveData<Resource<User>> {
        return object : NetworkBoundResource<ObjectResponse<User>, User>() {
            override suspend fun createCall(): Response<ObjectResponse<User>> = api.loginDemo(param)

            override fun processResponse(response: ObjectResponse<User>): User? = response.data
        }.build().asLiveData()
    }
}