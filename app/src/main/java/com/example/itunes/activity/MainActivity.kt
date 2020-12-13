package com.example.itunes.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes.R
import com.example.itunes.adapter.SongAdapter
import com.example.itunes.model.Result
import com.example.itunes.viewmodel.SearchViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var SongList : List<Result> = ArrayList<Result>()

    // View Models
    private lateinit var viewModel : SearchViewModel

    // Recycler View
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : SongAdapter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.search_icon)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
        return when(item.itemId) {
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
        viewModel.SongList.observe(this, androidx.lifecycle.Observer {
            Log.d("My", it.size.toString())
            adapter.updateSongList(it)
        })

        recyclerView = findViewById(R.id.recyclerview)
        adapter = SongAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }
}