package com.example.reliatest.repository

import androidx.lifecycle.LiveData
import com.example.reliatest.model.LoginResponse
import com.example.reliatest.param.LoginParam
import com.example.reliatest.vo.ReliaResource

interface LoginRepository {
    suspend fun login(param: LoginParam): LiveData<ReliaResource<LoginResponse>>

}