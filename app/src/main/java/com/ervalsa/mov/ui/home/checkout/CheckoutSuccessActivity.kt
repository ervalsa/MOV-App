package com.ervalsa.mov.ui.home.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ervalsa.mov.R
import com.ervalsa.mov.ui.home.HomeActivity
import com.ervalsa.mov.ui.home.ticket.TicketFragment
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_home.setOnClickListener {
            finishAffinity()

            val showHome = Intent(this@CheckoutSuccessActivity,
                HomeActivity::class.java)
            startActivity(showHome)
        }

        btn_tiket.setOnClickListener {
            val showTicket = Intent(this@CheckoutSuccessActivity,
                HomeActivity::class.java)
            startActivity(showTicket)
        }
    }
}