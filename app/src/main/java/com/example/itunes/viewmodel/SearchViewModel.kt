package com.example.itunes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.itunes.model.Result
import com.example.itunes.repository.SearchRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SearchRepository(application)
    val SongList : LiveData<List<Result>>


    init {
        this.SongList = repository.SongList
    }

    fun searchArtist(artistName : String) {
        repository.searchArtist(artistName)
    }

}