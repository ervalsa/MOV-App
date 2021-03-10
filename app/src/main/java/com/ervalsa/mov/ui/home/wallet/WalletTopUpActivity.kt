package com.ervalsa.mov.ui.home.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.ervalsa.mov.R
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet.btn_top_up
import kotlinx.android.synthetic.main.activity_wallet_top_up.*
import java.lang.NumberFormatException
import kotlinx.android.synthetic.main.activity_wallet_top_up.btn_back as btn_back1

class WalletTopUpActivity : AppCompatActivity() {

    private var status10K: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_top_up)

        initListener()

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun initListener() {

        btn_top_up_wallet.setOnClickListener {
            val showSuccessWallet = Intent(this, WalletSuccessActivity::class.java)
            startActivity(showSuccessWallet)
        }

        tv_10k.setOnClickListener {
            if (status10K) {
                deselectMoney(tv_10k)
            } else {
                selectMoney(tv_10k)
            }
        }

        edt_amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                try {
                    if (s.toString().toInt() >= 10000) {
                        btn_top_up_wallet.visibility = View.VISIBLE
                    } else {
                        tv_10k.setTextColor(resources.getColor(R.color.colorPrimary))
                        tv_10k.setBackgroundResource(R.drawable.bg_shape_line_inactive)
                        status10K = false
                        btn_top_up_wallet.visibility = View.INVISIBLE
                    }
                } catch (e: NumberFormatException) {
                    tv_10k.setTextColor(resources.getColor(R.color.colorPrimary))
                    tv_10k.setBackgroundResource(R.drawable.bg_shape_line_inactive)
                    status10K = false
                    btn_top_up_wallet.visibility = View.INVISIBLE
                }
            }

        })
    }

    private fun selectMoney(textView: TextView){
        textView.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        textView.setBackgroundResource(R.drawable.bg_shape_line_active)
        status10K = true

        btn_top_up_wallet.visibility = View.VISIBLE
        edt_amount.setText("10000")
    }

    private fun deselectMoney(textView: TextView){
        textView.setTextColor(resources.getColor(R.color.colorPrimary))
        textView.setBackgroundResource(R.drawable.bg_shape_line_inactive)
        status10K = false

        btn_top_up_wallet.visibility = View.INVISIBLE
        edt_amount.setText("")
    }
}