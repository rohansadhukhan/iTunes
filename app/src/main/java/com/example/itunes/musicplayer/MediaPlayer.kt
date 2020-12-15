package com.example.itunes.musicplayer

import android.media.MediaPlayer

object MediaPlayer {

    var mediaPlayer: MediaPlayer? = null
        get() {
            if (field == null) mediaPlayer = MediaPlayer()
            return field
        }

}