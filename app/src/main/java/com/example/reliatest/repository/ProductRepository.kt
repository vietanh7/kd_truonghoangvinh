package com.example.reliatest.repository

import androidx.lifecycle.LiveData
import com.example.reliatest.model.Product
import com.example.reliatest.param.SearchProductParam
import com.example.reliatest.vo.ReliaResource

interface ProductRepository {
    suspend fun getProducts(): LiveData<ReliaResource<ArrayList<Product>>>

    suspend fun searchProducts(param: SearchProductParam): LiveData<ReliaResource<Product>>
}