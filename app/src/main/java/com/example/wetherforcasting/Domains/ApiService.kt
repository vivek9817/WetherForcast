package com.example.wetherforcasting.Domains

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("https://api.openweathermap.org/data/2.5/weather?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getRequest(): Any

}