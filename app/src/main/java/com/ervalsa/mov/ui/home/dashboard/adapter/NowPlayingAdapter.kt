package com.ervalsa.bwamov.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Film

class NowPlayingAdapter(private var data: List<Film>,
                        private val listener:(Film) -> Unit) :
        RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val tvRating: TextView = view.findViewById(R.id.tv_rating)
        private val imgPhoto: ImageView = view.findViewById(R.id.img_item_photo)

        fun bindItem(data: Film, listener: (Film) -> Unit, context: Context) {
            tvTitle.text = data.judul
            tvRating.text = data.rating

            Glide.with(context)
                    .load(data.poster)
                    .placeholder(R.color.colorPrimaryLight)
                    .into(imgPhoto)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.item_row_now_playing, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size


}
