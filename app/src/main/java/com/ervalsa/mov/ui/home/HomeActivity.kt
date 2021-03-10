package com.ervalsa.mov.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ervalsa.mov.R
import com.ervalsa.mov.ui.home.dashboard.DashboardFragment
import com.ervalsa.mov.ui.home.profile.ProfileFragment
import com.ervalsa.mov.ui.home.ticket.TicketFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentDashboard = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentProfile = ProfileFragment()

        setFragment(fragmentDashboard)

        btn_dashboard.setOnClickListener {
            setFragment(fragmentDashboard)

            changeIcon(btn_dashboard, R.drawable.ic_home_active)
            changeIcon(btn_ticket, R.drawable.ic_tiket)
            changeIcon(btn_profile, R.drawable.ic_profile)
        }

        btn_ticket.setOnClickListener {
            setFragment(fragmentTicket)

            changeIcon(btn_dashboard, R.drawable.ic_home)
            changeIcon(btn_ticket, R.drawable.ic_tiket_active)
            changeIcon(btn_profile, R.drawable.ic_profile)
        }

        btn_profile.setOnClickListener {
            setFragment(fragmentProfile)

            changeIcon(btn_dashboard, R.drawable.ic_home)
            changeIcon(btn_ticket, R.drawable.ic_tiket)
            changeIcon(btn_profile, R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }
}