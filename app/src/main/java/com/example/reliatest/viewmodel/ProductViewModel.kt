package com.example.reliatest.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.reliatest.base.BaseViewModel
import com.example.reliatest.model.Product
import com.example.reliatest.param.AddProductParam
import com.example.reliatest.param.DeleteProductParam
import com.example.reliatest.param.SearchProductParam
import com.example.reliatest.param.UpdateProductParam
import com.example.reliatest.repository.ProductRepository
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.vo.ReliaResource
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : BaseViewModel() {

    val productsLiveData = MediatorLiveData<ArrayList<Product>?>()
    val addProductLiveData = MediatorLiveData<Product>()
    val updateProductLiveData = MediatorLiveData<Product?>()

    var isSearching = false

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

    fun addProducts(param: AddProductParam) {
        viewModelScope.launch {
            addProductLiveData.addSource(repository.addProducts(param)) { resource ->
                when (resource) {
                    is ReliaResource.Success -> {
                        resource.data?.let {
                            if (it.message.isNullOrEmpty()) {
                                if (!isSearching){
                                    val temp = productsLiveData.value
                                    temp?.add(it)
                                    productsLiveData.value = temp
                                }
                                addProductLiveData.value = it
                            } else {
                                PopupUtil.showPopupError(it.message)
                            }
                        }
                    }
                    is ReliaResource.Error -> Unit
                }
            }
        }
    }

    fun updateProducts(param: UpdateProductParam) {
        viewModelScope.launch {
            updateProductLiveData.addSource(repository.updateProducts(param)) { resource ->
                when (resource) {
                    is ReliaResource.Success -> {
                        resource.data?.let { product ->
                            if (product.message.isNullOrEmpty()) {
                                val temp = productsLiveData.value
                                val index = temp?.indexOfFirst { it.sku == product.sku }
                                index?.let {
                                    temp[index] = product
                                    productsLiveData.value = temp
                                }
                                updateProductLiveData.value = product

                            } else {
                                PopupUtil.showPopupError(product.message)
                            }
                        }
                    }
                    is ReliaResource.Error -> Unit
                }
            }
        }
    }

    fun deleteProducts(param: DeleteProductParam) {
        viewModelScope.launch {
            productsLiveData.addSource(repository.deleteProducts(param)) { resource ->
                when (resource) {
                    is ReliaResource.Success -> {
                        resource.data?.let { product ->
                            if (product.message.isNullOrEmpty()) {
                                val temp = productsLiveData.value
                                val index = temp?.indexOfFirst { it.sku == product.sku }
                                temp?.removeAt(index ?: return@let)
                                productsLiveData.value = temp
                            } else {
                                PopupUtil.showPopupError(product.message)
                            }
                        }
                    }
                    is ReliaResource.Error -> Unit
                }
            }
        }
    }

}