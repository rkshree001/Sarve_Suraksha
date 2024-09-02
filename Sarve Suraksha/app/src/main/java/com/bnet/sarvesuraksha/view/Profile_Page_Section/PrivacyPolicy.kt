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

class PrivacyPolicy : AppCompatActivity() {

    private lateinit var introLL: LinearLayout
    private lateinit var introParentLL: LinearLayout
    private lateinit var acceptenceOfPrivacyPolicy: LinearLayout
    private lateinit var acceptanceLL: LinearLayout
    private lateinit var personalDataInfoParentLL: LinearLayout
    private lateinit var personalInfoLL: LinearLayout
    private lateinit var NonPersonalDataInfoParentLL: LinearLayout
    private lateinit var nonPersonalInfoLL: LinearLayout
    private lateinit var HowWeUseParentLL: LinearLayout
    private lateinit var HowWeUseLL: LinearLayout
    private lateinit var SharingPersonalParentLL: LinearLayout
    private lateinit var SharingInfoLL: LinearLayout
    private lateinit var RetentionpolicyLL: LinearLayout
    private lateinit var RetentionpolicyParentLL: LinearLayout
    private lateinit var HowWeProtectParentLL: LinearLayout
    private lateinit var HowWeProtectLL: LinearLayout
    private lateinit var ThirdPartyParentLL: LinearLayout
    private lateinit var ThirdPartyLL: LinearLayout
    private lateinit var ChangesInParentLL: LinearLayout
    private lateinit var ChangeInLL: LinearLayout


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

    private lateinit var goBack: LinearLayout

    private lateinit var arrowDownIntro: ImageView
    private lateinit var arrowDownAcceptance: ImageView
    private lateinit var arrowPersonalInfoDown: ImageView
    private lateinit var arrowNonPersonalInfoDown: ImageView
    private lateinit var arrowHowWeUseDown: ImageView
    private lateinit var arrowSharingInfoDown: ImageView
    private lateinit var arrowRetentionpolicyDown: ImageView
    private lateinit var arrowHowWeProtectDown: ImageView
    private lateinit var arrowThirdPartyDown: ImageView
    private lateinit var arrowChangeInPolicy: ImageView

    private lateinit var ProductsArrowImageLL: ImageView
    private lateinit var HealthArrowImageLL: ImageView
    private lateinit var CarArrowImageLL: ImageView
    private lateinit var BikeArrowImgdown: ImageView
    private lateinit var TermLifeArrowImgdown: ImageView
    private lateinit var TravelArrowImgdown: ImageView

    private var isIntroVisible = false
    private var isAcceptanceVisible = false
    private var isPeronalInfoVisible = false
    private var isNonPeronalInfoVisible = false
    private var isHowWeUseDatVisible = false
    private var isSharingVisible = false
    private var isretentionPolicyVisible = false
    private var isHowWeProtectVisible = false
    private var isThirdPartyWebVisible = false
    private var isArrowChangeIsVisibile = false
    private var isProductVisibile = false
    private var ishealthVisibile = false
    private var iscarVisible = false
    private var isBikeVisibile = false
    private var isTermLifeVisible = false
    private var isTravelVisibile = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        initViews()


        goBack.setOnClickListener {
            startActivity(Intent(this@PrivacyPolicy, ProfilePage::class.java))
        }

        introParentLL.setOnClickListener {
            toggleVisibility(introLL, arrowDownIntro, ::isIntroVisible, introParentLL)
        }

        acceptenceOfPrivacyPolicy.setOnClickListener {
            toggleVisibility(acceptanceLL, arrowDownAcceptance, ::isAcceptanceVisible, acceptenceOfPrivacyPolicy)
        }

        personalDataInfoParentLL.setOnClickListener {
            toggleVisibility(personalInfoLL, arrowPersonalInfoDown, ::isPeronalInfoVisible, personalDataInfoParentLL)
        }

        NonPersonalDataInfoParentLL.setOnClickListener {
            toggleVisibility(nonPersonalInfoLL, arrowNonPersonalInfoDown, ::isNonPeronalInfoVisible, NonPersonalDataInfoParentLL)
        }


        HowWeUseParentLL.setOnClickListener {
            toggleVisibility(HowWeUseLL, arrowHowWeUseDown, ::isHowWeUseDatVisible, HowWeUseParentLL)
        }

        SharingPersonalParentLL.setOnClickListener {
            toggleVisibility(SharingInfoLL, arrowSharingInfoDown, ::isSharingVisible, SharingPersonalParentLL)
        }


        RetentionpolicyParentLL.setOnClickListener {
            toggleVisibility(RetentionpolicyLL, arrowRetentionpolicyDown, ::isretentionPolicyVisible, RetentionpolicyParentLL)
        }

        HowWeProtectParentLL.setOnClickListener {
            toggleVisibility(HowWeProtectLL, arrowHowWeProtectDown, ::isHowWeProtectVisible, HowWeProtectParentLL)
        }


        ThirdPartyParentLL.setOnClickListener {
            toggleVisibility(ThirdPartyLL, arrowThirdPartyDown, ::isThirdPartyWebVisible, ThirdPartyParentLL)
        }

        ChangesInParentLL.setOnClickListener {
            toggleVisibility(ChangeInLL, arrowChangeInPolicy, ::isArrowChangeIsVisibile, ChangesInParentLL)
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

    private fun initViews() {
        introLL = findViewById(R.id.introLL)
        introParentLL = findViewById(R.id.introParentLL)
        acceptenceOfPrivacyPolicy = findViewById(R.id.acceptenceOfPrivacyPolicy)
        arrowDownIntro = findViewById(R.id.arrowDownIntro)
        acceptanceLL = findViewById(R.id.acceptanceLL)
        arrowDownAcceptance = findViewById(R.id.arrowDownAcceptance)
        arrowPersonalInfoDown = findViewById(R.id.arrowPersonalInfoDown)
        personalDataInfoParentLL = findViewById(R.id.personalDataInfoParentLL)
        personalInfoLL = findViewById(R.id.personalInfoLL)
        NonPersonalDataInfoParentLL = findViewById(R.id.NonPersonalDataInfoParentLL)
        nonPersonalInfoLL = findViewById(R.id.nonPersonalInfoLL)
        arrowNonPersonalInfoDown = findViewById(R.id.arrowNonPersonalInfoDown)
        HowWeUseParentLL = findViewById(R.id.HowWeUseParentLL)
        arrowHowWeUseDown = findViewById(R.id.arrowHowWeUseDown)
        HowWeUseLL = findViewById(R.id.HowWeUseLL)
        SharingPersonalParentLL = findViewById(R.id.SharingPersonalParentLL)
        arrowSharingInfoDown = findViewById(R.id.arrowSharingInfoDown)
        SharingInfoLL = findViewById(R.id.SharingInfoLL)
        RetentionpolicyLL = findViewById(R.id.RetentionpolicyLL)
        arrowRetentionpolicyDown = findViewById(R.id.arrowRetentionpolicyDown)
        RetentionpolicyParentLL = findViewById(R.id.RetentionpolicyParentLL)
        HowWeProtectParentLL = findViewById(R.id.HowWeProtectParentLL)
        arrowHowWeProtectDown = findViewById(R.id.arrowHowWeProtectDown)
        HowWeProtectLL = findViewById(R.id.HowWeProtectLL)
        ThirdPartyParentLL = findViewById(R.id.ThirdPartyParentLL)
        arrowThirdPartyDown = findViewById(R.id.arrowThirdPartyDown)
        ThirdPartyLL = findViewById(R.id.ThirdPartyLL)
        ChangesInParentLL = findViewById(R.id.ChangesInParentLL)
        arrowChangeInPolicy = findViewById(R.id.arrowChangeInPolicy)
        ChangeInLL = findViewById(R.id.ChangeInLL)

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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@PrivacyPolicy, ProfilePage::class.java)
        startActivity(intent)

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
}
