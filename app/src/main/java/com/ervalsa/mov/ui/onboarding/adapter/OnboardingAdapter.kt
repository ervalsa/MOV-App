package com.ervalsa.mov.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ervalsa.mov.R
import com.ervalsa.mov.model.Onboarding
import kotlinx.android.synthetic.main.item_onboarding_container.view.*

class OnboardingAdapter(private val onBoardingItem: List<Onboarding>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding_container,
            parent, false)
        return OnboardingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItem[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItem.size
    }

    inner class OnboardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageOnboarding = view.findViewById<ImageView>(R.id.img_onboarding)
        private val titleOnboarding = view.findViewById<TextView>(R.id.tv_title_onboarding)
        private val descriptionOnboarding = view.findViewById<TextView>(R.id.tv_description_onboarding)

        fun bind(onboardingItem: Onboarding) {
            imageOnboarding.setImageResource(onboardingItem.onboardingImage)
            titleOnboarding.text = onboardingItem.title
            descriptionOnboarding.text = onboardingItem.description
        }
    }
}