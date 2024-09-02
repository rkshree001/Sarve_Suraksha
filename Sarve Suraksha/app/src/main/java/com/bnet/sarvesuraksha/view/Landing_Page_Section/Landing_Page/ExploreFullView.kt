package com.bnet.sarvesuraksha.view.Landing_Page_Section.Landing_Page

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.view.adapter.ExplorePagerAdapter

class ExploreFullView : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var nextImageButton: ImageView
    private lateinit var pagerAdapter: ExplorePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore_full_view)

        viewPager = findViewById(R.id.viewPager)
        nextImageButton = findViewById(R.id.nextImageButton)
        pagerAdapter = ExplorePagerAdapter(this)
        viewPager.adapter = pagerAdapter


        nextImageButton.setOnClickListener {
            val nextPage = viewPager.currentItem + 1
            if (nextPage < pagerAdapter.count) {
                viewPager.currentItem = nextPage
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ExploreFullView, LandingPage::class.java))
        finish()
    }
}
