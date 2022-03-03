package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.reliatest.base.BaseViewModel
import com.example.reliatest.model.Product
import com.example.reliatest.param.SearchProductParam
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

    fun searchProducts(param: SearchProductParam) {
        viewModelScope.launch {
            productsLiveData.addSource(repository.searchProducts(param)) { resource ->
                when (resource) {
                    is ReliaResource.Success -> {
                        resource.data?.let {
                            if (it.message.isNullOrEmpty()) {
                                productsLiveData.value = arrayListOf(it)
                            } else {
                                productsLiveData.value = arrayListOf()
                                networkError.value = Pair(it.message, null)
                            }
                        }
                    }
                    is ReliaResource.Error -> Unit
                }
            }
        }
    }

}