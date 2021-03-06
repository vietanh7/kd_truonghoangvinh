package com.example.reliatest.repository.impl

import androidx.lifecycle.LiveData
import com.example.reliatest.api.ApiService
import com.example.reliatest.data.remote.ReliaNetworkBoundResource
import com.example.reliatest.model.Product
import com.example.reliatest.param.AddProductParam
import com.example.reliatest.param.DeleteProductParam
import com.example.reliatest.param.SearchProductParam
import com.example.reliatest.param.UpdateProductParam
import com.example.reliatest.repository.ProductRepository
import com.example.reliatest.vo.ReliaResource
import retrofit2.Response

class ProductRepositoryImpl(val api: ApiService) : ProductRepository {

    override suspend fun getProducts(): LiveData<ReliaResource<ArrayList<Product>>> {
        return object : ReliaNetworkBoundResource<ArrayList<Product>>() {
            override suspend fun createCall(): Response<ArrayList<Product>> = api.getProducts()

        }.build().asLiveData()
    }

    override suspend fun searchProducts(param: SearchProductParam): LiveData<ReliaResource<Product>> {
        return object : ReliaNetworkBoundResource<Product>() {
            override suspend fun createCall(): Response<Product> = api.searchProduct(param)
        }.build().asLiveData()
    }

    override suspend fun addProducts(param: AddProductParam): LiveData<ReliaResource<Product>> {
        return object : ReliaNetworkBoundResource<Product>() {
            override suspend fun createCall(): Response<Product> = api.addProduct(param)
        }.build().asLiveData()
    }

    override suspend fun updateProducts(param: UpdateProductParam): LiveData<ReliaResource<Product>> {
        return object : ReliaNetworkBoundResource<Product>() {
            override suspend fun createCall(): Response<Product> = api.updateProduct(param)
        }.build().asLiveData()
    }

    override suspend fun deleteProducts(param: DeleteProductParam): LiveData<ReliaResource<Product>> {
        return object : ReliaNetworkBoundResource<Product>() {
            override suspend fun createCall(): Response<Product> = api.deleteProduct(param)
        }.build().asLiveData()
    }
}