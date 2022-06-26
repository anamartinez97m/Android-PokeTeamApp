package com.mimo.poketeamapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mimo.poketeamapp.databinding.ActivityTutorialBinding
import com.squareup.picasso.Picasso

private const val NUM_PAGES = 3

class TutorialActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var binding: ActivityTutorialBinding
    private lateinit var language: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        language = intent.getStringExtra("language").toString()

        viewPager = binding.pager
        val tabLayoutDots = binding.tabDots
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayoutDots, viewPager) { tab, position -> }.attach()
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment {

            val image: Int = when (position) {
                0 -> {
                    if(language == "en") {
                        R.drawable.tutorial_add_favorite_en
                    } else {
                        R.drawable.tutorial_add_favorite_es
                    }
                }
                1 ->  {
                    if(language == "en") {
                        R.drawable.tutorial_remove_favorite_en
                    } else {
                        R.drawable.tutorial_remove_favorite_es
                    }
                }
                2 ->  {
                    if(language == "en") {
                        R.drawable.tutorial_settings_en
                    } else {
                        R.drawable.tutorial_settings_es
                    }
                }
                else -> -1
            }
            return ScreenSlidePageFragment(image)
        }
    }
}