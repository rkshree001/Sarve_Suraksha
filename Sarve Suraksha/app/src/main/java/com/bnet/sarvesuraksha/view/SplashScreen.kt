package com.bnet.sarvesuraksha.view
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Landing_Page.LandingPage
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.view.adapter.ImageAdapter

import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.util.Timer
import java.util.TimerTask

class SplashScreen : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ImageAdapter

    private lateinit var nextImageButton: ImageView
    private lateinit var skipToLandingPage: TextView
    private var currentPage = 0
    private lateinit var progressBar: CircularProgressBar

    private val DELAY_MILLIS = 1500L

    private var timer: Timer? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        viewPager = findViewById(R.id.viewPager)
        skipToLandingPage = findViewById(R.id.skipToLandingPage)
        nextImageButton = findViewById(R.id.nextImageButton)
        adapter = ImageAdapter(this)
        viewPager.adapter = adapter

        progressBar = findViewById(R.id.progressBar)
        progressBar.progress = 0f

        startAutoPageSwitcher()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                updateProgressBar(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })


        skipToLandingPage.setOnClickListener {
            navigateToLandingPage()
        }

        nextImageButton.setOnClickListener { goToNextPage() }
    }

    private fun startAutoPageSwitcher() {
        val update = Runnable { goToNextPage() }

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MILLIS, DELAY_MILLIS)
    }

    private fun goToNextPage() {
        if (currentPage < adapter.count - 1) {
            currentPage++
            viewPager.setCurrentItem(currentPage, true)
            updateProgressBar(currentPage)
        } else {
            navigateToLandingPage()
        }
    }
//    private fun goToNextPage() {
//        currentPage = if (currentPage == adapter.count - 1) {
//            0
//        } else {
//            currentPage + 1
//        }
//        viewPager.setCurrentItem(currentPage, true)
//        updateProgressBar(currentPage)
//    }
    private fun navigateToLandingPage() {
        val intent = Intent(this@SplashScreen, LandingPage::class.java)
        startActivity(intent)
        finish()
    }
    private fun updateProgressBar(currentPage: Int) {
        val progress = ((currentPage + 1) * 100 / adapter.count).toFloat()
        progressBar.progress = progress
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
