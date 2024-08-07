package com.example.beritakini.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val BASE_URL = "https://newsapi.org/v2/"

    fun create(): APIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIService::class.java)
    }
}
