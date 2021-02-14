package com.resonatestudios.pushupplus.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.resonatestudios.pushupplus.Constants
import com.resonatestudios.pushupplus.R
import com.resonatestudios.pushupplus.adapter.NewsAdapter
import com.resonatestudios.pushupplus.controller.NewsApiController
import com.resonatestudios.pushupplus.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {
    private var recyclerViewNews: RecyclerView? = null
    private val newsApiController: NewsApiController? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerViewNews = view.findViewById(R.id.recycler_view_news)
        return view
    }

    override fun onStart() {
        super.onStart()
        setNews()
    }

    private fun startApi(): NewsApiController {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_DOMAIN)
                .addConverterFactory(GsonConverterFactory
                        .create(GsonBuilder()
                                .setLenient()
                                .create()))
                .build()
        return retrofit.create(NewsApiController::class.java)
    }

    private fun setNews() {
        val newsAdapter = NewsAdapter(context!!)
        val newsApiController = startApi()
        val newsResponseCall = newsApiController.getHeadlines("id", "health", Constants.API_KEY)
        newsResponseCall!!.enqueue(object : Callback<NewsResponse?> {
            override fun onResponse(call: Call<NewsResponse?>, response: Response<NewsResponse?>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    newsAdapter.articles = newsResponse!!.articles!!
                    recyclerViewNews!!.layoutManager = LinearLayoutManager(context)
                    recyclerViewNews!!.adapter = newsAdapter
                } else {
                    Log.e(TAG, "API consume failure with response")
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
                Log.e(TAG, "API consume failure with no response")
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val TAG = "NewsFragment"
    }
}