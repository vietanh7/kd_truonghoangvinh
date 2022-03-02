package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.reliatest.base.BaseViewModel
import com.example.reliatest.model.Product
import com.example.reliatest.repository.ProductRepository
import com.example.reliatest.vo.ReliaResource
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : BaseViewModel() {

    val productsLiveData = MediatorLiveData<ArrayList<Product>?>()

    fun getProducts() {
        viewModelScope.launch {
            productsLiveData.addSource(repository.getProducts()) { resource ->
                when (resource) {
                    is ReliaResource.Success -> {
                        resource.data?.let {
                            productsLiveData.value = it
                        }
                    }
                    is ReliaResource.Error -> Unit
                }
            }
        }
    }
}