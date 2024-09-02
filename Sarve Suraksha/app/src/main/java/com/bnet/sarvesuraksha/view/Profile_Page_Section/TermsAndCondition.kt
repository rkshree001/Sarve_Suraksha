package com.bnet.sarvesuraksha.view.Profile_Page_Section

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import com.bnet.savresuraksha.R

class TermsAndCondition : AppCompatActivity() {
    private lateinit var goBack: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_condition)

        goBack = findViewById(R.id.goBack)


        goBack.setOnClickListener {
            val intent = Intent(this@TermsAndCondition, ProfilePage::class.java)
            startActivity(intent)
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@TermsAndCondition, ProfilePage::class.java)
        startActivity(intent)

    }
}