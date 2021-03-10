package com.ervalsa.mov.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Onboarding
import com.ervalsa.mov.ui.home.HomeActivity
import com.ervalsa.mov.ui.onboarding.adapter.OnboardingAdapter
import com.ervalsa.mov.ui.sign.signin.SignInActivity
import com.ervalsa.mov.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var indicatorContainer: LinearLayout
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")) {
            startActivity(Intent(this@OnboardingActivity, SignInActivity::class.java))
            finish()
        }

        setOnboardingItems()
        setIndicator()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems() {
        onboardingAdapter = OnboardingAdapter(
            listOf(
                Onboarding(
                    onboardingImage = R.drawable.illustration_now_playing,
                    title = "Now Playing",
                    description = "Lebih mudah untuk mengetahui\nfilm yang sedang tampil"
                ),
                Onboarding(
                    onboardingImage = R.drawable.illustration_pre_sale,
                    title = "Pre Sale",
                    description = "Tidak khawatir ketinggalan\nupdate film terbaru"
                ),
                Onboarding(
                    onboardingImage = R.drawable.illustration_now_playing,
                    title = "Cashless",
                    description = "Gunakan e-wallet untuk\ntransaksi tiket nonton"
                )
            )
        )

        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingAdapter
        onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                setCurrentIndicator(position)
            }
        })

        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        findViewById<TextView>(R.id.tv_next).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                onboardingViewPager.currentItem += 1
            } else {
                navigateToHome()
            }
        }

        findViewById<TextView>(R.id.tv_skip).setOnClickListener {
            navigateToHome()
            finishAffinity()

        }
    }

    private fun navigateToHome() {
        preferences.setValues("onboarding", ("1"))
        startActivity(Intent(this@OnboardingActivity, SignInActivity::class.java))
        finishAffinity()
    }

    private fun setIndicator() {
        indicatorContainer = findViewById(R.id.indicatorContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )

                it.layoutParams = layoutParams
                indicatorContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}