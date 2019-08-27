package com.technoblizz.newsapp.api

import com.technoblizz.newsapp.model.ModelNews

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestInterface {

    @GET("everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Observable<ModelNews>

    @GET("top-headlines")
    fun getHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pagesize: Int?
    ): Observable<ModelNews>


}
