package com.ervalsa.mov.ui.home.ticket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Checkout

class TicketDetailAdapter(private var data: List<Checkout>,
                          private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<TicketDetailAdapter.ViewHolder>(){

    lateinit var contextAdapter: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.item_row_checkout_2, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSeat: TextView = itemView.findViewById(R.id.tv_seat)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context : Context, position : Int) {

            tvSeat.text = "Seat No. "+ data.seat

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}