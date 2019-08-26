package com.technoblizz.newsapp.api

import com.technoblizz.newsapp.BuildConfig

import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataSource {

    private var service: RequestInterface? = null

    fun getDataSource(): RequestInterface? {
        if (service == null) {
            val builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

            val okHttpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(loggingInterceptor)
            }
            val okHttpClient = okHttpClientBuilder
                .readTimeout(25, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .header("Authorization", "b5705aa5ea6f4062980f81d68940071b")
                            .build()
                    )
                }
                .build()
            val retrofit = builder.baseUrl(URL.BASE_URL).client(okHttpClient).build()
            service = retrofit.create(RequestInterface::class.java)
        }
        return service
    }

}
