package com.example.itunes.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicService : Service() {

    private lateinit var musicPlayer: MediaPlayer
    private var MEDIA_URL: String? = null
    private var TEMP_URL: String? = null

    companion object {
        @Volatile
        private var INSTANCE: MediaPlayer? = null
        fun getInstance(): MediaPlayer {
            var instance = INSTANCE

            if (instance == null) {
                instance = MediaPlayer()
                INSTANCE = instance
            }
            return instance
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("tag", "service on start command done")
        musicPlayer = getInstance()

        if (intent != null) {
            TEMP_URL = intent.getStringExtra("URL")
            if (TEMP_URL.equals(MEDIA_URL)) {
                if (musicPlayer.isPlaying) musicPlayer.pause()
                else if(musicPlayer.duration == 0) {
                    musicPlayer.reset()
                    MEDIA_URL = TEMP_URL
                    musicPlayer.setDataSource(MEDIA_URL)
                    musicPlayer.prepare()
                    musicPlayer.start()
                } else {
                    musicPlayer.start()
                }
            } else {
                if (musicPlayer.isPlaying) {
                    musicPlayer.stop()
                }
                musicPlayer.reset()
                MEDIA_URL = TEMP_URL
                musicPlayer.setDataSource(MEDIA_URL)
                musicPlayer.prepare()
                musicPlayer.start()
            }
        }


        Log.d("tag", "start music done")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        MEDIA_URL = null
        musicPlayer.stop()
        musicPlayer.reset()
    }


}