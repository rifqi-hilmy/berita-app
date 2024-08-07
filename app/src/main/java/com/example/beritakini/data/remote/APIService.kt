package com.example.beritakini.data.remote

import com.example.beritakini.data.response.ArticlesItem
import com.example.beritakini.data.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface   APIService {

    @GET("top-headlines?sources=bbc-news&apiKey=e994882525ee4c02bd7ea6ec4f7f1e98")
    fun getNews() : Call <NewsResponse>

    @GET
    fun getNewsDetail(@Url url: String): Call<ArticlesItem>
}