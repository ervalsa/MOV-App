package com.ervalsa.mov.ui.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ervalsa.mov.R
import com.ervalsa.mov.ui.home.wallet.MyWalletActivity
import com.ervalsa.mov.ui.sign.signin.SignInActivity
import com.ervalsa.mov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!.applicationContext)

        tv_name.text = preferences.getValues("nama")
        tv_email.text = preferences.getValues("email")

        Glide.with(this)
            .load(preferences.getValues("photoUrl"))
            .apply(RequestOptions.circleCropTransform())
            .into(img_user_photo)

        tv_my_wallet.setOnClickListener {
            val showMyWallet = Intent(activity, MyWalletActivity::class.java)
            startActivity(showMyWallet)
        }

        tv_logout.setOnClickListener {

            activity?.finish()

            preferences.setValues("status", "0")

            val showSignIn = Intent(activity, SignInActivity::class.java)
            startActivity(showSignIn)



        }
    }

}