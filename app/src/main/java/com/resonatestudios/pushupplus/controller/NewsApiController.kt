package com.resonatestudios.pushupplus.controller;

import com.resonatestudios.pushupplus.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiController {
    @GET("top-headlines")
    Call<NewsResponse> getHeadlines(@Query("country") String country,
                                    @Query("category") String category,
                                    @Query("apiKey") String apiKey);
}
