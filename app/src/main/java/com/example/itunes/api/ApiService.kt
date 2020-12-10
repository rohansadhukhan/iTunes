package com.example.itunes.api

import com.example.itunes.model.Artist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    fun getArtists(
        @Query("term") term : String
    ) : Call<Artist>

}