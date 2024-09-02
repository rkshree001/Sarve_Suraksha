package com.bnet.sarvesuraksha.view.Profile_Page_Section

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import com.bnet.savresuraksha.R
import kotlin.reflect.KMutableProperty0

class AboutSarveSuraksha : AppCompatActivity() {


    private lateinit var ProductsInfoLL: LinearLayout
    private lateinit var ProductsParentLL: LinearLayout
    private lateinit var healthParentLL: LinearLayout
    private lateinit var healthInfoLL: LinearLayout
    private lateinit var CarInfoLL: LinearLayout
    private lateinit var carparentLL: LinearLayout
    private lateinit var bikeParentLL: LinearLayout
    private lateinit var BikeInfoLL: LinearLayout
    private lateinit var TermLifeParentLL: LinearLayout
    private lateinit var TermLifeInfoLL: LinearLayout
    private lateinit var TravelParentLL: LinearLayout
    private lateinit var TravelInfoLL: LinearLayout


    private lateinit var ProductsArrowImageLL: ImageView
    private lateinit var HealthArrowImageLL: ImageView
    private lateinit var CarArrowImageLL: ImageView
    private lateinit var BikeArrowImgdown: ImageView
    private lateinit var TermLifeArrowImgdown: ImageView
    private lateinit var TravelArrowImgdown: ImageView
    private lateinit var goBack: LinearLayout


    private var isProductVisibile = false
    private var ishealthVisibile = false
    private var iscarVisible = false
    private var isBikeVisibile = false
    private var isTermLifeVisible = false
    private var isTravelVisibile = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_sarve_suraksha)


        ProductsInfoLL = findViewById(R.id.ProductsInfoLL)
        ProductsParentLL = findViewById(R.id.ProductsParentLL)
        ProductsArrowImageLL = findViewById(R.id.ProductsArrowImageLL)
        healthParentLL = findViewById(R.id.healthParentLL)
        healthInfoLL = findViewById(R.id.healthInfoLL)
        HealthArrowImageLL = findViewById(R.id.HealthArrowImageLL)
        CarArrowImageLL = findViewById(R.id.CarArrowImageLL)
        carparentLL = findViewById(R.id.carparentLL)
        CarInfoLL = findViewById(R.id.CarInfoLL)
        bikeParentLL = findViewById(R.id.bikeParentLL)
        BikeArrowImgdown = findViewById(R.id.BikeArrowImgdown)
        BikeInfoLL = findViewById(R.id.BikeInfoLL)
        TermLifeParentLL = findViewById(R.id.TermLifeParentLL)
        TermLifeArrowImgdown = findViewById(R.id.TermLifeArrowImgdown)
        TermLifeInfoLL = findViewById(R.id.TermLifeInfoLL)
        TravelParentLL = findViewById(R.id.TravelParentLL)
        TravelArrowImgdown = findViewById(R.id.TravelArrowImgdown)
        TravelInfoLL = findViewById(R.id.TravelInfoLL)
        goBack = findViewById(R.id.goBack)


        goBack.setOnClickListener {
            val intent = Intent(this@AboutSarveSuraksha, ProfilePage::class.java)
            startActivity(intent)
        }


        ProductsParentLL.setOnClickListener {
            toggleVisibility(ProductsInfoLL, ProductsArrowImageLL, ::isProductVisibile, ProductsParentLL)
        }

        healthParentLL.setOnClickListener {
            toggleVisibility(healthInfoLL, HealthArrowImageLL, ::ishealthVisibile, healthParentLL)
        }


        carparentLL.setOnClickListener {
            toggleVisibility(CarInfoLL, CarArrowImageLL, ::iscarVisible, carparentLL)
        }


        bikeParentLL.setOnClickListener {
            toggleVisibility(BikeInfoLL, BikeArrowImgdown, ::isBikeVisibile, bikeParentLL)
        }

        TermLifeParentLL.setOnClickListener {
            toggleVisibility(TermLifeInfoLL, TermLifeArrowImgdown, ::isTermLifeVisible, TermLifeParentLL)
        }


        TravelParentLL.setOnClickListener {
            toggleVisibility(TravelInfoLL, TravelArrowImgdown, ::isTravelVisibile, TravelParentLL)
        }

    }

    private fun toggleVisibility(contentLL: LinearLayout, arrow: ImageView, visibilityFlag: KMutableProperty0<Boolean>, parentLL: LinearLayout) {
        if (visibilityFlag.get()) {
            contentLL.visibility = View.GONE
            rotateArrow(arrow, 0f)
            updateParentMargin(parentLL, 20)
        } else {
            contentLL.visibility = View.VISIBLE
            rotateArrow(arrow, 180f)
            updateParentMargin(parentLL, 0)
        }
        visibilityFlag.set(!visibilityFlag.get())
    }

    private fun rotateArrow(arrow: ImageView, toDegree: Float) {
        val rotateAnimation = RotateAnimation(
            if (toDegree == 0f) 180f else 0f,
            toDegree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        arrow.startAnimation(rotateAnimation)
    }

    private fun updateParentMargin(parentLL: LinearLayout, marginBottom: Int) {
        val params = parentLL.layoutParams as LinearLayout.LayoutParams
        params.bottomMargin = marginBottom.dpToPx()
        parentLL.layoutParams = params
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AboutSarveSuraksha, ProfilePage::class.java)
        startActivity(intent)

    }

}