package com.example.itunes.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.itunes.R
import com.example.itunes.api.ApiService
import com.example.itunes.model.Artist
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private var demoText : TextView? = null
    val BASE_URL : String = "https://itunes.apple.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        demoText = findViewById(R.id.demo)
        getArtists()
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
                    demoText!!.text = response.code().toString();
                    return;
                }

                val artist = response.body()
                val result = artist?.result
                if (result != null) {
                    for(cur in result) {
                        val trackName = " " + cur.trackName + "\n"
                        demoText?.append(trackName)
                    }
                }
            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                demoText!!.text = t.message
            }

        })

    }
}