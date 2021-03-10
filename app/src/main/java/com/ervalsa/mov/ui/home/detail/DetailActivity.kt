package com.ervalsa.mov.ui.home.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Film
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_title.text = data?.judul
        tv_genre.text = data?.genre
        tv_description_storyboard.text = data?.desc
        tv_rating.text = data?.rating
        tv_name_who_played.text = data?.playName

        Glide.with(this)
            .load(data?.playUrl)
            .into(img_who_played)

        Glide.with(this)
            .load(data?.poster)
            .placeholder(R.color.colorPrimaryLight)
            .into(img_poster)

        Glide.with(this)
            .load(data?.background)
            .placeholder(R.color.colorPrimaryLight)
            .into(img_background_poster)


        btn_pilih_bangku.setOnClickListener {
            val showPilihBangku = Intent(this@DetailActivity,
                    PilihBangkuActivity::class.java).putExtra("data", data)
            startActivity(showPilihBangku)
        }

        getDataPlays()

        btn_back.setOnClickListener {
            finish()
        }

    }

    private fun getDataPlays() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getDataSnapshot in dataSnapshot.children) {
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }
            }

            override fun onCancelled(dataError: DatabaseError) {
                Toast.makeText(this@DetailActivity, "" + dataError.message,
                        Toast.LENGTH_SHORT).show()
            }
        })
    }
}