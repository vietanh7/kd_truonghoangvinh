package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reliatest.model.User
import com.example.reliatest.param.LoginParam
import com.example.reliatest.repository.LoginRepository
import com.example.reliatest.vo.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    val loginLiveData = MediatorLiveData<User?>()

    val errorLiveData = MutableLiveData<Pair<String, Int?>>()

    fun login(param: LoginParam) {
        viewModelScope.launch {
            loginLiveData.addSource(repository.login(param)) {
                when (it) {
                    is Resource.Success -> {
                        loginLiveData.value = it.data
                    }
                    is Resource.Error -> {
                        errorLiveData.value = Pair(it.message, it.code)
                    }
                }
            }
        }
    }
}