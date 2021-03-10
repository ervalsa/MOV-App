package com.ervalsa.mov.ui.home.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Wallet
import com.ervalsa.mov.ui.home.wallet.adapter.WalletAdapter
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWalletActivity : AppCompatActivity() {

    private var dataList = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        loadDummyData()
    }

    private fun initListener() {
        rv_transaction.layoutManager = LinearLayoutManager(this)
        rv_transaction.adapter = WalletAdapter(dataList) {

        }

        btn_top_up.setOnClickListener {
            val showTopUp = Intent(this, WalletTopUpActivity::class.java)
            startActivity(showTopUp)
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun loadDummyData() {
        dataList.add(
            Wallet(
                "Tom and Jerry",
                "Wednesday 10 Mar, 2021",
                70000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Bonus Sign Up",
                "Wednesday 10 Mar, 202`",
                100000.0,
                "1"
            )
        )

        initListener()
    }
}