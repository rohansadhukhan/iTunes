package com.example.itunes.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.itunes.view.model.Result
import com.example.itunes.repository.SearchRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SearchRepository(application)
    val SongList : LiveData<List<Result>>?

    init {
        this.SongList = repository.getAllSong()
    }

    fun searchArtist(artistName : String) {
        repository.searchArtist(artistName)
    }

    fun insert(songList : List<Result>?) {
        repository.insert(songList)
    }

    fun getAllSavedSong() : LiveData<List<Result>>? {
        return repository.getAllSong()
    }

    fun deleteSong(song : Result) {
        Log.d("tag", song.artistName)
        repository.delete(song)
    }

    fun deleteAllSong() {
        repository.setDatabase()
    }

    fun getAllSavedSongASC() : LiveData<List<Result>>? {
        return repository.getAllSongASC()
    }

    fun getAllSavedSongDESC() : LiveData<List<Result>>? {
        return repository.getAllSongDESC()
    }

}