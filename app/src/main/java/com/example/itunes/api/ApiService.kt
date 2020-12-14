package com.example.itunes.api

import com.example.itunes.view.model.Artist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL: String = "https://itunes.apple.com/"

interface ApiService {

    @GET("search")
    fun getArtists(
        @Query("term") term: String
    ): Call<Artist>

}