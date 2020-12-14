package com.example.itunes.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

//    // create logger
//    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//
//    // create Okhttp Client
//    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)
//
//    // create retrofit builder
//    private val builder = Retrofit.Builder()
//        .baseUrl(URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttp.build())
//
//    // create retrofit instance
//    private val retrofit = builder.build()

    private val clientBuilder = OkHttpClient.Builder()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientBuilder.build())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

}