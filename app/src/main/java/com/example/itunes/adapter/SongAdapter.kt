package com.example.itunes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes.R
import com.example.itunes.model.Result
import com.squareup.picasso.Picasso

class SongAdapter(private val context: Context) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var SongList : List<Result> = ArrayList()

    fun updateSongList(SongList : List<Result>) {
        this.SongList = SongList
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val songImg : ImageView = itemView.findViewById(R.id.song_img)
        val songName : TextView = itemView.findViewById(R.id.song_name)
        val artistName : TextView = itemView.findViewById(R.id.artist_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return SongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = SongList?.get(position)
        if (curSong != null) {
            Picasso.get()
                .load(curSong.artworkUrl100)
                .placeholder(R.drawable.ic_default)
                .into(holder.songImg)
            holder.songName.text = curSong.trackName
            holder.artistName.text = curSong.artistName
        }
    }

    override fun getItemCount(): Int {
        if (SongList != null) {
            return SongList.size
        }
        return 0
    }
}