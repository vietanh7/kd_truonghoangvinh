package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.reliatest.base.BaseViewModel
import com.example.reliatest.model.RegisterResponse
import com.example.reliatest.param.RegisterParam
import com.example.reliatest.repository.RegisterRepository
import com.example.reliatest.vo.ReliaResource
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel() {

    val registerLiveData = MediatorLiveData<RegisterResponse?>()

    fun register(param: RegisterParam) {
        viewModelScope.launch {
            registerLiveData.addSource(repository.register(param)) { resource ->
                when (resource) {
                    is ReliaResource.Success -> {
                        resource.data?.let {
                            if (it.success == true) {
                                registerLiveData.value = it
                            }
                        }
                    }
                    is ReliaResource.Error -> Unit
                }
            }
        }
    }
}