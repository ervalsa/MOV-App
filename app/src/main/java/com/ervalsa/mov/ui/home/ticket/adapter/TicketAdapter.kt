package com.ervalsa.mov.ui.home.ticket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Checkout
import com.ervalsa.mov.model.Film

class TicketAdapter(private var data: ArrayList<Film>,
                    private val listener: (Film) -> Unit)
    : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.item_row_ticket, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvGenre: TextView = itemView.findViewById(R.id.tv_genre)
        private val tvRate: TextView = itemView.findViewById(R.id.tv_rating)

        private val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)

        fun bindItem(data: Film, listener: (Film) -> Unit, context : Context, position : Int) {

            tvTitle.text = data.judul
            tvGenre.text = data.genre
            tvRate.text = data.rating

            Glide.with(context)
                .load(data.poster)
                .placeholder(R.color.colorPrimaryLight)
                .into(imgPhoto);

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}