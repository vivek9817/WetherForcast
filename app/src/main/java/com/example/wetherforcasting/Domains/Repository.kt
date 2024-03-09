package com.example.wetherforcasting.Domains

class Repository {

    private val apiService: ApiService? by lazy { RetrofitInstance.commonApiService }

    suspend fun getRequest() = apiService?.getRequest()

}