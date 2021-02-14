package com.resonatestudios.pushupplus.controller

import com.resonatestudios.pushupplus.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiController {
    @GET("top-headlines")
    fun getHeadlines(@Query("country") country: String?,
                     @Query("category") category: String?,
                     @Query("apiKey") apiKey: String?): Call<NewsResponse?>?
}