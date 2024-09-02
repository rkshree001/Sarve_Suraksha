package com.bnet.sarvesuraksha.view.Profile_Page_Section.Preferred_Language

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.utils.LocaleHelper
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import com.bnet.sarvesuraksha.view.adapter.CountryAdapter
import com.bnet.savresuraksha.R

class PreferredLanguage : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var videoViewParent: LinearLayout
    private lateinit var currentSelectedLL: LinearLayout
    private lateinit var selectYourRegion: LinearLayout
    private lateinit var SearchViewParent: LinearLayout
    private lateinit var changeYourLanguagePref: TextView
    private lateinit var goBack: LinearLayout

    private lateinit var searchView: SearchView
    private lateinit var listView: ListView
    private lateinit var countryAdapter: CountryAdapter

    private lateinit var toggleSearchViewButton: LinearLayout
    private lateinit var toggle_arrow_Mark: ImageView
    private lateinit var RadioGroupLL: LinearLayout
    private lateinit var updateLanguage: LinearLayout
    private lateinit var RadioGroupLanguage: RadioGroup
    private lateinit var user_selected_language_radio: RadioButton
    private lateinit var selected_radio_to_default: RadioButton

    private val originalStates = listOf(
        "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
        "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka",
        "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram",
        "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu",
        "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"
    )
    private val countries = ArrayList(originalStates)


    private val stateToLanguage = mapOf(
        "Andhra Pradesh" to "Telugu",
        "Arunachal Pradesh" to "English",
        "Assam" to "Assamese",
        "Bihar" to "Hindi",
        "Chhattisgarh" to "Hindi",
        "Goa" to "Konkani",
        "Gujarat" to "Gujarati",
        "Haryana" to "Hindi",
        "Himachal Pradesh" to "Hindi",
        "Jharkhand" to "Hindi",
        "Karnataka" to "Kannada",
        "Kerala" to "Malayalam",
        "Madhya Pradesh" to "Hindi",
        "Maharashtra" to "Marathi",
        "Manipur" to "Manipuri",
        "Meghalaya" to "English",
        "Mizoram" to "Mizo",
        "Nagaland" to "English",
        "Odisha" to "Odia",
        "Punjab" to "Punjabi",
        "Rajasthan" to "Hindi",
        "Sikkim" to "English",
        "Tamil Nadu" to "Tamil",
        "Telangana" to "Telugu",
        "Tripura" to "Bengali",
        "Uttar Pradesh" to "Hindi",
        "Uttarakhand" to "Hindi",
        "West Bengal" to "Bengali"
    )


    private var isSearchViewVisible = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferred_language)

        videoView = findViewById(R.id.videoView)
        videoViewParent = findViewById(R.id.videoViewParent)
        currentSelectedLL = findViewById(R.id.currentSelectedLL)
        changeYourLanguagePref = findViewById(R.id.changeYourLanguagePref)
        selectYourRegion = findViewById(R.id.selectYourRegion)
        SearchViewParent = findViewById(R.id.SearchViewParent)
        goBack = findViewById(R.id.goBack)
        RadioGroupLL = findViewById(R.id.RadioGroupLL)
        updateLanguage = findViewById(R.id.updateLanguage)
        RadioGroupLanguage = findViewById(R.id.RadioGroupLanguage)
        user_selected_language_radio = findViewById(R.id.user_selected_language_radio)
        selected_radio_to_default = findViewById(R.id.selected_radio_to_default)

        toggleSearchViewButton = findViewById(R.id.toggleSearchViewButton)
        toggle_arrow_Mark = findViewById(R.id.toggle_arrow_Mark)


        toggle_arrow_Mark.rotation = 180f

        changeYourLanguagePref.setOnClickListener {
            changeYourLanguagePref.visibility = View.GONE
            currentSelectedLL.visibility = View.GONE
            selectYourRegion.visibility = View.VISIBLE
        }

        selectYourRegion.setOnClickListener {
            videoViewParent.visibility = View.GONE
            selectYourRegion.visibility = View.GONE
            changeYourLanguagePref.visibility = View.GONE
            SearchViewParent.visibility = View.VISIBLE
            toggleSearchViewButton.visibility = View.VISIBLE

        }

        toggleSearchViewButton.setOnClickListener {
            toggleSearchViewVisibility()
        }

        searchView = findViewById(R.id.searchView)
        listView = findViewById(R.id.listView)

        countryAdapter = CountryAdapter(this, ArrayList(originalStates))
        listView.adapter = countryAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCountry = countryAdapter.getItem(position)

//            listView.visibility=View.GONE
            SearchViewParent.visibility=View.GONE
            RadioGroupLL.visibility=View.VISIBLE
            updateLanguage.visibility=View.VISIBLE

            val language = stateToLanguage[selectedCountry]
            user_selected_language_radio.text = language
            rotateArrowMark(0f)
//            Toast.makeText(this, "Selected: $selectedCountry", Toast.LENGTH_SHORT).show()

        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                countryAdapter.filter.filter(newText)
                return false
            }
        })

        goBack.setOnClickListener {

            startActivity(Intent(this@PreferredLanguage, ProfilePage::class.java))
        }


        selected_radio_to_default.setOnClickListener {

            selected_radio_to_default.isChecked = true
            user_selected_language_radio.isChecked = false

        }

        user_selected_language_radio.setOnClickListener {

            selected_radio_to_default.isChecked = false
            user_selected_language_radio.isChecked = true

        }


        updateLanguage.setOnClickListener {
            val selectedLanguage = when (user_selected_language_radio.text.toString()) {
                "Telugu" -> "te"
                "English" -> "en"
                "Assamese" -> "as"
                "Hindi" -> "hi"
                "Konkani" -> "kok"
                "Gujarati" -> "gu"
                "Kannada" -> "kn"
                "Malayalam" -> "ml"
                "Marathi" -> "mr"
                "Manipuri" -> "mni"
                "Mizo" -> "lus"
                "Odia" -> "or"
                "Punjabi" -> "pa"
                "Tamil" -> "ta"
                "Bengali" -> "bn"
                else -> "en"
            }
            changeAppLanguage(selectedLanguage)
        }



        playVideo()

    }

    private fun changeAppLanguage(language: String) {
        LocaleHelper.setLocale(this, language)
        val intent = Intent(this, ProfilePage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun toggleSearchViewVisibility() {
        isSearchViewVisible = !isSearchViewVisible
        Log.d("PreferredLanguage", "Toggle SearchViewVisibility: $isSearchViewVisible")
        SearchViewParent.visibility = if (isSearchViewVisible) View.VISIBLE else View.GONE
        if (isSearchViewVisible) {
            videoViewParent.visibility = View.GONE
            changeYourLanguagePref.visibility = View.GONE
            rotateArrowMark(180f)
        } else {
            rotateArrowMark(0f)
        }
    }
//    private fun toggleSearchViewVisibility() {
//        isSearchViewVisible = !isSearchViewVisible
//        Log.d("PreferredLanguage", "Toggle SearchViewVisibility: $isSearchViewVisible")
//        SearchViewParent.visibility = if (isSearchViewVisible) View.VISIBLE else View.GONE
//        if (isSearchViewVisible) {
//            videoViewParent.visibility = View.GONE
//            changeYourLanguagePref.visibility = View.GONE
//        }
//    }


    private fun rotateArrowMark(degrees: Float) {
        toggle_arrow_Mark.animate().rotation(degrees).start()
    }

    private fun playVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.language_mp4}")
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoView.start()
        }
    }
}
