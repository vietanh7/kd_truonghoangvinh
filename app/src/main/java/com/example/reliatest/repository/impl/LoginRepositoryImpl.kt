package com.example.reliatest.repository.impl

import androidx.lifecycle.LiveData
import com.example.reliatest.api.ApiService
import com.example.reliatest.data.remote.NetworkBoundResource
import com.example.reliatest.model.ObjectResponse
import com.example.reliatest.model.User
import com.example.reliatest.param.LoginParam
import com.example.reliatest.repository.LoginRepository
import com.example.reliatest.vo.Resource
import retrofit2.Response

class LoginRepositoryImpl(val api: ApiService) : LoginRepository {

    override suspend fun login(param: LoginParam): LiveData<Resource<User>> {
        return object : NetworkBoundResource<ObjectResponse<User>, User>() {
            override suspend fun createCall(): Response<ObjectResponse<User>> = api.login(param)

            override fun processResponse(response: ObjectResponse<User>): User? = response.data
        }.build().asLiveData()
    }
}