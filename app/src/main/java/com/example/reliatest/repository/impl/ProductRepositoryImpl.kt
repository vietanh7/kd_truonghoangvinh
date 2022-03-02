package com.example.reliatest.repository.impl

import androidx.lifecycle.LiveData
import com.example.reliatest.api.ApiService
import com.example.reliatest.data.remote.ReliaNetworkBoundResource
import com.example.reliatest.model.Product
import com.example.reliatest.repository.ProductRepository
import com.example.reliatest.vo.ReliaResource
import retrofit2.Response

class ProductRepositoryImpl(val api: ApiService) : ProductRepository {

    override suspend fun getProducts(): LiveData<ReliaResource<ArrayList<Product>>> {
        return object : ReliaNetworkBoundResource<ArrayList<Product>>() {
            override suspend fun createCall(): Response<ArrayList<Product>> = api.getProducts()

        }.build().asLiveData()
    }

}