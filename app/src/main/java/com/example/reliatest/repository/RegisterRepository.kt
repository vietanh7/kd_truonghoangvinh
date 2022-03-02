package com.example.reliatest.repository

import androidx.lifecycle.LiveData
import com.example.reliatest.model.RegisterResponse
import com.example.reliatest.param.RegisterParam
import com.example.reliatest.vo.ReliaResource

interface RegisterRepository {
    suspend fun register(param: RegisterParam): LiveData<ReliaResource<RegisterResponse>>

}