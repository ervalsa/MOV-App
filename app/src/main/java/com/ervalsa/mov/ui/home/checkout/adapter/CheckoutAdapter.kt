package com.ervalsa.mov.ui.home.checkout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>,
    private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.item_row_checkout, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvSeat: TextView = view.findViewById(R.id.tv_seat)
        private val tvBalance: TextView = view.findViewById(R.id.tv_balance)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context : Context, position : Int) {

            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            tvBalance.setText(formatRupiah.format(data.balance!!.toDouble()))

            if (data.seat!!.startsWith("Total")){
                tvSeat.text = data.seat
                tvSeat.setCompoundDrawables(null,null,null,null)
            } else {
                tvSeat.text = "Seat No. "+data.seat
            }

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}