package com.bnet.sarvesuraksha.view.Landing_Page_Section.Health_Insurance

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.asynctaskcoffee.cardstack.CardContainer
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3005
import com.bnet.sarvesuraksha.model_api.PostHealthInsConatiner2MainData
import com.bnet.sarvesuraksha.model_api.PostHealthInsuranceMainData
import com.bnet.sarvesuraksha.view.adapter.CardAdapter
import com.bnet.sarvesuraksha.view.adapter.CarouselAdapter
import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern

class HealthInsurance : AppCompatActivity() {

    private lateinit var fullNameTxtEditText: TextInputEditText
    private lateinit var userPhoneNumberTxtET: TextInputEditText
    private lateinit var dateOfBirthTxtEditTxt: TextInputEditText
    private lateinit var pincode: TextInputEditText
    private lateinit var Verify_TV: TextView
    private lateinit var proceed_TV: TextView
    private lateinit var emailTxt: TextInputEditText
    private lateinit var cardContainer: CardContainer

    private lateinit var viewPagerRecommended: ViewPager2
    private lateinit var landingPage: LinearLayout
    private lateinit var toolbarLL: LinearLayout
    private lateinit var main: CoordinatorLayout
    private lateinit var fotterLL: LinearLayout
    private lateinit var verifyLL: LinearLayout
    private lateinit var gif1: LinearLayout
    private lateinit var gif2: LinearLayout
    private lateinit var gif3: LinearLayout
    private lateinit var framwLayoutOP: FrameLayout
    private lateinit var proceedLL: LinearLayout
    private lateinit var indicator1: LinearLayout
    private lateinit var indicator2: LinearLayout
    private lateinit var indicator3: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var male_LL: LinearLayout
    private lateinit var female_LL: LinearLayout
    private lateinit var other_LL: LinearLayout
    private lateinit var first_conatiner: LinearLayout
    private lateinit var second_conatiner: LinearLayout
    private lateinit var progressBars: RoundedProgressBar
    private lateinit var close_icon: ImageView

    private var GenderString: String= "M"
    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val handler = Handler(Looper.getMainLooper())
    private var healthID: String? = ""
    private val updateCarousel = object : Runnable {
        override fun run() {
            val nextItem = (viewPagerRecommended.currentItem + 1) % (viewPagerRecommended.adapter?.itemCount ?: 1)
            viewPagerRecommended.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 2000)
        }
    }

    private val updateCards = object : Runnable {
        override fun run() {
            cardContainer.swipeLeft()
            handler.postDelayed(this, 1000)
        }
    }

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_insurance)

        fullNameTxtEditText = findViewById(R.id.fullNameTxtEditText)
        userPhoneNumberTxtET = findViewById(R.id.mobileNumber)
        dateOfBirthTxtEditTxt = findViewById(R.id.dateOfBirthTxtEditTxt)
        framwLayoutOP = findViewById(R.id.framwLayoutOP)
        emailTxt = findViewById(R.id.emailTxt)
        gif1 = findViewById(R.id.gif1)
        gif2 = findViewById(R.id.gif2)
        gif3 = findViewById(R.id.gif3)
        cardContainer = findViewById(R.id.cardContainer)
        first_conatiner = findViewById(R.id.first_conatiner)
        second_conatiner = findViewById(R.id.second_conatiner)
        pincode = findViewById(R.id.pincode)
        close_icon = findViewById(R.id.close_icon)
        viewPagerRecommended = findViewById(R.id.viewPagerRecommended)
        proceedLL = findViewById(R.id.proceedLL)
        landingPage = findViewById(R.id.landingPage)
        toolbarLL = findViewById(R.id.toolbarLL)
        indicator1 = findViewById(R.id.indicator1)
        indicator2 = findViewById(R.id.indicator2)
        indicator3 = findViewById(R.id.indicator3)
        scrollView = findViewById(R.id.scrollView)
        fotterLL = findViewById(R.id.fotterLL)
        main = findViewById(R.id.main)
        verifyLL = findViewById(R.id.verifyLL)
        Verify_TV = findViewById(R.id.Verify_TV)
        proceed_TV = findViewById(R.id.proceed_TV)
        progressBars = findViewById(R.id.progressBars)

        male_LL = findViewById(R.id.male_LL)
        female_LL = findViewById(R.id.female_LL)
        other_LL = findViewById(R.id.other_LL)

        verifyLL.setOnClickListener {
            validateFieldsOnclick()


        }
        proceedLL.setOnClickListener {
            validateFieldsOnclickProcced()
        }

        dateOfBirthTxtEditTxt.setOnClickListener {
            showDatePicker()
        }
        male_LL.setOnClickListener {
            GenderString="M"
            selectGenderLayout(male_LL)
        }

        female_LL.setOnClickListener {
            GenderString="F"
            selectGenderLayout(female_LL)
        }

        other_LL.setOnClickListener {
            GenderString="O"
            selectGenderLayout(other_LL)
        }


        close_icon.setOnClickListener{
            framwLayoutOP.visibility=View.GONE
            main.setBackgroundColor(Color.parseColor("#FBF6EE"))
        }
        fullNameTxtEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                landingPage.visibility = View.GONE
                fotterLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
                main.setBackgroundColor(Color.parseColor("#FBF6EE"))
            } else {
                main.setBackgroundColor(Color.parseColor("#003a37"))
            }
        }

        userPhoneNumberTxtET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                landingPage.visibility = View.GONE
                fotterLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
                main.setBackgroundColor(Color.parseColor("#FBF6EE"))
            } else {
                main.setBackgroundColor(Color.parseColor("#003a37"))
            }
        }

        dateOfBirthTxtEditTxt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                landingPage.visibility = View.GONE
                fotterLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
                main.setBackgroundColor(Color.parseColor("#FBF6EE"))
            } else {
                main.setBackgroundColor(Color.parseColor("#003a37"))
            }
        }

        pincode.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                landingPage.visibility = View.GONE
                fotterLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
                main.setBackgroundColor(Color.parseColor("#FBF6EE"))
            } else {
                main.setBackgroundColor(Color.parseColor("#003a37"))
            }
        }



        emailTxt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                landingPage.visibility = View.GONE
                fotterLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
                main.setBackgroundColor(Color.parseColor("#FBF6EE"))
            } else {
                main.setBackgroundColor(Color.parseColor("#003a37"))
            }
        }

        toolbarLL.setOnClickListener {

            if (second_conatiner.isVisible){
                first_conatiner.visibility = View.VISIBLE
                second_conatiner.visibility = View.GONE
            }else if (first_conatiner.isVisible){
                landingPage.visibility = View.VISIBLE
                fotterLL.visibility = View.VISIBLE
                main.setBackgroundColor(Color.parseColor("#003a37"))
                toolbarLL.visibility = View.GONE
            }

        }

        findViewById<View>(R.id.main).setOnTouchListener { _,  event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (currentFocus != null) {
                    if (currentFocus is EditText) {
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                        currentFocus?.clearFocus()
                        landingPage.visibility = View.VISIBLE
                        fotterLL.visibility = View.VISIBLE
                        toolbarLL.visibility = View.GONE
                    }
                }
            }
            false
        }


        val images = listOf(
            R.drawable.slider_1_car,
            R.drawable.slider_2_travel,
            R.drawable.slider_3_termlife
        )

        val adapter = CarouselAdapter(images)
        viewPagerRecommended.adapter = adapter

        viewPagerRecommended.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
            }
        })

        scrollView.setOnTouchListener { v, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (isTouchInsideCardContainer(event)) {
                        scrollView.requestDisallowInterceptTouchEvent(true)
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    scrollView.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }


        cardContainer.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                scrollView.requestDisallowInterceptTouchEvent(true)
            }
            false
        }

        handler.postDelayed(updateCarousel, 2000)
        handler.postDelayed(updateCards, 2000)

        setupInputFilters()

        val images1 = listOf(
            R.drawable.stack_card1,
            R.drawable.stack_card2,
            R.drawable.stack_card3
        )
        cardContainer.maxStackSize = 3
        val adapter1 = CardAdapter(this, images1)
        cardContainer.setAdapter(adapter1)
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val selectedDate = calendar.time

                if (isAgeValid(selectedDate)) {
                    dateOfBirthTxtEditTxt.setText(dateFormat.format(selectedDate))

                } else {
                    dateOfBirthTxtEditTxt.setText("")

                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        val eighteenYearsAgo = Calendar.getInstance()
        eighteenYearsAgo.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.maxDate = eighteenYearsAgo.timeInMillis

        datePickerDialog.show()
    }
    private fun isAgeValid(dateOfBirth: Date): Boolean {
        val dobCalendar = Calendar.getInstance()
        dobCalendar.time = dateOfBirth

        val eighteenYearsAgo = Calendar.getInstance()
        eighteenYearsAgo.add(Calendar.YEAR, -18)

        return dobCalendar.before(eighteenYearsAgo)
    }


    private fun selectGenderLayout(selectedLayout: LinearLayout) {

        male_LL.setBackgroundResource(R.drawable.gender_default)
        female_LL.setBackgroundResource(R.drawable.gender_default)
        other_LL.setBackgroundResource(R.drawable.gender_default)


        selectedLayout.setBackgroundResource(R.drawable.gender_selected)
    }
    private fun validateFieldsOnclick() {
        val name = fullNameTxtEditText.text.toString().trim()
        val phoneNumber = userPhoneNumberTxtET.text.toString().trim()
        val email = emailTxt.text.toString().trim()

        val isNameValid = name.isNotEmpty() && name.matches(Regex("^[a-zA-Z ]+$"))
        val isPhoneNumberValid = isValidIndianPhoneNumber(phoneNumber)
        val isEmailValid = isValidEmailFormat(email)

        if (isNameValid && isPhoneNumberValid && isEmailValid) {
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            Verify_TV.setTextColor(Color.BLACK)
            main.setBackgroundColor(Color.parseColor("#FBF6EE"))
            PostUserDetails(name,phoneNumber,email)
        } else {
            showToast("Please Enter all the Fields!!")
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            Verify_TV.setTextColor(resources.getColor(R.color.verify_def_grey))

            first_conatiner.visibility = View.VISIBLE
            second_conatiner.visibility = View.GONE
        }
    }

    private fun validateFieldsOnclickProcced() {
        val name = dateOfBirthTxtEditTxt.text.toString().trim()
        val pincode = pincode.text.toString().trim()

        val isNameValid = name.isNotEmpty()


        if (isNameValid && !pincode.isEmpty()) {
            proceedLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            proceed_TV.setTextColor(Color.BLACK)

            PostUserDetailsC2()

        } else {
            proceedLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            proceed_TV.setTextColor(resources.getColor(R.color.verify_def_grey))

        }
    }
    private fun validateFields() {
        val name = fullNameTxtEditText.text.toString().trim()
        val phoneNumber = userPhoneNumberTxtET.text.toString().trim()
        val email = emailTxt.text.toString().trim()

        val isNameValid = name.isNotEmpty() && name.matches(Regex("^[a-zA-Z ]+$"))
        val isPhoneNumberValid = isValidIndianPhoneNumber(phoneNumber)
        val isEmailValid = isValidEmailFormat(email)

        if (isNameValid && isPhoneNumberValid && isEmailValid) {
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            Verify_TV.setTextColor(Color.BLACK)
        } else {
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            Verify_TV.setTextColor(resources.getColor(R.color.verify_def_grey))

        }
    }
    private fun validateFieldsProcced() {
        val name = dateOfBirthTxtEditTxt.text.toString().trim()
        val pincode = pincode.text.toString().trim()

        val isNameValid = name.isNotEmpty()


        if (isNameValid && !pincode.isEmpty()) {
            proceedLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            proceed_TV.setTextColor(Color.BLACK)
        } else {
            proceedLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            proceed_TV.setTextColor(resources.getColor(R.color.verify_def_grey))

        }
    }


    private fun isTouchInsideCardContainer(event: MotionEvent): Boolean {
        val location = IntArray(2)
        cardContainer.getLocationOnScreen(location)
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        val left = location[0]
        val right = location[0] + cardContainer.width
        val top = location[1]
        val bottom = location[1] + cardContainer.height
        return x in left..right && y in top..bottom
    }

    private fun updateIndicators(position: Int) {
        indicator1.visibility = View.INVISIBLE
        indicator2.visibility = View.INVISIBLE
        indicator3.visibility = View.INVISIBLE

        when (position % 3) {
            0 -> indicator1.visibility = View.VISIBLE
            1 -> indicator2.visibility = View.VISIBLE
            2 -> indicator3.visibility = View.VISIBLE
        }
    }


    private fun setupInputFilters() {
        fullNameTxtEditText.filters = arrayOf(
            InputFilter { source, _, _, _, _, _ ->
                if (source.matches(Regex("^[a-zA-Z ]*$"))) {

                    return@InputFilter source

                } else {
                    ""
                }
            }
        )

        val maxLength = 10
        userPhoneNumberTxtET.filters = arrayOf(
            InputFilter { source, start, end, dest, dstart, dend ->
                if (dstart == 0 && source.isNotEmpty() && source[0] in '6'..'9') {
                    return@InputFilter source.filter { it.isDigit() }
                } else if (dstart > 0 && source.all { it.isDigit() }) {
                    return@InputFilter source
                } else {
                    ""
                }
            },
            InputFilter.LengthFilter(maxLength)
        )

        emailTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()

                if (isValidEmailFormat(email)) {

                    validateFields()
                } else {

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        fullNameTxtEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 4) {
                    validateFields()

                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })

        dateOfBirthTxtEditTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 4) {
                    validateFieldsProcced()

                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })


        pincode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length ==6) {
                    validateFieldsProcced()
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })


        userPhoneNumberTxtET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNum = userPhoneNumberTxtET.text.toString().trim()
                if (isValidIndianPhoneNumber(phoneNum)) {
                    validateFields()

                } else {

                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun isValidIndianPhoneNumber(phone: String): Boolean {
        val pattern = Pattern.compile("^[6789]\\d{9}$")
        return pattern.matcher(phone).matches()
    }

    private fun isValidEmailFormat(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return Regex(emailPattern).matches(email)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun PostUserDetails(name: String, phoneNumber: String, email: String) {


        val json = JSONObject().apply {
            put("basicDetail", JSONObject().apply {
                put("fullName", name)
                put("mobileNumber", phoneNumber)
                put("email", email)
            })
        }

        Log.d("PostUserDetails", "Request JSON: $json")

        val call: Call<PostHealthInsuranceMainData?>? =
            MyApplicationPort3005.getRetrofitClient(this@HealthInsurance).create<ApiInterface>(
                ApiInterface::class.java
            ).postQouteFormBasicDetail(json.toString())

        call?.enqueue(object : Callback<PostHealthInsuranceMainData?> {
            override fun onResponse(
                call: Call<PostHealthInsuranceMainData?>,
                response: Response<PostHealthInsuranceMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: PostHealthInsuranceMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostUserDetails", "onResponse 1: " + response.raw() + "~" + json.toString())
                            Toast.makeText(this@HealthInsurance, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            first_conatiner.visibility = View.GONE
                            second_conatiner.visibility = View.VISIBLE

                            healthID=mains!!.data!!.id

                            progressBars.setProgressPercentage(70.00)
                            gif1.visibility=View.INVISIBLE
                            gif2.visibility=View.VISIBLE
                            gif3.visibility=View.INVISIBLE
                            main.setBackgroundColor(Color.parseColor("#FBF6EE"))
                        }
                    } catch (e: Exception) {
                        Log.d("PostUserDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@HealthInsurance, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostUserDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@HealthInsurance, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostHealthInsuranceMainData?>?, t: Throwable) {
                Log.d("PostUserDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@HealthInsurance, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })



    }

    private fun PostUserDetailsC2() {
        if (!healthID!!.isNullOrEmpty()){
            val json = JSONObject()


            val dobString = dateOfBirthTxtEditTxt.text.toString().trim()
            val isoDob = convertToISOFormat(dobString)


            json.put("DOB", isoDob)
            json.put("pincode", pincode.text.toString())
            json.put("gender", GenderString)


            Log.d("PostUserDetailsC2", "Request JSON: $json")

            val call: Call<PostHealthInsConatiner2MainData?>? =
                MyApplicationPort3005.getRetrofitClient(this@HealthInsurance).create<ApiInterface>(
                    ApiInterface::class.java
                ).postQouteFormBasicDetailC2(healthID.toString(),json.toString())

            call?.enqueue(object : Callback<PostHealthInsConatiner2MainData?> {
                override fun onResponse(
                    call: Call<PostHealthInsConatiner2MainData?>,
                    response: Response<PostHealthInsConatiner2MainData?>
                ) {
                    if (response.isSuccessful) {
                        val main: PostHealthInsConatiner2MainData? = response.body()
                        try {
                            if (response.isSuccessful) {
                                Log.d("PostUserDetailsC2", "onResponse 1: " + response.raw() + "~" + json.toString())
                                Toast.makeText(this@HealthInsurance, response.body()!!.message, Toast.LENGTH_SHORT).show()
                                gif1.visibility=View.INVISIBLE
                                gif2.visibility=View.INVISIBLE
                                gif3.visibility=View.INVISIBLE

                                progressBars.setProgressPercentage(100.00)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    framwLayoutOP.visibility = View.VISIBLE
                                }, 1000)

                                progressBars.setProgressDrawableColor(Color.parseColor("#00AF26"))
                            }
                        } catch (e: Exception) {
                            Log.d("PostUserDetailsC2", "onResponse 2: " + response.raw() + "~" + json.toString())
                            Toast.makeText(this@HealthInsurance, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("PostUserDetailsC2", "onResponse 3: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@HealthInsurance, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostHealthInsConatiner2MainData?>?, t: Throwable) {
                    Log.d("PostUserDetailsC2", "onFailure: " + t.localizedMessage + "~" + json.toString())
                    Toast.makeText(this@HealthInsurance, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
                }
            })

        }else{
            Toast.makeText(this, "Something Went Wrong !! please Try again Later", Toast.LENGTH_LONG).show()
        }

    }

    private fun convertToISOFormat(dobString: String): String? {
        return try {

            val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = inputFormat.parse(dobString)


            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getTimeZone("UTC")
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
