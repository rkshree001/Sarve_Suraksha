package com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplication
import com.bnet.sarvesuraksha.api.MyApplicationPort3009
import com.bnet.sarvesuraksha.api.MyApplicationPort9001
import com.bnet.sarvesuraksha.model_api.CommonData
import com.bnet.sarvesuraksha.model_api.GetUserDeatilsMainGet
import com.bnet.sarvesuraksha.model_api.GetUserDeatilsMainRes
import com.bnet.sarvesuraksha.model_api.UserDeatilMainGet
import com.bnet.sarvesuraksha.model_api.VerifyOtpMainGet
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Landing_Page.LandingPage
import com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Family.MyFamilyProfilePage
import com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Policies.MyPolicies
import com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Vehicles.MyVehicles
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Preferred_Language.PreferredLanguage
import com.bnet.sarvesuraksha.view.Profile_Page_Section.AboutSarveSuraksha
import com.bnet.sarvesuraksha.view.Profile_Page_Section.PrivacyPolicy
import com.bnet.sarvesuraksha.view.Profile_Page_Section.TermsAndCondition
import com.bnet.sarvesuraksha.view.User_Basic_Detail.UserBasicDetail
import com.bnet.savresuraksha.R
import com.github.kwasow.bottomnavigationcircles.BottomNavigationCircles
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.otpview.OTPListener
import com.otpview.OTPTextView
import jp.wasabeef.blurry.Blurry
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.InetAddress
import kotlin.math.roundToInt

class ProfilePage : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationCircles
    private lateinit var goBack: LinearLayout
    private lateinit var userLoggedInMobileNumber: TextView
    private lateinit var userLoggedInUserName: TextView
    private lateinit var sign_In: LinearLayout
    private lateinit var basicDetailPage: LinearLayout
    private lateinit var MyVehiclesPage: LinearLayout
    private lateinit var PreferredLanguagePage: LinearLayout
    private lateinit var myFamilyPage: LinearLayout
    private lateinit var MyPoliciesPage: LinearLayout
    private lateinit var sign_in_parent: LinearLayout
    private lateinit var userName_LL: LinearLayout
    private lateinit var logOutLL: LinearLayout
    private lateinit var sign_in_main_ll: LinearLayout
    private lateinit var otpHasSentTo: TextView
    private lateinit var resendOtpTimer: TextView
    private lateinit var resendOtp: TextView
    private lateinit var verifyLL: LinearLayout
    private lateinit var mainLayout: RelativeLayout
    private lateinit var otp_main_ll: LinearLayout
    private lateinit var mainLayoutParent: RelativeLayout
    private lateinit var otp_view: OTPTextView
    private var countDownTimer: CountDownTimer? = null
    private var mobileNumberString: String? = null
    private var otpString: String = ""
    private var dataValidation: String = "No"
    private var dataValidationIntent: String = "Yes"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var termsAndConditions: LinearLayout
    private lateinit var privacyPolicy: LinearLayout
    private lateinit var aboutSarveSuraksha: LinearLayout
    private lateinit var profile_completion: LinearLayout
    private lateinit var ProfileImage: ImageView
    private lateinit var progressBar: RoundedProgressBar
    private lateinit var progressText: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)


        sharedPreferences = getSharedPreferences("com.bnet.sarvesuraksha", Context.MODE_PRIVATE)
        mainLayoutParent = findViewById(R.id.main_layout)
        userName_LL = findViewById(R.id.userName_LL)
        logOutLL = findViewById(R.id.logOutLL)
        sign_in_parent = findViewById(R.id.sign_in_parent)
        userLoggedInMobileNumber = findViewById(R.id.userLoggedInMobileNumber)
        userLoggedInUserName = findViewById(R.id.userLoggedInUserName)
        MyVehiclesPage = findViewById(R.id.MyVehiclesPage)
        PreferredLanguagePage = findViewById(R.id.PreferredLanguagePage)
        basicDetailPage = findViewById(R.id.basicDetailPage)
        myFamilyPage = findViewById(R.id.myFamilyPage)
        MyPoliciesPage = findViewById(R.id.MyPoliciesPage)
        termsAndConditions = findViewById(R.id.termsAndConditions)
        privacyPolicy = findViewById(R.id.privacyPolicy)
        aboutSarveSuraksha = findViewById(R.id.aboutSarveSuraksha)
        profile_completion = findViewById(R.id.profile_completion)
        ProfileImage = findViewById(R.id.ProfileImage)
        progressBar = findViewById<RoundedProgressBar>(R.id.progressBar)
        progressText = findViewById(R.id.progressText)

        logOutLL.setOnClickListener {
            ShowLogOutConfirmation()
        }



        val storedMobileNumber = sharedPreferences.getString("mobile_number", "")
        val storedUserName = sharedPreferences.getString("user_name", "")

        Log.d("storedUserName", "onCreate: "+storedUserName)
//        if (!storedMobileNumber.isNullOrEmpty()) {
        userLoggedInMobileNumber.text = storedMobileNumber
        userLoggedInUserName.text = storedUserName
        GetUserDetail(storedMobileNumber.toString())
//        } else {
//            Log.e("ProfilePage", "Stored mobile number is null or empty")
//        }

    //        storedMobileNumber?.takeIf { it.isNotEmpty() }?.let {
//            GetUserDetail(it)
 //       }


        sign_In = findViewById(R.id.sign_In)
        goBack = findViewById(R.id.goBack)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.menu.findItem(R.id.menu_Profile).isChecked = true
        bottomNavigationView.itemRippleColor = null
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    startActivity(Intent(this@ProfilePage, LandingPage::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.menu_Claims, R.id.menu_Policies, R.id.menu_Profile, R.id.menu_More -> true
                else -> false
            }
        }



        basicDetailPage.setOnClickListener {
            validateBeforeNavigation {
                startActivity(Intent(this@ProfilePage, UserBasicDetail::class.java).putExtra("dataValidation", dataValidation))
            }
        }

        goBack.setOnClickListener {
            startActivity(Intent(this@ProfilePage, LandingPage::class.java))
        }

        myFamilyPage.setOnClickListener {
            validateBeforeNavigation {
                startActivity(Intent(this@ProfilePage, MyFamilyProfilePage::class.java))
            }


        }
        MyPoliciesPage.setOnClickListener {
            validateBeforeNavigation {
                startActivity(Intent(this@ProfilePage, MyPolicies::class.java))
            }

        }
        MyVehiclesPage.setOnClickListener {
            validateBeforeNavigation {
                startActivity(Intent(this@ProfilePage, MyVehicles::class.java))
            }
        }


        PreferredLanguagePage.setOnClickListener {
            startActivity(Intent(this@ProfilePage, PreferredLanguage::class.java))
        }

        termsAndConditions.setOnClickListener {
            startActivity(Intent(this@ProfilePage, TermsAndCondition::class.java))
        }


        privacyPolicy.setOnClickListener {
            startActivity(Intent(this@ProfilePage, PrivacyPolicy::class.java))
        }

        aboutSarveSuraksha.setOnClickListener {
            startActivity(Intent(this@ProfilePage, AboutSarveSuraksha::class.java))
        }

        sign_In.setOnClickListener {
            showSignInBottomSheet()
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

    private fun validateBeforeNavigation(navigate: () -> Unit) {
        runOnUiThread {
            val storedMobileNumber = sharedPreferences.getString("mobile_number", null)
            if (dataValidationIntent.equals("No")) {
//            if (storedMobileNumber.isNullOrEmpty()) {
                showSignInBottomSheet()
//                Toast.makeText(this, "Please log in to continue", Toast.LENGTH_SHORT).show()
            } else {
                navigate()
            }
        }
    }

    private fun ShowLogOutConfirmation() {
        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.log_out_confirmation, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val no_BTN = alertDialogView.findViewById<LinearLayout>(R.id.no_BTN)
        val yes_LogOut = alertDialogView.findViewById<LinearLayout>(R.id.yes_LogOut)



        closeIcon.setOnClickListener {
            alertDialog.dismiss()
        }

        no_BTN.setOnClickListener {

            alertDialog.dismiss()

        }

        yes_LogOut.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            sharedPreferences.edit().remove("mobile_number").apply()
            userLoggedInMobileNumber.text = ""
            userLoggedInUserName.text = ""
            logOutLL.visibility = View.GONE
            sign_in_parent.visibility = View.VISIBLE
            userName_LL.visibility = View.GONE
            profile_completion.visibility = View.GONE
            alertDialog.dismiss()
            ProfileImage.setImageResource(R.drawable.profile_user_default)

        }

        alertDialog.show()
    }
    private fun fetchImage(profilePhoto: String?) {
        if (profilePhoto == null) {
            Log.e("FetchImage", "Profile photo URL is null")
            return
        }

        val call = MyApplicationPort9001.getRetrofitClient(this@ProfilePage).create(ApiInterface::class.java)
            .profilePhoto(profilePhoto)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val inputStream = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    ProfileImage.setImageBitmap(bitmap)
                    Log.d("FetchImage", "Request URL1: ${call.request().url}"+profilePhoto)

                } else {
                    Log.e("FetchImage", "Error: ${response.code()} - ${response.message()}")
                    Log.d("FetchImage", "Request URL2: ${call.request().url}"+profilePhoto)

                    Toast.makeText(this@ProfilePage, "Failed to fetch image", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("FetchImage", "Failure: ${t.message}", t)
                Log.d("FetchImage", "Request URL3: ${call.request().url}"+profilePhoto)

                Toast.makeText(this@ProfilePage, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showSignInBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.sign_in_bottom_sheet, null)

        setupBottomSheetView(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)

//        mainLayoutParent.let {
//            Blurry.with(this)
//                .radius(3)
//                .sampling(1)
//                .onto(it)
//        }

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()

        bottomSheetDialog.setOnDismissListener {
//            Blurry.delete(mainLayoutParent)
        }
    }

    private fun setupBottomSheetView(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        mainLayout = bottomSheetView.findViewById<RelativeLayout>(R.id.main)
        verifyLL = bottomSheetView.findViewById<LinearLayout>(R.id.verifyLL)
        val parentMobileLL = bottomSheetView.findViewById<LinearLayout>(R.id.parentMobileLL)
        val Verify_TV = bottomSheetView.findViewById<TextView>(R.id.Verify_TV)
        val skip_TV = bottomSheetView.findViewById<TextView>(R.id.skip_TV)
        val SignInTV = bottomSheetView.findViewById<TextView>(R.id.SignInTV)
        val mobileNumber = bottomSheetView.findViewById<TextInputEditText>(R.id.mobileNumber)
        val gifCheck = bottomSheetView.findViewById<ImageView>(R.id.gifCheck)
        sign_in_main_ll = bottomSheetView.findViewById<LinearLayout>(R.id.sign_in_main_ll)
        resendOtp = bottomSheetView.findViewById<TextView>(R.id.resendOtp)
        otpHasSentTo = bottomSheetView.findViewById<TextView>(R.id.otpHasSentTo)
        resendOtpTimer = bottomSheetView.findViewById<TextView>(R.id.resendOtpTimer)
        otp_main_ll = bottomSheetView.findViewById<LinearLayout>(R.id.otp_main_ll)
        val edit_mobile_number = bottomSheetView.findViewById<LinearLayout>(R.id.edit_mobile_number)
        val SignInLL = bottomSheetView.findViewById<LinearLayout>(R.id.SignInLL)
        otp_view = bottomSheetView.findViewById(R.id.otp_view)



        otp_view.requestFocusOTP()
        otp_view.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                otpString=otp
                SignInLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
                SignInTV.setTextColor(Color.BLACK)
                otp_view.hideKeyboard()

                SignInTV.setOnClickListener {
                    if (otpString.length == 4) {
                        VerifyOtp(mobileNumberString!!, otpString,bottomSheetDialog)
                    } else {
                        Toast.makeText(this@ProfilePage, "Please enter a valid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)
        otpHasSentTo.paintFlags = otpHasSentTo.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        mobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length == 10) {
                    parentMobileLL.setBackgroundResource(R.drawable.log_out_bg_green_stroke)
                    verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
                    Verify_TV.setTextColor(Color.BLACK)
                    gifCheck.visibility = View.VISIBLE
                    mobileNumber.hideKeyboard()
                } else {
                    parentMobileLL.setBackgroundResource(R.drawable.log_out_bg)
                    gifCheck.visibility = View.INVISIBLE
                    verifyLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
                    Verify_TV.setTextColor(resources.getColor(R.color.verify_def_grey))
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val startCountdownTimer: () -> Unit = {
            countDownTimer?.cancel()
            countDownTimer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsLeft = millisUntilFinished / 1000
                    resendOtpTimer.text = "00:${String.format("%02d", secondsLeft)}s"
                }

                override fun onFinish() {
                    resendOtpTimer.text = ""
                    resendOtp.isEnabled = true
                    resendOtp.setTextColor(Color.RED)
                    resendOtp.text = "Resend Otp"
                }
            }
            countDownTimer?.start()
            resendOtp.isEnabled = false
            resendOtp.setTextColor(Color.GRAY)
        }

        verifyLL.setOnClickListener {
            val enteredMobileNumber = mobileNumber.text?.toString()

            if (enteredMobileNumber?.length == 10) {
                mobileNumberString = enteredMobileNumber

                SendOtp(mobileNumberString!!)
//                FindUser(mobileNumberString!!)

            }
        }

        resendOtp.setOnClickListener {
            SendOtp(mobileNumberString.toString())
            startCountdownTimer()
        }

        edit_mobile_number.setOnClickListener {
            otp_main_ll.visibility = View.GONE
            sign_in_main_ll.visibility = View.VISIBLE
            verifyLL.visibility = View.VISIBLE
            countDownTimer?.cancel()
            resendOtpTimer.text = ""
            resendOtp.isEnabled = false
            resendOtp.setTextColor(Color.GRAY)
        }

        skip_TV.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }


    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun FindUser(mobileNumberString: String) {
        val call: Call<UserDeatilMainGet?>? = MyApplication.getRetrofitClient(this@ProfilePage).create(ApiInterface::class.java).findUser(mobileNumberString)
        Log.d("ProfilePageUU", "findUserDetails URL: ${call?.request()?.url}")
        call?.enqueue(object : Callback<UserDeatilMainGet?> {
            override fun onResponse(call: Call<UserDeatilMainGet?>, response: Response<UserDeatilMainGet?>) {
                if (response.isSuccessful) {
                    val userDetails: UserDeatilMainGet? = response.body()
                    Log.d("ProfilePageUU", "findUserDetails onResponse: Success")
                    if (userDetails != null) {

                        Log.d("ProfilePageUU", "User Details: $userDetails"+"~~"+ userDetails.data!!.userDetail!!.mobileNumber)
                    } else {
                        Log.d("ProfilePageUU", "User Details are null")
                    }
                } else {

                    Log.e("ProfilePageUU", "findUserDetails onResponse: Error ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDeatilMainGet?>, t: Throwable) {

                Log.e("ProfilePageUU", "findUserDetails onFailure: ${t.message}", t)
            }
        })
    }


    private fun SendOtp(mobileNumberString: String) {
        val json: JSONObject = JSONObject()
        json.put("mobileNumber", mobileNumberString)

        Log.d("LOGPostBrfTRan", "Resp : "+json.toString())
        val call: Call<CommonData?>? =
            MyApplication.getRetrofitClient(this@ProfilePage).create<ApiInterface>(
                ApiInterface::class.java
            ).sendOtp(json.toString())
        call?.enqueue(object : Callback<CommonData?> {
            override fun onResponse(
                call: Call<CommonData?>,
                response: retrofit2.Response<CommonData?>
            ) {
                if (response.isSuccessful()) {
                    val main: CommonData? = response.body()
                    try {

                        if (response.isSuccessful){

                            sign_in_main_ll.visibility = View.GONE
                            verifyLL.visibility = View.GONE
                            otp_main_ll.visibility = View.VISIBLE
                            otpHasSentTo.text = mobileNumberString
                            startCountdownTimer()


                            Log.d("ResponseRawa", "onResponse 1: "+response.raw()+"~"+json.toString())

                            Toast.makeText(this@ProfilePage, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("ResponseRawa", "onResponse 2: "+response.raw()+"~"+json.toString())
                        Toast.makeText(this@ProfilePage, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("ResponseRawa", "onResponse 3: "+response.raw()+"~"+json.toString())
                    Toast.makeText(this@ProfilePage, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            public override fun onFailure(call: Call<CommonData?>?, t: Throwable) {
                Log.d("ResponseRawa", "onResponse: "+t.localizedMessage+"~"+json.toString())
                Toast.makeText(this@ProfilePage, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }

    fun startCountdownTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                resendOtpTimer.text = "00:${String.format("%02d", secondsLeft)}s"
            }

            override fun onFinish() {
                resendOtpTimer.text = ""
                resendOtp.isEnabled = true
                resendOtp.setTextColor(Color.RED)
                resendOtp.text = "Resend Otp"
            }
        }
        countDownTimer?.start()
        resendOtp.isEnabled = false
        resendOtp.setTextColor(Color.GRAY)
    }

    private fun VerifyOtp(
        mobileNumberString: String,
        otp: String,
        bottomSheetDialog: BottomSheetDialog
    ) {
        val json: JSONObject = JSONObject()
        json.put("mobileNumber", mobileNumberString)
        json.put("OTP", otp.toInt())


        Log.d("LOGPostBrfTRan", "Resp : "+json.toString())
        val call: Call<VerifyOtpMainGet?>? =
            MyApplication.getRetrofitClient(this@ProfilePage).create<ApiInterface>(
                ApiInterface::class.java
            ).verifyOtp(json.toString())
        call?.enqueue(object : Callback<VerifyOtpMainGet?> {
            override fun onResponse(
                call: Call<VerifyOtpMainGet?>,
                response: retrofit2.Response<VerifyOtpMainGet?>
            ) {
                if (response.isSuccessful()) {
                    val main: VerifyOtpMainGet? = response.body()
                    try {

                        if (response.isSuccessful){
                            sharedPreferences.edit().putString("mobile_number", mobileNumberString).apply()
                            sharedPreferences.edit().putString("mobile_number", mobileNumberString).commit()

                            logOutLL.visibility=View.VISIBLE
                            sign_in_parent.visibility=View.GONE
                            userName_LL.visibility=View.VISIBLE
                            profile_completion.visibility=View.VISIBLE

                            GetUserDetail(mobileNumberString)
                            bottomSheetDialog.dismiss()
                            Log.d("VerifyOtpA", "onResponse 1: "+response.raw()+"~"+json.toString())
                            Toast.makeText(this@ProfilePage, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("VerifyOtpA", "onResponse 2: "+response.raw()+"~"+json.toString())
                        Toast.makeText(this@ProfilePage, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("VerifyOtpA", "onResponse 3: "+response.raw()+"~"+json.toString())
                    Toast.makeText(this@ProfilePage, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            public override fun onFailure(call: Call<VerifyOtpMainGet?>?, t: Throwable) {
                Log.d("VerifyOtpA", "onResponse: "+t.localizedMessage+"~"+json.toString())
                Toast.makeText(this@ProfilePage, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }


        })
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

    private fun GetUserDetail(mobileNumberString: String) {
        if (!APIClient.isNetworkAvailable(this@ProfilePage)) {
            Toast.makeText(this@ProfilePage, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetUserDetailMain", "Request : $mobileNumberString")
        val call: Call<GetUserDeatilsMainGet?>? =
            MyApplicationPort3009.getRetrofitClient(this@ProfilePage).create<ApiInterface>(
                ApiInterface::class.java
            ).getUserDetail(mobileNumberString)
        call?.enqueue(object : Callback<GetUserDeatilsMainGet?> {
            override fun onResponse(
                call: Call<GetUserDeatilsMainGet?>,
                response: retrofit2.Response<GetUserDeatilsMainGet?>
            ) {
                if (response.isSuccessful) {
                    val main: GetUserDeatilsMainGet? = response.body()
                    main?.let {
                        Log.d("GetUserDetailMain", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                        val fullName: String? = it.getData()?.getPersonalDetail()?.getFullName()
                        val phoneNum: String? = it.getData()?.getPersonalDetail()?.getLogInId()
                        Log.d("GetUserDetailMain", "Full name: $fullName")
                        Log.d("GetUserDetailMain", "phoneNum: $phoneNum")
//                        userLoggedInUserName.text = fullName
                        if (phoneNum.isNullOrEmpty()) {
                            dataValidationIntent="No"

                            logOutLL.visibility = View.GONE
                            sign_in_parent.visibility = View.VISIBLE
                            userName_LL.visibility = View.GONE
                            profile_completion.visibility = View.GONE

                        }else{
                            dataValidationIntent="Yes"
                            val profilePhoto = main.getData()?.getProfilePicture()?.getProfilePhoto()
                            if (profilePhoto != null) {
                                fetchImage(profilePhoto)
                            }
                            Log.d("dataValidationIntent", "onResponse: "+phoneNum+"~~"+dataValidationIntent)


 //                           val storedPhoneNum = sharedPreferences.getString("mobile_number", "")
 //                           val storedFullName = sharedPreferences.getString("user_name", "")

//                            if (phoneNum != storedPhoneNum || fullName != storedFullName) {

                                sharedPreferences.edit().apply {
                                    putString("mobile_number", phoneNum)
                                    putString("user_name", fullName)
                                }.apply()

 //                               userLoggedInMobileNumber.text = phoneNum
 //                               userLoggedInUserName.text = fullName
 //                           }

                            Log.d("storedUserName", "user_name: $fullName")


                            userLoggedInMobileNumber.text = phoneNum
                            userLoggedInUserName.text = fullName
                            Log.d("storedUserName", "user_name: "+fullName)

//                            userLoggedInMobileNumber.text = phoneNum
                            dataValidation = "Yes"
                            logOutLL.visibility = View.VISIBLE
                            sign_in_parent.visibility = View.GONE
                            userName_LL.visibility = View.VISIBLE
                            profile_completion.visibility = View.VISIBLE

                            val profileData = it.getData()
                            val completionPercentage = profileCompletion(profileData)
                            progressBar.setProgressPercentage(completionPercentage.toFloat().toDouble())
                            progressText.text = "$completionPercentage% Complete"
                        }



                    }
                } else {
                    dataValidationIntent="No"

                    logOutLL.visibility = View.GONE
                    sign_in_parent.visibility = View.VISIBLE
                    userName_LL.visibility = View.GONE
                    profile_completion.visibility = View.GONE


                    Log.e("dataValidationIntent", "Failed to get user dataValidationIntent"+dataValidationIntent)
                    Log.d("GetUserDetailMain", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@ProfilePage, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<GetUserDeatilsMainGet?>?, t: Throwable) {
                dataValidationIntent="No"

                logOutLL.visibility = View.GONE
                sign_in_parent.visibility = View.VISIBLE
                userName_LL.visibility = View.GONE
                profile_completion.visibility = View.GONE

                Log.e("GetUserDetailMain", "Failed to get user details", t)
                Log.e("dataValidationIntent", "Failed to get user dataValidationIntent"+dataValidationIntent)
                Toast.makeText(this@ProfilePage, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun transformJsonToMap(json: String): Map<String, Any?>? {
        val gson = Gson()
        val type = object : TypeToken<Map<String, Any?>>() {}.type
        return gson.fromJson(json, type)
    }


    fun profileCompletion(data: GetUserDeatilsMainRes?): Int {
        if (data == null) {
            Log.d("ProfileCompletion", "Data is null")
            return 0
        }

        // Create a map from the provided data class
        val dataMap = mutableMapOf<String, Any?>()

        // Fill the map with data from the data class
        dataMap["personalDetail"] = mapOf(
            "fullName" to data.getPersonalDetail()?.getFullName(),
            "gender" to data.getPersonalDetail()?.getGender(),
            "DOB" to data.getPersonalDetail()?.getDob()
        )
        dataMap["profilePicture"] = mapOf(
            "profilePhoto" to data.getProfilePicture()?.getProfilePhoto()
        )
        dataMap["contactDetail"] = mapOf(
            "mobileNumber" to data.getContactDetail()?.getMobileNumber(),
            "email" to data.getContactDetail()?.getEmail()
        )
        dataMap["addressDetail"] = mapOf(
            "address" to data.getAddressDetail()?.getAddress(),
            "street" to data.getAddressDetail()?.getStreet(),
            "landmark" to data.getAddressDetail()?.getLandmark(),
            "pincode" to data.getAddressDetail()?.getPincode(),
            "city" to data.getAddressDetail()?.getCity(),
            "state" to data.getAddressDetail()?.getState()
        )
        dataMap["identificationDetail"] = mapOf(
            "aadharCard" to data.getIdentificationDetail()?.getAadharCard(),
            "panCard" to data.getIdentificationDetail()?.getPanCard(),
            "passport" to data.getIdentificationDetail()?.getPassport()
        )
        dataMap["advanceDetail"] = mapOf(
            "maritalStatus" to data.getAdvanceDetail()?.getMaritalStatus(),
            "qualification" to data.getAdvanceDetail()?.getQualification(),
            "occupation" to data.getAdvanceDetail()?.getOccupation()
        )

        val sections = dataMap.keys
        val totalSections = sections.size
        var completedSections = 0

        // Define required fields for each section
        fun getRequiredFields(section: String): List<String> {
            return when (section) {
                "personalDetail" -> listOf("fullName", "gender", "DOB")
                "profilePicture" -> listOf("profilePhoto")
                "contactDetail" -> listOf("mobileNumber", "email")
                "addressDetail" -> listOf("address", "street", "landmark", "pincode", "city", "state")
                "identificationDetail" -> listOf("aadharCard", "panCard", "passport")
                "advanceDetail" -> listOf("maritalStatus", "qualification", "occupation")
                else -> emptyList()
            }
        }

        // Check if each section is complete
        sections.forEach { section ->
            val sectionData = dataMap[section] as? Map<String, Any?>
            if (sectionData != null) {
                val requiredFields = getRequiredFields(section)
                val isComplete = requiredFields.all { field ->
                    val isFieldPresent = sectionData[field] != null
                    Log.d("ProfileCompletion", "Section: $section, Field: $field, Present: $isFieldPresent")
                    isFieldPresent
                }

                if (isComplete) {
                    completedSections++
                    Log.d("ProfileCompletion", "Section $section is complete")
                } else {
                    Log.d("ProfileCompletion", "Section $section is not complete")
                }
            } else {
                Log.d("ProfileCompletion", "Section $section data is null")
            }
        }

        val percentageComplete = (completedSections.toDouble() / totalSections.toDouble()) * 100
        val roundedPercentage = percentageComplete.roundToInt()
        Log.d("ProfileCompletion", "Completed Sections: $completedSections, Total Sections: $totalSections, Percentage: $roundedPercentage")
        return roundedPercentage
    }



    private fun checkIpAddressReachability(ipAddress: String) {
        Thread {
            try {
                val inet = InetAddress.getByName(ipAddress)
                val reachable = inet.isReachable(5000)

                if (reachable) {
                    Log.d("NetworkTest", "IP Address $ipAddress is reachable")
                } else {
                    Log.d("NetworkTest", "IP Address $ipAddress is not reachable")
                }
            } catch (e: IOException) {
                Log.e("NetworkTest", "Error checking IP address reachability", e)
            }
        }.start()
    }

    private fun pingServer(url: String) {
        Thread {
            try {
                val address = InetAddress.getByName(url)
                val reachable = address.isReachable(5000)

                Log.d(
                    "MYAPPLICATIONPINMSG",
                    "Ping result for " + url + ": " + (if (reachable) "Reachable" else "Not reachable")
                )
            } catch (e: IOException) {
                Log.e("MYAPPLICATIONPINMSG", "Error pinging server: " + e.message)
            }
        }.start()
    }

}
