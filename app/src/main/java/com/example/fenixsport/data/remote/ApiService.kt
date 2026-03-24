package com.example.fenixsport.data.remote

import com.example.fenixsport.data.local.FakeData
import com.example.fenixsport.data.model.Product

interface ApiService {
    suspend fun getProducts(): List<Product>
}

class FakeApiService : ApiService {
    override suspend fun getProducts(): List<Product> = FakeData.products
}