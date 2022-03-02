package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.reliatest.base.BaseViewModel
import com.example.reliatest.model.User
import com.example.reliatest.param.LoginDemoParam
import com.example.reliatest.repository.LoginDemoRepository
import com.example.reliatest.vo.Resource
import kotlinx.coroutines.launch

class LoginDemoViewModel(private val repository: LoginDemoRepository) : BaseViewModel() {

    val loginLiveData = MediatorLiveData<User?>()

    fun login(param: LoginDemoParam) {
        viewModelScope.launch {
            loginLiveData.addSource(repository.login(param)) {
                when (it) {
                    is Resource.Success -> {
                        loginLiveData.value = it.data
                    }
                    is Resource.Error -> {
                        networkError.value = Pair(it.message, it.code)
                    }
                }
            }
        }
    }
}