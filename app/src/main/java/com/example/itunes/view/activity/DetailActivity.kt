package com.example.itunes.view.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.itunes.R

class DetailActivity : AppCompatActivity() {

    private lateinit var playerButton: ImageView
    private lateinit var curTime: TextView
    private lateinit var totalTime: TextView
    private lateinit var playerSeekBar: SeekBar
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private val handler: Handler = Handler()
    private var MEDIA_URL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        MEDIA_URL = intent.getStringExtra("previewURL")

        playerButton = findViewById(R.id.detail_play_song)
        curTime = findViewById(R.id.detail_song_current_time)
        totalTime = findViewById(R.id.detail_song_total_time)
        playerSeekBar = findViewById(R.id.detail_song_seek_bar)

        playerSeekBar.max = 100

        playerButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (mediaPlayer.isPlaying) {
                    handler.removeCallbacks(update)
                    mediaPlayer.pause()
                    playerButton.setImageResource(R.drawable.ic_play)
                } else {
                    mediaPlayer.start()
                    playerButton.setImageResource(R.drawable.ic_pause)
                    updateSeekBar()
                }
            }
        })
        if(MEDIA_URL != null)
        prepareMediaPlayer()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer.stop()
        handler.removeCallbacks(update)
        onBackPressed()
    }

    private fun prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource(MEDIA_URL)
            mediaPlayer.prepare()
            totalTime.text = changeTimeFormat(mediaPlayer.duration)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private var update: Runnable = Runnable {
        updateSeekBar()
        var curDuration: Int = mediaPlayer.currentPosition
        curTime.text = changeTimeFormat(curDuration)
    }

    private fun updateSeekBar() {
        if (mediaPlayer.isPlaying) {
            var curPosition: Int = mediaPlayer.currentPosition
            var progress: Int = ((curPosition / mediaPlayer.duration) * 100)
            playerSeekBar.progress = progress
            handler.postDelayed(update, 1000)
        }
    }

    private fun changeTimeFormat(milliSecond: Int): String {
        var timerString: String = ""
        var hour: Int = (milliSecond / (1000 * 60 * 60))
        var minute: Int = ((milliSecond / (1000 * 60 * 60)) / (1000 * 60))
        var second: Int = (((milliSecond / (1000 * 60 * 60)) / (1000 * 60)) / (1000))

        if (hour > 0) timerString = hour.toString() + ":"
        if (second < 10) timerString = timerString + minute.toString() + ":0" + second.toString()
        else timerString = timerString + minute.toString() + ":" + second.toString()

        return timerString
    }
}