package com.example.itunes.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes.R
import com.example.itunes.view.adapter.SongAdapter
import com.example.itunes.view.model.Result
import com.example.itunes.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity(), SongAdapter.onItemClickListener {

    private var SongList: ArrayList<Result> = ArrayList<Result>()

    private var MEDIA_URL : String? = null

    // View Models
    private lateinit var viewModel: SearchViewModel

    // Recycler View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongAdapter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.search_icon)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search artist ..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchArtist(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_icon -> {
                true
            }
            R.id.favourites -> {
                true
            }
            R.id.order_by_name_asc -> {
                viewModel.getAllSavedSongASC()?.observe(this, Observer {
                    SongList = it as ArrayList<Result>
                    adapter.updateSongList(it)
                })
                true
            }
            R.id.order_by_name_desc -> {
                viewModel.getAllSavedSongDESC()?.observe(this, Observer {
                    SongList = it as ArrayList<Result>
                    adapter.updateSongList(it)
                })
                true
            }
            R.id.undo -> {
                viewModel.SongList?.observe(this, Observer {
                    SongList = it as ArrayList<Result>
                    adapter.updateSongList(it)
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.SongList?.observe(this, androidx.lifecycle.Observer {
            this.SongList = it as ArrayList<Result>
            adapter.updateSongList(it)
        })

        recyclerView = findViewById(R.id.recyclerview)
        adapter = SongAdapter(this, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClicked(position: Int) {
//        Toast.makeText(this, SongList.size.toString(), Toast.LENGTH_SHORT).show()
        var song : Result = SongList[position]
        Toast.makeText(this@MainActivity, "clicked " + position + " " + song.artistName, Toast.LENGTH_SHORT).show()
//        viewModel.deleteSong(song)
//        handleMediaPlayer(SongList[position]?.trackViewUrl!!)
    }

    fun handleMediaPlayer(url : String) {
        val player : android.media.MediaPlayer = android.media.MediaPlayer()
        MEDIA_URL = url
        try {
            player.setDataSource("https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview114/v4/42/51/bd/4251bd94-a533-fe0e-8bd3-c20178e4c7e9/mzaf_16464829143727885423.plus.aac.p.m4a")
            player.prepare()
        } catch (e : Exception) {
            Log.d("tag", e.message.toString())
        }

        if(player.isPlaying == true || url == MEDIA_URL) {
            player.pause()
        } else {
            player?.start()
        }
    }
}