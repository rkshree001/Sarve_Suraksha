package com.bnet.sarvesuraksha.view.Landing_Page_Section.Landing_Page

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.model.Department
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import com.bnet.sarvesuraksha.view.adapter.CategoryAdapter
import com.bnet.sarvesuraksha.view.adapter.DepartmentAdapter
import com.bnet.sarvesuraksha.view.adapter.OurPartnersAdapter
import com.bnet.sarvesuraksha.view.adapter.ViewPagerAdapter
import com.github.kwasow.bottomnavigationcircles.BottomNavigationCircles


class LandingPage : AppCompatActivity(), CategoryAdapter.OnCategoryClickListener {

    private lateinit var whatsNewUpLL: LinearLayout
    private lateinit var whatsNewUp: ImageView
    private lateinit var fullView: ImageView
    private lateinit var whatsNewDown: ImageView
    private lateinit var reccyclerView: RecyclerView
    private lateinit var ourPartnerImg: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView

    private var isWhatsNewVisible = true

    private lateinit var bottomNavigationView: BottomNavigationCircles
    private var selectedItemId: Int = -1

    private lateinit var frame_arrow_img: ImageView
    private lateinit var viewPager: ViewPager

    private lateinit var adapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_landing_page)

        whatsNewUpLL = findViewById(R.id.whatsNewUpLL)
        fullView = findViewById(R.id.fullView)



        fullView.setOnClickListener {
            val intent = Intent(this@LandingPage, ExploreFullView::class.java)
            startActivity(intent)
        }

        reccyclerView = findViewById(R.id.reccyclerView)
        ourPartnerImg = findViewById(R.id.ourPartnerImg)
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        whatsNewUp = findViewById(R.id.whatsNewUp)
        whatsNewDown = findViewById(R.id.whatsNewDown)
        viewPager = findViewById(R.id.viewPager)
        frame_arrow_img = findViewById(R.id.frame_arrow_img)

        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter



        whatsNewUp.setOnClickListener {
            toggleWhatsNewVisibility()
        }



        whatsNewDown.setOnClickListener {
            toggleWhatsNewVisibility()
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.itemRippleColor = null

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {

                    true
                }
                R.id.menu_Claims -> {

                    true
                }
                R.id.menu_Policies -> {

                     true
                }
                R.id.menu_Profile -> {
                    val intent = Intent(this@LandingPage, ProfilePage::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.menu_More -> {

                    true
                }
                else -> false
            }
        }


//        bottomNavigationView.circleColor = Color.RED
//        bottomNavigationView.darkIcon = true
//
//        bottomNavigationView.backgroundShape = BottomNavigationCircles.Shape.Circle

//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            updateMenuItems(item.itemId)
//            true
//        }
//
//
//        val parent_layout = findViewById<LinearLayout>(R.id.parent_layout)
//
//
//
//        Glide.with(this)
//            .asGif()
//            .load(R.drawable.stars)
//            .into(object : CustomTarget<GifDrawable>() {
//                override fun onResourceReady(resource: GifDrawable, transition: Transition<in GifDrawable>?) {
//                    parent_layout.background = resource
//                    resource.start()
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // Handle placeholder if needed
//                }
//            })

        var departments: List<Department> = getStaticDepartments()
        val ourPartners: List<Department> = getourPartner()
        val catList: List<Department> = getCategory()

        val partnersAdapter = OurPartnersAdapter(this, ourPartners)
        val categoryAdapter = CategoryAdapter(this, catList,this)



        categoryRecyclerView.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        categoryRecyclerView.adapter = categoryAdapter



        ourPartnerImg.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        ourPartnerImg.adapter = partnersAdapter



        val departmentAdapter = DepartmentAdapter(this, departments)
        reccyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        reccyclerView.adapter = departmentAdapter

        frame_arrow_img.setOnClickListener {
            moveToNextPage()
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = android.Manifest.permission.POST_NOTIFICATIONS
            when {
                checkSelfPermission(notificationPermission) == android.content.pm.PackageManager.PERMISSION_GRANTED -> {

                }
                else -> {

                    requestNotificationPermissionLauncher.launch(notificationPermission)
                }
            }
        }
    }

    private val requestNotificationPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {

                openNotificationSettings()
            }
        }

    private fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {

            } else {

                openNotificationSettings()
            }
        }
    }
    private fun getourPartner(): List<Department> {
        return listOf(

            Department("", R.drawable.partners1),
            Department("", R.drawable.partners2),
            Department("", R.drawable.partners3),
            Department("", R.drawable.partners4),
            Department("", R.drawable.partners5),
            Department("", R.drawable.partners6),
            Department("", R.drawable.partners7),
            Department("", R.drawable.partners8)

        )
    }
    private fun getCategory(): List<Department> {
        return listOf(

            Department("Health", R.drawable.health_ins),
            Department("Car", R.drawable.car_ins),
            Department("Bike", R.drawable.bike_ins),
            Department("Term Life", R.drawable.term_life),
            Department("Travel", R.drawable.travel),
            Department("Home", R.drawable.home_ins)


        )
    }

    private fun moveToNextPage() {

        val currentItem = viewPager.currentItem
        val totalItems = adapter.count

        if (currentItem < totalItems - 1) {
            viewPager.currentItem = currentItem + 1
        } else {
            viewPager.currentItem = 0
        }
    }


    private fun getStaticDepartments(): List<Department> {
        return listOf(
            Department("Learn with Videos", R.drawable.round1),
            Department("Visa on arrival", R.drawable.round2),
            Department("Cashless Hospital", R.drawable.round3),
            Department("Visa free countries", R.drawable.round4),
            Department("Learn with Videos", R.drawable.round1),
            Department("Visa on arrival", R.drawable.round2),
            Department("Cashless Hospital", R.drawable.round3),
            Department("Visa free countries", R.drawable.round4)


        )
    }
//
//    private fun updateMenuItems(itemId: Int) {
//        if (selectedItemId != -1) {
//
//            resetMenuItem(selectedItemId)
//        }
//
//        moveMenuItemUp(itemId)
//
//        selectedItemId = itemId
//    }
//
//
//    private fun resetMenuItem(itemId: Int) {
//        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
//        itemView?.translationY = 0f
//    }
//
//    private fun moveMenuItemUp(itemId: Int) {
//        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
//        itemView?.translationY = -convertCmToPx(0.13f)
//    }
//
//    private fun convertCmToPx(cm: Float): Float {
//        val displayMetrics = resources.displayMetrics
//        return cm * displayMetrics.densityDpi / 2.54f
//    }


    private fun toggleWhatsNewVisibility() {
        isWhatsNewVisible = !isWhatsNewVisible

        if (isWhatsNewVisible) {
            whatsNewUpLL.animate()
                .alpha(1f)
                .setDuration(800)
                .setInterpolator(DecelerateInterpolator())
                .withStartAction {
                    whatsNewUpLL.visibility = View.VISIBLE
                    whatsNewDown.visibility = View.GONE
                }
        } else {
            whatsNewUpLL.animate()
                .alpha(0f)
                .setDuration(800)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {
                    whatsNewUpLL.visibility = View.GONE
                    whatsNewDown.visibility = View.VISIBLE
                }
        }
    }

    override fun onCategoryClick(categoryName: String) {
//        when (categoryName) {
//            "Car" -> {
//                val intent = Intent(this, CarInsuranceActivity::class.java)
//                startActivity(intent)
//            }
//            "Health" -> {
//                val intent = Intent(this, HealthInsuranceActivity::class.java)
//                startActivity(intent)
//            }
//            "Bike" -> {
//                val intent = Intent(this, BikeInsuranceActivity::class.java)
//                startActivity(intent)
//            }
//            "Term Life" -> {
//                val intent = Intent(this, TermLifeInsuranceActivity::class.java)
//                startActivity(intent)
//            }
//            "Travel" -> {
//                val intent = Intent(this, TravelInsuranceActivity::class.java)
//                startActivity(intent)
//            }
//            "Home" -> {
//                val intent = Intent(this, HomeInsuranceActivity::class.java)
//                startActivity(intent)
//            }
//        }
    }

}
