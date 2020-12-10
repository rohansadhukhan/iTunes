package com.example.itunes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes.R
import com.example.itunes.model.Result
import com.squareup.picasso.Picasso

class SongAdapter(private val SongList : List<Result>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val songImg : ImageView = itemView.findViewById(R.id.song_img)
        val songName : TextView = itemView.findViewById(R.id.song_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return SongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = SongList[position]
        Picasso.get()
            .load(curSong.artworkUrl100)
            .placeholder(R.drawable.ic_default)
            .into(holder.songImg)
        holder.songName.text = curSong.trackName
    }

    override fun getItemCount(): Int {
        return SongList.size
    }
}