package com.ervalsa.mov.ui.home.ticket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Film
import com.ervalsa.mov.ui.home.dashboard.adapter.ComingSoonAdapter
import com.ervalsa.mov.ui.home.ticket.adapter.TicketAdapter
import com.ervalsa.mov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_ticket.*

class TicketFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        rv_ticket.layoutManager = LinearLayoutManager(context!!.applicationContext)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()

                for (getDataSnapshot in dataSnapshot.getChildren()) {
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_ticket.adapter = TicketAdapter(dataList) {
                    val showTicketDetail = Intent(context,
                        TicketDetailActivity::class.java).putExtra("data", it)
                    startActivity(showTicketDetail)
                }

                tv_total_ticket.setText(dataList.size.toString() + " Movies")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+ databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}