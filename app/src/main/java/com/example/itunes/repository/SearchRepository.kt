package com.example.itunes.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.itunes.api.ApiService
import com.example.itunes.api.BASE_URL
import com.example.itunes.model.Artist
import com.example.itunes.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchRepository(val application: Application) {

    val SongList = MutableLiveData<List<Result>>()

    fun searchArtist(artistName : String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.getArtists(artistName).enqueue(object : Callback<Artist> {
            override fun onResponse(call: Call<Artist>, response: Response<Artist>) {
                if(!response.isSuccessful)
                    Toast.makeText(application, response.code(), Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(application, "Successful APi call", Toast.LENGTH_SHORT).show()
                    SongList.value = response.body()?.result
                }
            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                Toast.makeText(application, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

}