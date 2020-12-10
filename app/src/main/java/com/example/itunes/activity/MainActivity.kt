package com.example.itunes.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.example.itunes.R
import com.example.itunes.adapter.SongAdapter
import com.example.itunes.api.ApiService
import com.example.itunes.model.Artist
import com.example.itunes.model.Result
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val BASE_URL : String = "https://itunes.apple.com/"
    private lateinit var searchText : EditText
    private lateinit var recyclerView : RecyclerView
    private var SongList : List<Result> = ArrayList<Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getArtists()

        searchText = findViewById(R.id.search)
        recyclerView = findViewById(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    private fun getArtists() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.getArtists("arijit")
        call.enqueue(object : Callback<Artist> {
            override fun onResponse(call: Call<Artist>, response: Response<Artist>) {
                if(!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, response.code(), Toast.LENGTH_SHORT).show()
                    return;
                }
                Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_SHORT).show()
//                SongList = ArrayList<Result>()
                for(result in response.body()?.result!!) {
//                    Toast.makeText(this@MainActivity, result.trackName, Toast.LENGTH_SHORT).show()
                    SongList += result
                }
                recyclerView.adapter = SongAdapter(SongList)

            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}