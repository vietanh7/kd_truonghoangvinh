package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.reliatest.base.BaseViewModel
import com.example.reliatest.model.LoginResponse
import com.example.reliatest.param.LoginParam
import com.example.reliatest.repository.LoginRepository
import com.example.reliatest.vo.ReliaResource
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : BaseViewModel() {

    val loginLiveData = MediatorLiveData<LoginResponse?>()

    fun login(param: LoginParam) {
        viewModelScope.launch {
            loginLiveData.addSource(repository.login(param)) { resource ->
                when (resource) {
                    is ReliaResource.Success -> {
                        resource.data?.let {
                            loginLiveData.value = it
                        }
                    }
                    is ReliaResource.Error -> Unit
                }
            }
        }
    }
}