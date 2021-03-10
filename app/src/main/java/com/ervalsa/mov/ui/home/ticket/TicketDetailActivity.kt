package com.ervalsa.mov.ui.home.ticket

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Checkout
import com.ervalsa.mov.model.Film
import com.ervalsa.mov.ui.home.ticket.adapter.TicketAdapter
import com.ervalsa.mov.ui.home.ticket.adapter.TicketDetailAdapter
import kotlinx.android.synthetic.main.activity_ticket_detail.*

class TicketDetailActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_detail)

        val data = intent.getParcelableExtra<Film>("data")

        tv_title.text = data?.judul
        tv_genre.text = data?.genre
        tv_rating.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(img_poster)

        rv_checkout.layoutManager = LinearLayoutManager(this)

        dataList.add(Checkout("C1", ""))
        dataList.add(Checkout("C2", ""))

        rv_checkout.adapter = TicketDetailAdapter(dataList) {

        }

        btn_back.setOnClickListener {
            finish()
        }

        img_barcode.setOnClickListener {
            showDialog("Please do a scanning at the nearest ticket counter")
        }
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val tvDesc = dialog.findViewById(R.id.tv_desc) as TextView
        tvDesc.text = title

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}