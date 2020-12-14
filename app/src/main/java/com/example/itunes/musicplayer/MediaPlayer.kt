package com.example.itunes.musicplayer

import android.media.MediaPlayer
import android.widget.Toast

object MediaPlayer {

    val mediaPlayer : MediaPlayer = MediaPlayer()

    private fun prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource(MEDIA_URL)
            mediaPlayer.prepare()
            totalTime.text = changeTimeFormat(mediaPlayer.duration)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

}