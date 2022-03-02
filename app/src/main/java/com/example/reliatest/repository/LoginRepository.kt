package com.example.reliatest.repository

import androidx.lifecycle.LiveData
import com.example.reliatest.model.User
import com.example.reliatest.param.LoginParam
import com.example.reliatest.vo.Resource

interface LoginRepository {
    suspend fun login(param: LoginParam): LiveData<Resource<User>>
}