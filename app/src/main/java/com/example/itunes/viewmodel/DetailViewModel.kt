package com.example.itunes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.itunes.view.model.Result
import com.example.itunes.repository.DetailRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DetailRepository(application)
    private lateinit var currentSong: Result
    var currentTime : Int = 0
    var totalTime : Int = 0

    public fun setCurrentSong(currentSong: Result) {
        this.currentSong = currentSong
    }

    public fun getCurrentSong() : Result = currentSong
}