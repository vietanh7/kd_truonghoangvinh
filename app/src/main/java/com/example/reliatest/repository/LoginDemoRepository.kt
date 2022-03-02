package com.example.reliatest.repository

import androidx.lifecycle.LiveData
import com.example.reliatest.model.User
import com.example.reliatest.param.LoginDemoParam
import com.example.reliatest.vo.Resource

interface LoginDemoRepository {
    suspend fun login(param: LoginDemoParam): LiveData<Resource<User>>
}