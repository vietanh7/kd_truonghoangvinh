package com.example.reliatest.repository

import androidx.lifecycle.LiveData
import com.example.reliatest.model.Product
import com.example.reliatest.vo.ReliaResource

interface ProductRepository {
    suspend fun getProducts(): LiveData<ReliaResource<ArrayList<Product>>>

}