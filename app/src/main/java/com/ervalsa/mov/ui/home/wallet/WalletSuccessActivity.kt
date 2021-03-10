package com.ervalsa.mov.ui.home.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ervalsa.mov.R
import com.ervalsa.mov.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_wallet_success.*

class WalletSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_success)

        btn_home.setOnClickListener {
            finishAffinity()

            val showHome = Intent(this@WalletSuccessActivity,
                HomeActivity::class.java)
            startActivity(showHome)
        }

        btn_wallet.setOnClickListener {
            finishAffinity()

            val showHome = Intent(this@WalletSuccessActivity,
                HomeActivity::class.java)
            startActivity(showHome)
        }
    }
}