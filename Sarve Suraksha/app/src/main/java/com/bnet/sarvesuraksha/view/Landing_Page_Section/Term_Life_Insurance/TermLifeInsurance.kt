package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bnet.savresuraksha.R

import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3006
import com.bnet.sarvesuraksha.model_api.PostTLPersonalDetailMainData
import com.bnet.sarvesuraksha.model_api.PostTLQuoteFromBasicMainData
import com.google.android.material.textfield.TextInputEditText
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

class TermLifeInsurance : AppCompatActivity() {

    private val imageList = arrayOf(
        R.drawable.termlife1,
        R.drawable.termlife2,
        R.drawable.termlife3,
        R.drawable.termlife4,
        R.drawable.termlife5
    )

    private lateinit var changeImagesCarousel: ImageView
    private lateinit var carouselLL: LinearLayout
    private lateinit var blurLL: LinearLayout

    private var currentImageIndex = 0
    private val imageChangeDelay = 2000L

    private val handler = Handler()
    private lateinit var fullNameTxtEditText: TextInputEditText
    private lateinit var userPhoneNumberTxtET: TextInputEditText

    private lateinit var dateOfBirthTxtEditTxt: TextInputEditText
    private lateinit var pincode: TextInputEditText
    private lateinit var Verify_TV: TextView
    private lateinit var headerTV: TextView
    private lateinit var proceed_TV: TextView
    private lateinit var verifyLL: LinearLayout
    private lateinit var proceedLL: LinearLayout
    private lateinit var first_conatiner: LinearLayout
    private lateinit var second_conatiner: LinearLayout
    private lateinit var toolbarLL: LinearLayout

    private var TermID: String? = ""
    private var GenderString: String= "M"
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val calendar: Calendar = Calendar.getInstance()

    private lateinit var male_LL: LinearLayout
    private lateinit var female_LL: LinearLayout
    private lateinit var other_LL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_life_insurance)
        dateOfBirthTxtEditTxt = findViewById(R.id.dateOfBirthTxtEditTxt)
        pincode = findViewById(R.id.pincode)
        changeImagesCarousel = findViewById(R.id.changeImagesCarousel)
        carouselLL = findViewById(R.id.carouselLL)
        blurLL = findViewById(R.id.blurLL)
        fullNameTxtEditText = findViewById(R.id.fullNameTxtEditText)
        userPhoneNumberTxtET = findViewById(R.id.mobileNumber)
        verifyLL = findViewById(R.id.verifyLL)
        first_conatiner = findViewById(R.id.first_conatiner)
        Verify_TV = findViewById(R.id.Verify_TV)
        second_conatiner = findViewById(R.id.second_conatiner)
        proceedLL = findViewById(R.id.proceedLL)
        proceed_TV = findViewById(R.id.proceed_TV)
        male_LL = findViewById(R.id.male_LL)
        female_LL = findViewById(R.id.female_LL)
        other_LL = findViewById(R.id.other_LL)
        toolbarLL = findViewById(R.id.toolbarLL)
        headerTV = findViewById(R.id.headerTV)


        val rootLayout = findViewById<CoordinatorLayout>(R.id.rootLayout)


        rootLayout.setOnClickListener {

            fullNameTxtEditText.clearFocus()
            userPhoneNumberTxtET.clearFocus()
            pincode.clearFocus()

            toolbarLL.visibility = View.GONE
            carouselLL.visibility = View.VISIBLE
            headerTV.visibility = View.VISIBLE
            blurLL.visibility = View.VISIBLE
        }


        fullNameTxtEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                headerTV.visibility = View.GONE
                carouselLL.visibility = View.GONE
                blurLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE

            } else {

            }
        }
        dateOfBirthTxtEditTxt.setOnClickListener {
            showDatePicker()
        }
        userPhoneNumberTxtET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                carouselLL.visibility = View.GONE
                headerTV.visibility = View.GONE
                blurLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
            } else {

            }
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

        fullNameTxtEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 4) {
                    validateFields()

                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })

        userPhoneNumberTxtET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNum = userPhoneNumberTxtET.text.toString().trim()
                if (s!!.length==10) {
                    validateFields()

                } else {
                    validateFields()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        dateOfBirthTxtEditTxt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                headerTV.visibility = View.GONE
                carouselLL.visibility = View.GONE
                blurLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
            } else {

            }
        }

        pincode.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                headerTV.visibility = View.GONE
                carouselLL.visibility = View.GONE
                blurLL.visibility = View.GONE
                toolbarLL.visibility = View.VISIBLE
            } else {

            }
        }

        pincode.setOnClickListener {
            carouselLL.visibility = View.GONE
            blurLL.visibility = View.GONE
            toolbarLL.visibility = View.VISIBLE
            headerTV.visibility = View.GONE
        }
        verifyLL.setOnClickListener {
            validateFieldsOnclick()
        }

        proceedLL.setOnClickListener {
            validateFieldsOnclickProcced()
        }

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

        toolbarLL.setOnClickListener {

            if (second_conatiner.isVisible){
                first_conatiner.visibility = View.VISIBLE
                second_conatiner.visibility = View.GONE
            }else if (first_conatiner.isVisible){
                carouselLL.visibility = View.VISIBLE
                blurLL.visibility = View.VISIBLE
                toolbarLL.visibility = View.GONE
            }

        }

        handler.post(imageChangerRunnable)
    }

    private fun validateFieldsProcced() {
        val name = dateOfBirthTxtEditTxt.text.toString().trim()
        val pincode = pincode.text.toString().trim()

        val isNameValid = name.isNotEmpty()


        if (isNameValid && !pincode.isEmpty()&&pincode.length==6) {
            proceedLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            proceed_TV.setTextColor(Color.BLACK)
        } else {
            proceedLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            proceed_TV.setTextColor(resources.getColor(R.color.verify_def_grey))

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

    private fun PostUserDetailsC2() {
        if (!TermID!!.isNullOrEmpty()){
            val json = JSONObject()
            val dobString = dateOfBirthTxtEditTxt.text.toString().trim()
            val isoDob = convertToISOFormat(dobString)
            json.put("DOB", isoDob)
            json.put("pincode", pincode.text.toString())
            json.put("gender", GenderString)

            Log.d("PostUserDetailsC2", "Request JSON: $json")

            val call: Call<PostTLPersonalDetailMainData?>? =
                MyApplicationPort3006.getRetrofitClient(this@TermLifeInsurance).create<ApiInterface>(
                    ApiInterface::class.java
                ).postQouteFormTLC2(TermID.toString(),json.toString())

            call?.enqueue(object : Callback<PostTLPersonalDetailMainData?> {
                override fun onResponse(
                    call: Call<PostTLPersonalDetailMainData?>,
                    response: Response<PostTLPersonalDetailMainData?>
                ) {
                    if (response.isSuccessful) {
                        val main: PostTLPersonalDetailMainData? = response.body()
                        try {
                            if (response.isSuccessful) {
                                Log.d("PostUserDetailsC2", "onResponse 1: " + response.raw() + "~" + json.toString())
                                TermID=main!!.data!!.id

                                Toast.makeText(this@TermLifeInsurance, response.body()!!.message, Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@TermLifeInsurance, TermLifeInsurancePostQuoteForm::class.java)
                                intent.putExtra("TermID", TermID)
                                startActivity(intent)

//                                Handler(Looper.getMainLooper()).postDelayed({
//                                    framwLayoutOP.visibility = View.VISIBLE
//                                }, 1000)

                            }
                        } catch (e: Exception) {
                            Log.d("PostUserDetailsC2", "onResponse 2: " + response.raw() + "~" + json.toString())
                            Toast.makeText(this@TermLifeInsurance, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("PostUserDetailsC2", "onResponse 3: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@TermLifeInsurance, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostTLPersonalDetailMainData?>?, t: Throwable) {
                    Log.d("PostUserDetailsC2", "onFailure: " + t.localizedMessage + "~" + json.toString())
                    Toast.makeText(this@TermLifeInsurance, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
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

    private fun validateFields() {
        val name = fullNameTxtEditText.text.toString().trim()
        val phoneNumber = userPhoneNumberTxtET.text.toString().trim()

        val isNameValid = name.isNotEmpty() && name.matches(Regex("^[a-zA-Z ]+$"))
        val isPhoneNumberValid = isValidIndianPhoneNumber(phoneNumber)

        if (isNameValid && isPhoneNumberValid && phoneNumber.length==10) {
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            Verify_TV.setTextColor(Color.BLACK)
        } else {
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            Verify_TV.setTextColor(resources.getColor(R.color.verify_def_grey))

        }
    }
    private fun validateFieldsOnclick() {
        val name = fullNameTxtEditText.text.toString().trim()
        val phoneNumber = userPhoneNumberTxtET.text.toString().trim()


        val isNameValid = name.isNotEmpty() && name.matches(Regex("^[a-zA-Z ]+$"))
        val isPhoneNumberValid = isValidIndianPhoneNumber(phoneNumber)

        if (isNameValid && isPhoneNumberValid) {
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            Verify_TV.setTextColor(Color.BLACK)

            PostUserDetails(name,phoneNumber)
        } else {
            verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            Verify_TV.setTextColor(resources.getColor(R.color.verify_def_grey))

        }
    }

    private fun PostUserDetails(name: String, phoneNumber: String) {


        val json = JSONObject().apply {
            put("logInId", phoneNumber)
            put("basicDetail", JSONObject().apply {
                put("fullName", name)
                put("mobileNumber", phoneNumber)
            })
        }

        Log.d("PostUserDetailsC1", "Request JSON: $json")

        val call: Call<PostTLQuoteFromBasicMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@TermLifeInsurance).create<ApiInterface>(
                ApiInterface::class.java
            ).postQouteFormBasicDetailTL(json.toString())

        call?.enqueue(object : Callback<PostTLQuoteFromBasicMainData?> {
            override fun onResponse(
                call: Call<PostTLQuoteFromBasicMainData?>,
                response: Response<PostTLQuoteFromBasicMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: PostTLQuoteFromBasicMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostUserDetailsC1", "onResponse 1: " + response.raw() + "~" + json.toString())
                            Toast.makeText(this@TermLifeInsurance, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            first_conatiner.visibility = View.GONE
                            second_conatiner.visibility = View.VISIBLE

                            TermID=mains!!.data!!.id

                        }
                    } catch (e: Exception) {
                        Log.d("PostUserDetailsC1", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@TermLifeInsurance, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostUserDetailsC1", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@TermLifeInsurance, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostTLQuoteFromBasicMainData?>?, t: Throwable) {
                Log.d("PostUserDetailsC1", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@TermLifeInsurance, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })



    }

    private fun isValidIndianPhoneNumber(phone: String): Boolean {
        val pattern = Pattern.compile("^[6789]\\d{9}$")
        return pattern.matcher(phone).matches()
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

        male_LL.setBackgroundResource(R.drawable.gender_default_plain)
        female_LL.setBackgroundResource(R.drawable.gender_default_plain)
        other_LL.setBackgroundResource(R.drawable.gender_default_plain)


        selectedLayout.setBackgroundResource(R.drawable.gender_selected)
    }

    private val imageChangerRunnable = object : Runnable {
        override fun run() {
            val enterAnimation = AnimationUtils.loadAnimation(this@TermLifeInsurance, R.anim.enter_animation)
            val exitAnimation = AnimationUtils.loadAnimation(this@TermLifeInsurance, R.anim.exit_animation)


            changeImagesCarousel.startAnimation(exitAnimation)
            exitAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {

                    changeImagesCarousel.setImageResource(imageList[currentImageIndex])
                    changeImagesCarousel.startAnimation(enterAnimation)
                    currentImageIndex = (currentImageIndex + 1) % imageList.size
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })

            handler.postDelayed(this, imageChangeDelay)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(imageChangerRunnable)
    }
}
