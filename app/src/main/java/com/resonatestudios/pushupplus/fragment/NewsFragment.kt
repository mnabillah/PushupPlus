package com.resonatestudios.pushupplus.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.resonatestudios.pushupplus.Constants;
import com.resonatestudios.pushupplus.R;
import com.resonatestudios.pushupplus.adapter.NewsAdapter;
import com.resonatestudios.pushupplus.controller.NewsApiController;
import com.resonatestudios.pushupplus.model.NewsResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";
    private RecyclerView recyclerViewNews;
    private NewsApiController newsApiController;
    private Context context;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerViewNews = view.findViewById(R.id.recycler_view_news);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setNews();
    }

    private NewsApiController startApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_DOMAIN)
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder()
                                .setLenient()
                                .create()))
                .build();

        return retrofit.create(NewsApiController.class);
    }

    private void setNews() {
        final NewsAdapter newsAdapter = new NewsAdapter(context);
        NewsApiController newsApiController = startApi();

        Call<NewsResponse> newsResponseCall = newsApiController.getHeadlines("id", "health", Constants.API_KEY);
        newsResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    if (newsResponse != null) {
                        newsAdapter.setArticles(newsResponse.getArticles());
                        recyclerViewNews.setLayoutManager(new LinearLayoutManager(context));
                        recyclerViewNews.setAdapter(newsAdapter);
                    }
                } else {
                    Log.e(TAG, "API consume failure with response");
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "API consume failure with no response");
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
