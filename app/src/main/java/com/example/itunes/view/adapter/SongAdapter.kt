package com.example.itunes.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes.R
import com.example.itunes.view.model.Result
import com.squareup.picasso.Picasso

class SongAdapter(private val context: Context, val listener: onItemClickListener) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var SongList: List<Result>? = ArrayList()

    fun updateSongList(SongList: List<Result>) {
        this.SongList = SongList
        notifyDataSetChanged()
    }

    interface onItemClickListener {
        fun onItemClicked(position: Int)
    }

    class SongViewHolder(itemView: View, val listener: onItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val songImg: ImageView = itemView.findViewById(R.id.song_img)
        val songName: TextView = itemView.findViewById(R.id.song_name)
        val artistName: TextView = itemView.findViewById(R.id.artist_name)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClicked(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return SongViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = SongList?.get(position)
        Picasso.get()
            .load(curSong?.artworkUrl100)
            .placeholder(R.drawable.ic_default)
            .into(holder.songImg)
        holder.songImg.setBackgroundResource(R.drawable.background_rounded)
        holder.songName.text = curSong?.trackName
        holder.artistName.text = curSong?.artistName
    }

    override fun getItemCount(): Int {
        if (SongList != null)
            return SongList!!.size
        return 0
    }
}