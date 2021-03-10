package com.ervalsa.mov.ui.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ervalsa.bwamov.home.dashboard.NowPlayingAdapter
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Film
import com.ervalsa.mov.ui.home.detail.DetailActivity
import com.ervalsa.mov.ui.home.dashboard.adapter.ComingSoonAdapter
import com.ervalsa.mov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference
    
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_name.setText(preferences.getValues("nama"))

        if (!preferences.getValues("saldo").equals("")) {
            currency(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
                .load(preferences.getValues("photoUrl"))
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.img_user_placeholder)
                .into(img_user_photo)

        rv_now_playing.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
        )

        rv_coming_soon.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in dataSnapshot.children) {
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_now_playing.adapter = NowPlayingAdapter(dataList) {
                    val showDetail = Intent(
                            context,
                            DetailActivity::class.java
                    ).putExtra("data", it)
                    startActivity(showDetail)
                }

                rv_coming_soon.adapter = ComingSoonAdapter(dataList) {
                    val showDetail = Intent(
                            context,
                            DetailActivity::class.java
                    ).putExtra("data", it)
                    startActivity(showDetail)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun currency(harga: Double, textView: TextView) {
        val localId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localId)
        textView.setText(formatRupiah.format(harga as Double))
    }


}