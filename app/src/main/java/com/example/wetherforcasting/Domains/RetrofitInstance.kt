package com.example.wetherforcasting.Domains

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {

        private var CONNECTION_TIMEOUT = 60L
        private var READ_TIMEOUT = 60L
        private var WRITE_TIMEOUT = 60L
        private var CALL_TIMEOUT = 60L

        private val retrofitLogin by lazy {
            val login = HttpLoggingInterceptor()
            login.setLevel(HttpLoggingInterceptor.Level.BODY)

            val gsonNullable = GsonBuilder()
                .setLenient()
                .serializeNulls()
                .create()

            val client = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(login)
                .build()

            Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonNullable))
                .client(client)
                .build()
        }

        val commonApiService: ApiService by lazy {
            retrofitLogin.create(ApiService::class.java)
        }

    }

}