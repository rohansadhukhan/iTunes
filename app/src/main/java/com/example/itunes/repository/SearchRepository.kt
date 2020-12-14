package com.example.itunes.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.itunes.api.ApiService
import com.example.itunes.api.RetrofitService
import com.example.itunes.room.dao.SongDao
import com.example.itunes.room.databse.SongDatabase
import com.example.itunes.view.model.Artist
import com.example.itunes.view.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository(val application: Application) {

    val SongList: MutableLiveData<List<Result>>? = MutableLiveData<List<Result>>()
    private val database: SongDatabase = SongDatabase.getInstance(application)

    fun insert(songList: List<Result>?) {
        InsertAsyncTask(database).execute(songList)
    }

    fun getAllSong(): LiveData<List<Result>>? {
        return database.songDao().getAllSong()
    }

    fun delete(song: Result) {
        DeleteAsyncTask(database).execute(song)
    }

    fun setDatabase() {
        DeleteAllAsyncTask(database).execute()
    }

    class InsertAsyncTask(songDatabase: SongDatabase) : AsyncTask<List<Result>?, Void?, Void>() {
        private val songDao: SongDao = songDatabase.songDao()
        override fun doInBackground(vararg params: List<Result>?): Void? {
            songDao.insert(params[0])
            return null
        }
    }

    class DeleteAsyncTask(songDatabase: SongDatabase) : AsyncTask<Result, Void, Void>() {
        private val songDao : SongDao = songDatabase.songDao()
        override fun doInBackground(vararg params: Result?): Void? {
            songDao.delete(params[0])
            return null
        }
    }

    class DeleteAllAsyncTask(songDatabase: SongDatabase) : AsyncTask<Void, Void, Void>() {
        private val songDao: SongDao = songDatabase.songDao()
        override fun doInBackground(vararg params: Void?): Void? {
            songDao.deleteAll()
            return null
        }
    }

    fun searchArtist(artistName: String) {

        val service = RetrofitService.buildService(ApiService::class.java)

        val call = service.getArtists(artistName).enqueue(object : Callback<Artist> {
            override fun onResponse(call: Call<Artist>, response: Response<Artist>) {
                if (!response.isSuccessful)
                    Toast.makeText(application, response.code(), Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(application, "Successful APi call", Toast.LENGTH_SHORT).show()
                    SongList?.value = response.body()?.result
                    Log.d("tag", "pre setDatabase")
                    setDatabase()
                    insert(SongList?.value)
                    Log.d("tag", "post setDatabase")
                }
            }

            override fun onFailure(call: Call<Artist>, t: Throwable) {
                Toast.makeText(application, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}