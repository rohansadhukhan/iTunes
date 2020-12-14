package com.example.itunes.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
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

//        viewModel.SongList?.observe(this, Observer {
//            this.SongList = it
//        })

        recyclerView = findViewById(R.id.recyclerview)
        adapter = SongAdapter(this, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClicked(position: Int) {
//        Toast.makeText(this, SongList.size.toString(), Toast.LENGTH_SHORT).show()
        var song : Result = SongList[position]
//        Toast.makeText(this@MainActivity, "clicked " + position + " " + song.artistName, Toast.LENGTH_SHORT).show()
        viewModel.deleteSong(song)
    }
}