package com.bnet.sarvesuraksha.view.User_Basic_Detail

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3009
import com.bnet.sarvesuraksha.api.MyApplicationPort9001
import com.bnet.sarvesuraksha.model_api.AdditionalDetailMainData
import com.bnet.sarvesuraksha.model_api.ContactDetailMainData
import com.bnet.sarvesuraksha.model_api.GetPincodeDetailsMainGet
import com.bnet.sarvesuraksha.model_api.GetUserDeatilsMainGet
import com.bnet.sarvesuraksha.model_api.PostIdentificationDetailMainData
import com.bnet.sarvesuraksha.model_api.PostUserAddressMainData
import com.bnet.sarvesuraksha.model_api.PostUserDeatilsMainData
import com.bnet.sarvesuraksha.model_api.UploadImageMainGet
import com.bnet.sarvesuraksha.utils.PanCardInputFilter
import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.skydoves.powerspinner.PowerSpinnerView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import com.bnet.sarvesuraksha.model_api.PostUserProfilePicMainData
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

class UserBasicDetail : AppCompatActivity() {

    private lateinit var personalLL: LinearLayout
    private lateinit var uploadImage: LinearLayout
    private lateinit var parentGender: LinearLayout
    private lateinit var UploadTxtDescArea: LinearLayout
    private lateinit var previewAreaLL: LinearLayout
    private lateinit var fileName: TextView
    private lateinit var previewImageArea: ImageView
    private lateinit var ImageViewPopUp: ImageView
    private lateinit var uploadOrSaveTxtView: TextView
    private lateinit var goBack: LinearLayout
    private lateinit var addressLL: LinearLayout
    private lateinit var otherLL: LinearLayout
    private lateinit var male_LL: LinearLayout
    private lateinit var female_LL: LinearLayout
    private lateinit var other_LL: LinearLayout
    private lateinit var perosnalDetailsLL: LinearLayout
    private lateinit var datePickerLL: LinearLayout
    private lateinit var addressDetailsLL: LinearLayout
    private lateinit var otherDetailsLL: LinearLayout
    private lateinit var saveLL: LinearLayout
    private lateinit var edit_LL: LinearLayout
    private lateinit var MartialStatusSpinner: PowerSpinnerView
    private lateinit var EducationSpinner: PowerSpinnerView
    private lateinit var OccupationSpinner: PowerSpinnerView
    private lateinit var AnnualIncomeSpinner: PowerSpinnerView
    private lateinit var fullNameTxtInputLayout: TextInputLayout
    private lateinit var dateOfBirthTxtInputLayout: TextInputLayout
    private lateinit var fullNameTxtEditText: TextInputEditText
    private lateinit var dateOfBirthTxtEditTxt: TextInputEditText
    private lateinit var UserphoneNumberTxtET: TextInputEditText
    private lateinit var UserphoneNumberTxtIL: TextInputLayout
    private lateinit var EmailIdtxtinputET: TextInputEditText
    private lateinit var helperTextFullName: TextView
    private lateinit var helperDOB: TextView
    private lateinit var helperTextEmailId: TextView
    private lateinit var saveTextView: TextView

    private lateinit var pincodetxtinputEditText: TextInputEditText
    private lateinit var cityTxtipEditText: TextInputEditText
    private lateinit var stateTxtipEditText: TextInputEditText
    private lateinit var flatNoFloorTxtInputEditText: TextInputEditText
    private lateinit var areaLocalityTxtInputEditText: TextInputEditText
    private lateinit var landMarkTxtInputEditText: TextInputEditText
    private lateinit var aadharCardTIET: TextInputEditText
    private lateinit var panCardTIET: TextInputEditText
    private lateinit var passportNoTIET: TextInputEditText


    private var GenderString: String= "M"
    private var PostValidator: String= "Personal"

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 123
        private const val REQUEST_CODE_PICK_IMAGE = 456
        private const val REQUEST_CODE_TAKE_PHOTO = 1001



    }

    private var selectedImageUri: Uri? = null
    private var imageSelected = false

    private lateinit var dataValidation: String
    private lateinit var storedMobileNumber: String
    private var profilrePhoto: String =""

    private var userIdFromPostUserDetails: String = ""


    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    val MaritalStatus = listOf("single", "married", "divorced", "widow")
    val EducationList = listOf("postGradAndAbove", "Graduate", "diploma", "12thPass", "10thPass", "below10thPass", "illiterate")
    val OccupationList = listOf("Salaried", "Self Employed / Business")
    val AnnualIncomeList = listOf("25 Lac+", "15 Lac to 24.9 Lac","10 Lac to 14.9 Lac","8 Lac to 9.9 Lac","5 Lac to 7.9 Lac","3 Lac to 4.9 Lac","2 Lac to 2.9 Lac","Less than 2 Lac")

    private val annualIncomeMap = mapOf(
        "25 Lac+" to 2500000,
        "15 Lac to 24.9 Lac" to 1500000,
        "10 Lac to 14.9 Lac" to 1000000,
        "8 Lac to 9.9 Lac" to 800000,
        "5 Lac to 7.9 Lac" to 500000,
        "3 Lac to 4.9 Lac" to 300000,
        "2 Lac to 2.9 Lac" to 200000,
        "Less than 2 Lac" to 100000
    )
    private lateinit var sharedPreferences: SharedPreferences
    private var isEditable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_basic_detail)
        sharedPreferences = getSharedPreferences("com.bnet.sarvesuraksha", Context.MODE_PRIVATE)

        dataValidation = intent.getStringExtra("dataValidation") ?: ""

        storedMobileNumber = sharedPreferences.getString("mobile_number", null).toString()

        Log.d("dataValidation", "onCreate: "+dataValidation)
        init()
        setupListeners()




    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun GetUserDetail(mobileNumberString: String) {
        if (!APIClient.isNetworkAvailable(this@UserBasicDetail)) {
            Toast.makeText(this@UserBasicDetail, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetUserDetailMain", "Request : $mobileNumberString")
        val call: Call<GetUserDeatilsMainGet?>? =
            MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
                .getUserDetail(mobileNumberString)
        call?.enqueue(object : Callback<GetUserDeatilsMainGet?> {
            override fun onResponse(
                call: Call<GetUserDeatilsMainGet?>,
                response: retrofit2.Response<GetUserDeatilsMainGet?>
            ) {
                if (response.isSuccessful) {
                    val main: GetUserDeatilsMainGet? = response.body()
                    main?.let {
                        Log.d("GetUserDetailMain", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")



                        val profilePhoto = main.getData()?.getProfilePicture()?.getProfilePhoto()
                        if (profilePhoto != null) {
                            profilrePhoto= main.getData()!!.getProfilePicture()!!.getProfilePhoto().toString()
                            fetchImage(profilePhoto)
                        }
                        val personalDetail = it.getData()?.getPersonalDetail()
                        val contactDetail = it.getData()?.getContactDetail()
                        val addressDetail = it.getData()?.getAddressDetail()
                        val identificationDetail = it.getData()?.getIdentificationDetail()
                        val advanceDetail = it.getData()?.getAdvanceDetail()

                        val fullName = personalDetail?.getFullName()
                        val phoneNum = personalDetail?.getLogInId()
                        val gender = personalDetail?.getGender()
                        val dateOfBirth = personalDetail?.getDob()
                        val emailID = contactDetail?.getEmail()
                        val pinCode = addressDetail?.getPincode()
                        val address = addressDetail?.getAddress()
                        val street = addressDetail?.getStreet()
                        val landmark = addressDetail?.getLandmark()
                        val city = addressDetail?.getCity()
                        val state = addressDetail?.getState()
                        val userID = personalDetail?.getId()
                        val panCard = identificationDetail?.getPanCard()?.getIdNumber()
                        val aadharCard = identificationDetail?.getAadharCard()?.getIdNumber()
                        val passportNo = identificationDetail?.getPassport()?.getIdNumber()
                        val maritalStatus = advanceDetail?.getMaritalStatus()
                        val education = advanceDetail?.getQualification()
                        val occupation = advanceDetail?.getOccupation()?.getEmployeType()
                        val annualIncome = advanceDetail?.getOccupation()?.getAnnualIncome().toString()

                        if (fullName.isNullOrEmpty()) {
                            saveLL.visibility = View.VISIBLE
                            edit_LL.visibility = View.GONE
                        }else{
                            saveLL.visibility = View.GONE
                            edit_LL.visibility = View.VISIBLE
                            setEditable(false)
                            updateSaveButtonState()

                        }
                        userIdFromPostUserDetails = userID.toString()

                        Log.d("USERIDGET", "onResponse: $emailID~~$userIdFromPostUserDetails")

                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                        val formattedDate: String? = dateOfBirth?.let {
                            val date: Date? = inputFormat.parse(it)
                            date?.let { outputFormat.format(it) }
                        }



                        when (gender) {
                            "M" -> selectGenderLayout(male_LL)
                            "Y" -> selectGenderLayout(female_LL)
                            else -> selectGenderLayout(other_LL)
                        }

                        fullName?.let { fullNameTxtEditText.setText(it) }
                        phoneNum?.let { UserphoneNumberTxtET.setText(it) }
                        formattedDate?.let { dateOfBirthTxtEditTxt.setText(it) }
                        emailID?.let { EmailIdtxtinputET.setText(it) }

                        pinCode?.let { pincodetxtinputEditText.setText(it) }
                        address?.let { flatNoFloorTxtInputEditText.setText(it) }
                        street?.let { areaLocalityTxtInputEditText.setText(it) }
                        landmark?.let { landMarkTxtInputEditText.setText(it) }
                        city?.let { cityTxtipEditText.setText(it) }
                        state?.let { stateTxtipEditText.setText(it) }

                        panCard?.let { panCardTIET.setText(it) }
                        aadharCard?.let { aadharCardTIET.setText(it) }
                        passportNo?.let { passportNoTIET.setText(it) }
                        maritalStatus?.let { MartialStatusSpinner.setText(it) }
                        education?.let { EducationSpinner.setText(it) }
                        occupation?.let { OccupationSpinner.setText(it) }
                        annualIncome?.let { AnnualIncomeSpinner.setText(it) }


                    }
                } else {
                    Log.d("GetUserDetailMain", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@UserBasicDetail, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetUserDeatilsMainGet?>?, t: Throwable) {
                Log.e("GetUserDetailMain", "Failed to get user details", t)
                Toast.makeText(this@UserBasicDetail, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun init() {
        personalLL = findViewById(R.id.personalLL)
        uploadImage = findViewById(R.id.uploadImage)
        parentGender = findViewById(R.id.parentGender)
        UploadTxtDescArea = findViewById(R.id.UploadTxtDescArea)
        previewImageArea = findViewById(R.id.previewImageArea)
        previewAreaLL = findViewById(R.id.previewAreaLL)
        fileName = findViewById(R.id.fileName)
        goBack = findViewById(R.id.goBack)
        addressLL = findViewById(R.id.addressLL)
        otherLL = findViewById(R.id.otherLL)
        male_LL = findViewById(R.id.male_LL)
        female_LL = findViewById(R.id.female_LL)
        other_LL = findViewById(R.id.other_LL)
        perosnalDetailsLL = findViewById(R.id.perosnalDetailsLL)
        addressDetailsLL = findViewById(R.id.addressDetailsLL)
        otherDetailsLL = findViewById(R.id.otherDetailsLL)
        saveLL = findViewById(R.id.saveLL)
        edit_LL = findViewById(R.id.edit_LL)
        MartialStatusSpinner = findViewById(R.id.MartialStatusSpinner)
        EducationSpinner = findViewById(R.id.EducationSpinner)
        OccupationSpinner = findViewById(R.id.OccupationSpinner)
        AnnualIncomeSpinner = findViewById(R.id.AnnualIncomeSpinner)
        fullNameTxtInputLayout = findViewById(R.id.FullNameTxtInputLayout)
        fullNameTxtEditText = findViewById(R.id.FullNameTxtEditText)
        helperTextFullName = findViewById(R.id.helperTextFullName)
        dateOfBirthTxtInputLayout = findViewById(R.id.dateOfBirthTxtInputLayout)
        dateOfBirthTxtEditTxt = findViewById(R.id.dateOfBirthTxtEditTxt)
        helperDOB = findViewById(R.id.helperDOB)
        datePickerLL = findViewById(R.id.datePickerLL)
        helperTextEmailId = findViewById(R.id.helperTextEmailId)
        EmailIdtxtinputET = findViewById(R.id.EmailIdtxtinputET)
        UserphoneNumberTxtET = findViewById(R.id.UserphoneNumberTxtET)
        UserphoneNumberTxtIL = findViewById(R.id.UserphoneNumberTxtIL)
        saveTextView = findViewById(R.id.saveTextView)

        pincodetxtinputEditText = findViewById(R.id.pincodetxtinputEditText)
        cityTxtipEditText = findViewById(R.id.cityTxtipEditText)
        stateTxtipEditText = findViewById(R.id.stateTxtipEditText)
        flatNoFloorTxtInputEditText = findViewById(R.id.flatNoFloorTxtInputEditText)
        areaLocalityTxtInputEditText = findViewById(R.id.areaLocalityTxtInputEditText)
        landMarkTxtInputEditText = findViewById(R.id.landMarkTxtInputEditText)

        aadharCardTIET = findViewById(R.id.aadharCardTIET)
        panCardTIET = findViewById(R.id.panCardTIET)
        passportNoTIET = findViewById(R.id.passportNoTIET)

        uploadImage.setOnClickListener {
            ShowUploadImageDialog()

        }

//        setupPanCardInput(panCardTIET)

        if (storedMobileNumber != null) {
            GetUserDetail(storedMobileNumber)
        }

        pincodetxtinputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
//                val pincode = s.toString()
//                if (pincode.length == 6) {
//                    Log.d("pincodeAA", "afterTextChanged: "+pincode)
//                    FindPincodeDetails(pincode)
//                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pincode = s.toString()
                if (pincode.length == 6) {
                    Log.d("pincodeAA", "afterTextChanged: "+pincode)
                    FindPincodeDetails(pincode)
                }
            }
        })

        UserphoneNumberTxtET.isClickable = false
        UserphoneNumberTxtET.isFocusable = false
        UserphoneNumberTxtIL.isClickable = false
        UserphoneNumberTxtIL.isFocusable = false

        UserphoneNumberTxtET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phoneNum = UserphoneNumberTxtET.text.toString().trim()
                if (isValidIndianPhoneNumber(phoneNum)) {
                    updateSaveButtonState()
                    UserphoneNumberTxtET.setTextColor(Color.BLACK)
                } else {
                    UserphoneNumberTxtET.setTextColor(Color.BLACK)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })



        val UserID = sharedPreferences.getString("user_id", null) ?: ""
//        userIdFromPostUserDetails = UserID

        if (!storedMobileNumber.isNullOrEmpty()) {
            UserphoneNumberTxtET.setText(storedMobileNumber)
            Log.e("ProfilePageUserData", "Stored mobile number " + storedMobileNumber+"~~"+UserID+"~~"+userIdFromPostUserDetails)
        } else {
            Log.e("ProfilePageUserData", "Stored mobile number is null or empty")
        }



        setupSpinners()
        setupDatePicker()
        setupFullNameTextWatcher()
        setupEmailTextWatcher()
        setupValidationWatchers()

        edit_LL.setOnClickListener {
            edit_LL.visibility=View.GONE
            saveLL.visibility=View.VISIBLE


            isEditable = true
            setEditable(isEditable)

        }

        saveLL.setOnClickListener {

            val fullName = fullNameTxtEditText.text.toString().trim()
            val dob = dateOfBirthTxtEditTxt.text.toString().trim()
            val email = EmailIdtxtinputET.text.toString().trim()

            if (fullName.isEmpty()) {
                showToast("Please enter full name")
                return@setOnClickListener
            }

            if (dob.isEmpty()) {
                showToast("Please enter date of birth")
                return@setOnClickListener
            }
            if (!isValidEmailFormat(email)) {
                showToast("Please enter a valid Email Id")
                return@setOnClickListener
            }

            PostUserDetails()

            if (PostValidator.equals("Personal")){

            }else if (PostValidator.equals("Address")){



            } else if (PostValidator.equals("Other")){

            }


        }

    }


    private fun PostUserDetails() {
        val json = JSONObject().apply {
            put("logInId", UserphoneNumberTxtET.text.toString().trim())
            put("fullName", fullNameTxtEditText.text.toString().trim())
            put("gender", GenderString)
            val originalDOB = dateOfBirthTxtEditTxt.text.toString()
            val convertedDOB = convertDateFormat(originalDOB)
            put("DOB", convertedDOB)
            put("memberType", "Self")
            if (!userIdFromPostUserDetails.contains("null")) {
                put("personalId", userIdFromPostUserDetails)
            }
        }

        Log.d("PostUserDetails", "Request JSON: $json"+"~~"+userIdFromPostUserDetails)

        val call: Call<PostUserDeatilsMainData?>? = if (userIdFromPostUserDetails.contains("null")) {
            MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
                .postNewUserpersonalDetails(json.toString())
        } else {
            MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
                .postUserpersonalDetails(userIdFromPostUserDetails, json.toString())
        }

        call?.enqueue(object : Callback<PostUserDeatilsMainData?> {
            override fun onResponse(
                call: Call<PostUserDeatilsMainData?>,
                response: Response<PostUserDeatilsMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostUserDeatilsMainData? = response.body()
                    main?.let {
                        val status = it.getStatus()
                        val message = it.getMessage()
                        val personalDetail = it.getData()?.getPersonalDetail()
                        Log.d("PostUserDetails", "Response: ${response.raw()}")
                        personalDetail?.let { detail ->
                            val id = detail.getId()
                            PostContactDetails(id)
                            PostUserAddressDetails(id)
                            PostIdentificationDetail(id)
                            PostAdditionalDetail(id)

                            edit_LL.visibility=View.VISIBLE
                            saveLL.visibility=View.GONE

                            val editor = sharedPreferences.edit()
                            editor.putString("user_id", id)
                            editor.apply()
                            setEditable(false)
                            Log.d("USERID_PO", "User _id: $id~~Pref~~$userIdFromPostUserDetails")
                        }

                        Log.d("PostUserDetails", "onResponse: $message")
                        Toast.makeText(this@UserBasicDetail, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostUserDetails", "onResponse Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@UserBasicDetail, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostUserDeatilsMainData?>?, t: Throwable) {
                Log.d("PostUserDetails", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@UserBasicDetail, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setEditable(editable: Boolean) {
        val editTextList = listOf(
            fullNameTxtEditText, dateOfBirthTxtEditTxt, UserphoneNumberTxtET, EmailIdtxtinputET,
            pincodetxtinputEditText, cityTxtipEditText, stateTxtipEditText,
            flatNoFloorTxtInputEditText, areaLocalityTxtInputEditText, landMarkTxtInputEditText,
            aadharCardTIET, panCardTIET, passportNoTIET
        )


        for (editText in editTextList) {
            editText.isFocusable = editable
            editText.isFocusableInTouchMode = editable
            editText.isClickable = editable
            editText.isEnabled = editable
        }

        val spinnerList = listOf(
            MartialStatusSpinner, EducationSpinner, AnnualIncomeSpinner, OccupationSpinner
        )

        for (spinner in spinnerList) {
            spinner.isEnabled = editable
        }

        val linearLayoutList = listOf(
            uploadImage, parentGender, datePickerLL,male_LL,female_LL,other_LL
        )

        for (linearLayout in linearLayoutList) {
            linearLayout.isEnabled = editable
        }
    }


    private fun isValidIndianPhoneNumber(phone: String): Boolean {
        val pattern = Pattern.compile("^[6789]\\d{9}$")
        return pattern.matcher(phone).matches()
    }

    private fun ShowUploadImageDialog() {
        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.upload_image_alert_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val editImage = alertDialogView.findViewById<LinearLayout>(R.id.editImage)
        val UploadImg = alertDialogView.findViewById<LinearLayout>(R.id.UploadImg)
        val removeImage = alertDialogView.findViewById<LinearLayout>(R.id.removeImage)
        uploadOrSaveTxtView = alertDialogView.findViewById<TextView>(R.id.uploadOrSaveTxtView)
        ImageViewPopUp = alertDialogView.findViewById<ImageView>(R.id.ImageViewPopUp)


        uploadOrSaveTxtView.setOnClickListener {
            if (uploadOrSaveTxtView.text.toString() == "Save" && imageSelected) {
                PostImageAsFile(alertDialog)
            } else {

                if (!imageSelected) {
                    requestPermissions()
                }
            }
        }
        editImage.setOnClickListener {
            requestPermissions()
        }

        closeIcon.setOnClickListener {
            alertDialog.dismiss()
        }


        removeImage.setOnClickListener {

            val staticImageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
            handleImagePicked(staticImageUri)
            PostImageAsFile(alertDialog)
        }

        fetchImagepoup(profilrePhoto.toString()!!,ImageViewPopUp!!)

        alertDialog.show()
    }



    private fun fetchImagepoup(profilePhoto: String?, imageViewPopUp: ImageView) {
        if (profilePhoto == null) {
            Log.e("FetchImage", "Profile photo URL is null")
            return
        }

        val call = MyApplicationPort9001.getRetrofitClient(this).create(ApiInterface::class.java)
            .profilePhoto(profilePhoto)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val inputStream = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageViewPopUp.setImageBitmap(bitmap)
                    Log.d("FetchImage", "Request URL1: ${call.request().url}"+profilePhoto)

                } else {
                    Log.e("FetchImage", "Error: ${response.code()} - ${response.message()}")
                    Log.d("FetchImage", "Request URL2: ${call.request().url}"+profilePhoto)

                    Toast.makeText(this@UserBasicDetail, "Failed to fetch image", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("FetchImage", "Failure: ${t.message}", t)
                Log.d("FetchImage", "Request URL3: ${call.request().url}"+profilePhoto)

                Toast.makeText(this@UserBasicDetail, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchImage(profilePhoto: String?) {
        if (profilePhoto == null) {
            Log.e("FetchImage", "Profile photo URL is null")
            return
        }

        val call = MyApplicationPort9001.getRetrofitClient(this).create(ApiInterface::class.java)
            .profilePhoto(profilePhoto)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val inputStream = response.body()?.byteStream()

                    UploadTxtDescArea.visibility=View.GONE
                    previewAreaLL.visibility=View.VISIBLE
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    previewImageArea.setImageBitmap(bitmap)
                    Log.d("FetchImage", "Request URL1: ${call.request().url}"+profilePhoto)

                } else {
                    Log.e("FetchImage", "Error: ${response.code()} - ${response.message()}")
                    Log.d("FetchImage", "Request URL2: ${call.request().url}"+profilePhoto)

                    Toast.makeText(this@UserBasicDetail, "Failed to fetch image", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("FetchImage", "Failure: ${t.message}", t)
                Log.d("FetchImage", "Request URL3: ${call.request().url}"+profilePhoto)

                Toast.makeText(this@UserBasicDetail, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun requestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                openFilePicker()
//                showImagePickerDialog()
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_PICK_IMAGE)
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> dispatchTakePictureIntent() // Option to take a photo
                1 -> openFilePicker() // Option to choose from gallery
            }
        }
        builder.show()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                handleImagePicked(uri)
            }
        }
    }


    private fun handleImagePicked(uri: Uri) {
        val imageStream = contentResolver.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        previewImageArea.setImageBitmap(selectedImage)
        ImageViewPopUp.setImageBitmap(selectedImage)
        previewAreaLL.visibility = LinearLayout.VISIBLE
        UploadTxtDescArea.visibility = LinearLayout.GONE
        val fileName = getFileName(uri)
        this.fileName.setText(fileName)
        imageSelected = true

        uploadOrSaveTxtView.text = "Save"
        uploadOrSaveTxtView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0) // Remove drawable
    }

    private fun getFileName(uri: Uri): String? {
        var fileName: String? = null
        uri.let {
            val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
            contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                if (cursor.moveToFirst()) {
                    fileName = cursor.getString(columnIndex)
                }
            }
        }
        return fileName
    }


    private fun getPathFromUri(uri: Uri): String {
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        Log.d("PostImageAsFile", "Path from URI: $path")
        return path ?: ""
    }

    private fun PostImageAsFile(alertDialog: AlertDialog) {
        if (selectedImageUri == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            return
        }

        val filePath = if (selectedImageUri.toString().startsWith("android.resource://")) {
            getPathFromDrawable(selectedImageUri!!)
        } else {
            getPathFromUri(selectedImageUri!!)
        }

        val file = File(filePath)

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val mobileNumber = storedMobileNumber
        val mobileNumberBody = mobileNumber.toRequestBody(MultipartBody.FORM)
        val docTypeBody = "profile-photo".toRequestBody(MultipartBody.FORM)
        val docIdBody = "mobileNumber".toRequestBody(MultipartBody.FORM)

        val call: Call<UploadImageMainGet> = MyApplicationPort9001.getRetrofitClient(this).create(ApiInterface::class.java)
            .uploadImage(body, mobileNumberBody, docTypeBody, docIdBody)

        call.enqueue(object : Callback<UploadImageMainGet> {
            override fun onResponse(call: Call<UploadImageMainGet>, response: Response<UploadImageMainGet>) {
                if (response.isSuccessful) {
                    Log.d("UploadImageData", "Success: ${response.body()?.message} ~~~ ${response.body()!!.data!!.docName}")
                    PostUserProfilePic(userIdFromPostUserDetails, response.body()!!.data!!.docName, alertDialog)
                    Toast.makeText(this@UserBasicDetail, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("UploadImageData", "Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@UserBasicDetail, response.body()?.message ?: "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UploadImageMainGet>, t: Throwable) {
                Log.e("UploadImageData", "Failure: ${t.message}", t)
                Toast.makeText(this@UserBasicDetail, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun PostUserProfilePic(id: String?, docName: String?, alertDialog: AlertDialog) {
        val json: JSONObject = JSONObject()

        json.put("logInId", storedMobileNumber)
        json.put("personalId", id)
        json.put("profilePhoto", docName)
        json.put("mobNo", storedMobileNumber)

        Log.d("PostUserProfilePic", "Request JSON: $json")

        val call: Call<PostUserProfilePicMainData?>? = MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
            .postUserProfilePic(id.toString(),json.toString())

        call?.enqueue(object : Callback<PostUserProfilePicMainData?> {
            override fun onResponse(call: Call<PostUserProfilePicMainData?>, response: Response<PostUserProfilePicMainData?>) {
                if (response.isSuccessful) {
                    val PostIdentificationDetailMainData = response.body()

                    alertDialog.dismiss()

                    Log.d("PostUserProfilePic", "onResponse: $PostIdentificationDetailMainData"+"~"+response.raw()+"~~"+json.toString())

                } else {
                    Log.d("PostUserProfilePic", "onResponse Error: ${response.errorBody()?.string()}"+"~~"+response.raw()+"~~"+json.toString())

                }
            }

            override fun onFailure(call: Call<PostUserProfilePicMainData?>, t: Throwable) {
                Log.d("PostAdditionalDetail", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@UserBasicDetail, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getPathFromDrawable(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val tempFile = File(cacheDir, "static_image.jpg")
        tempFile.outputStream().use { inputStream?.copyTo(it) }
        return tempFile.absolutePath
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO)
        }
    }



    fun setupPanCardInput(panCardTIET: EditText) {
        panCardTIET.filters = arrayOf(PanCardInputFilter(), InputFilter.LengthFilter(10))

        panCardTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0

                when {
                    length <= 5 -> panCardTIET.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    length in 6..9 -> panCardTIET.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                    length == 10 -> panCardTIET.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                }

                panCardTIET.setSelection(length)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun setupValidationWatchers() {
        panCardTIET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val panCard = s.toString().trim()
                if (panCard.matches(Regex("[A-Z]{5}[0-9]{4}[A-Z]"))) {
                    panCardTIET.error = null
                } else {
                    panCardTIET.error = "Invalid PAN card format"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        aadharCardTIET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val aadharCard = s.toString().trim()
                if (aadharCard.matches(Regex("\\d{12}"))) {
                    aadharCardTIET.error = null
                } else {
                    aadharCardTIET.error = "Invalid Aadhaar card format"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        passportNoTIET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val passportNo = s.toString().trim()
                if (passportNo.matches(Regex("[A-Z]{1}[0-9]{7}"))) {
                    passportNoTIET.error = null
                } else {
                    passportNoTIET.error = "Invalid passport number format"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupEmailTextWatcher() {
        EmailIdtxtinputET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()

                if (isValidEmailFormat(email)) {
                    helperTextEmailId.visibility = View.GONE
                    updateSaveButtonState()
                } else {
                    helperTextEmailId.visibility = View.VISIBLE
                    helperTextEmailId.text = "Enter a valid Email"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun isValidEmailFormat(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return Regex(emailPattern).matches(email)
    }

    private fun isValidEmailDomain(email: String): Boolean {
        val validDomains = listOf(
            "gmail.com",
            "yahoo.com",
            "hotmail.com",
            "outlook.com",
            "icloud.com",
            "aol.com",
            "protonmail.com",
            "mail.com",
            "zoho.com",
            "yandex.com",
            "live.com",
            "gmx.com",
            "inbox.com",
        )


        val domain = email.substringAfterLast('@')
        return validDomains.contains(domain)
    }

    private fun setupListeners() {
        goBack.setOnClickListener {
            onBackPressed()
        }

        personalLL.setOnClickListener {
            PostValidator="Personal"
            selectLayout(personalLL, perosnalDetailsLL)
        }

        addressLL.setOnClickListener {
            PostValidator="Address"
            selectLayout(addressLL, addressDetailsLL)
        }

        otherLL.setOnClickListener {
            PostValidator="Other"
            selectLayout(otherLL, otherDetailsLL)
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


    }

    private fun setupSpinners() {
        MartialStatusSpinner.setItems(MaritalStatus)
        EducationSpinner.setItems(EducationList)
        OccupationSpinner.setItems(OccupationList)
        AnnualIncomeSpinner.setItems(AnnualIncomeList)

        MartialStatusSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
            showToast("Selected Marital Status: $item")
        }

        EducationSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
            showToast("Selected Education: $item")
        }

        OccupationSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
            showToast("Selected Occupation: $item")
        }

        AnnualIncomeSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
            showToast("Selected Annual Income: $item")
        }
    }

    private fun setupDatePicker() {
        datePickerLL.setOnClickListener {
            showDatePicker()
        }

        dateOfBirthTxtEditTxt.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace("[^\\d]".toRegex(), "")
                    val length = userInput.length

                    if (length <= 8) {
                        val sb = StringBuilder()
                        var index = 0
                        for (i in userInput.indices) {
                            sb.append(userInput[i])
                            if ((i == 1 || i == 3) && i != length - 1) {
                                sb.append("-")
                            }
                        }
                        current = sb.toString()
                        dateOfBirthTxtEditTxt.setText(current)
                        dateOfBirthTxtEditTxt.setSelection(current.length)
                        validateDateOfBirth()
                        updateSaveButtonState()
                    }
                }
            }
        })
    }



    private fun validateDateOfBirth() {
        val dobString = dateOfBirthTxtEditTxt.text.toString()
        if (dobString.length == 10) {
            try {
                val dob = dateFormat.parse(dobString)
                if (dob != null && isAgeValid(dob)) {
                    helperDOB.visibility = View.GONE
                } else {
                    helperDOB.visibility = View.VISIBLE
                    helperDOB.text = "You must be 18 years or older"
                }
            } catch (e: Exception) {
                helperDOB.visibility = View.VISIBLE
                helperDOB.text = "Invalid date format"
            }
        } else {
            helperDOB.visibility = View.VISIBLE
            helperDOB.text = "Enter valid date"
        }
    }

    private fun isAgeValid(dateOfBirth: Date): Boolean {
        val dobCalendar = Calendar.getInstance()
        dobCalendar.time = dateOfBirth

        val eighteenYearsAgo = Calendar.getInstance()
        eighteenYearsAgo.add(Calendar.YEAR, -18)

        return dobCalendar.before(eighteenYearsAgo)
    }

    private fun setupFullNameTextWatcher() {
        fullNameTxtEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    helperTextFullName.visibility = View.VISIBLE
                    helperTextFullName.text = "Full Name is required"
                } else {
                    if (s.length < 5) {
                        helperTextFullName.visibility = View.VISIBLE
                        helperTextFullName.text = "Full Name must have at least 5 characters"
                    } else {
                        updateSaveButtonState()
                        helperTextFullName.visibility = View.GONE
                        helperTextFullName.text = ""
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateSaveButtonState() {
        val fullName = fullNameTxtEditText.text.toString().trim()
        val dob = dateOfBirthTxtEditTxt.text.toString().trim()
        val email = EmailIdtxtinputET.text.toString().trim()
        val mobileNum = UserphoneNumberTxtET.text.toString().trim()

        if (fullName.isEmpty() || dob.isEmpty() || email.isEmpty()|| mobileNum.isEmpty()) {
            saveLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            saveTextView.setTextColor(resources.getColor(R.color.verify_def_grey))
        } else {
            saveLL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            saveTextView.setTextColor(Color.BLACK)
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val selectedDate = calendar.time

                if (isAgeValid(selectedDate)) {
                    dateOfBirthTxtEditTxt.setText(dateFormat.format(selectedDate))
                    helperDOB.visibility = View.GONE
                } else {
                    dateOfBirthTxtEditTxt.setText("")
                    helperDOB.visibility = View.VISIBLE
                    helperDOB.text = "You must be 18 years or older"
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

    private fun selectLayout(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {
        personalLL.background = null
        addressLL.background = null
        otherLL.background = null

        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg)

        perosnalDetailsLL.visibility = if (detailsLayout == perosnalDetailsLL) View.VISIBLE else View.GONE
        addressDetailsLL.visibility = if (detailsLayout == addressDetailsLL) View.VISIBLE else View.GONE
        otherDetailsLL.visibility = if (detailsLayout == otherDetailsLL) View.VISIBLE else View.GONE
    }

    private fun selectGenderLayout(selectedLayout: LinearLayout) {
        male_LL.setBackgroundResource(R.drawable.gender_default)
        female_LL.setBackgroundResource(R.drawable.gender_default)
        other_LL.setBackgroundResource(R.drawable.gender_default)

        selectedLayout.setBackgroundResource(R.drawable.gender_selected)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@UserBasicDetail, ProfilePage::class.java))
        finish()
    }



//
//    private fun PostUserDetails() {
//
//        val json: JSONObject = JSONObject()
//        json.put("logInId", UserphoneNumberTxtET.text.toString().trim())
//        json.put("fullName", fullNameTxtEditText.text.toString().trim())
//        json.put("gender", GenderString)
//        val originalDOB = dateOfBirthTxtEditTxt.text.toString()
//        val convertedDOB = convertDateFormat(originalDOB)
//        json.put("DOB", convertedDOB)
//        json.put("memberType", "Self")
//        json.put("personalId", userIdFromPostUserDetails)
//
//        Log.d("PostUserDetails", "Request JSON: $json")
//
//        val call: Call<PostUserDeatilsMainData?>? =
//            MyApplicationPort3009.getRetrofitClient().create(ApiInterface::class.java)
//                .postUserpersonalDetails(userIdFromPostUserDetails,json.toString())
//
//        call?.enqueue(object : Callback<PostUserDeatilsMainData?> {
//            override fun onResponse(
//                call: Call<PostUserDeatilsMainData?>,
//                response: Response<PostUserDeatilsMainData?>
//            ) {
//                if (response.isSuccessful) {
//                    val main: PostUserDeatilsMainData? = response.body()
//                    main?.let {
//                        val status = it.getStatus()
//                        val message = it.getMessage()
//                        val personalDetail = it.getData()?.getPersonalDetail()
//                    Log.d("PostUserDetails", "Request JSON: $json"+"~"+response.raw())
//                        personalDetail?.let { detail ->
//                            val id = detail.getId()
//                            PostContactDetails(id)
//
//                            PostUserAddressDetails(id)
//                            PostIdentificationDetail(id)
//                            PostAdditionalDetail(id)
//                            val editor = sharedPreferences.edit()
//                            editor.putString("user_id", id)
//                            editor.apply()
//
////                            userIdFromPostUserDetails = id ?: ""
//                            Log.d("USERID_PO", "User _id: $id"+"~~Pref~~"+userIdFromPostUserDetails)
//
//
//
//                        }
//
//                        Log.d("PostUserDetails", "onResponse: $message")
//                        Toast.makeText(this@UserBasicDetail, message, Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Log.d("PostUserDetails", "onResponse Error: ${response.errorBody()?.string()}")
//                    Toast.makeText(this@UserBasicDetail, response.message(), Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<PostUserDeatilsMainData?>?, t: Throwable) {
//                Log.d("PostUserDetails", "onFailure: ${t.localizedMessage}")
//                Toast.makeText(this@UserBasicDetail, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//

    private fun showCommonAlertDialog() {
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.common_alert_dialog_apicall, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val AlertTextMsg = alertDialogView.findViewById<TextView>(R.id.AlertTextMsg)

        AlertTextMsg.text="Saved Sucessfully"

        closeIcon.setOnClickListener {
            alertDialog.dismiss()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.dismiss()
        }, 2000)


        alertDialog.show()
    }


    private fun PostUserAddressDetails(id: String?) {
        val json: JSONObject = JSONObject()
        json.put("personalId", id.toString())
        json.put("address", flatNoFloorTxtInputEditText.text.toString().trim())
        json.put("street", areaLocalityTxtInputEditText.text.toString().trim())
        json.put("landmark", landMarkTxtInputEditText.text.toString().trim())
        json.put("pincode", pincodetxtinputEditText.text.toString().trim())
        json.put("city", cityTxtipEditText.text.toString().trim())
        json.put("state", stateTxtipEditText.text.toString().trim())


        Log.d("PostAddressDetails", "Request JSON: $json")

        val call: Call<PostUserAddressMainData?>? =
            MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
                .postUserAddressDetail(id.toString(),json.toString())

        call?.enqueue(object : Callback<PostUserAddressMainData?> {
            override fun onResponse(
                call: Call<PostUserAddressMainData?>,
                response: Response<PostUserAddressMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostUserAddressMainData? = response.body()
                    main?.let {
                        val status = it.getStatus()
                        val message = it.getMessage()
                        val personalDetail = it.getData()?.getAddressDetail()

                        personalDetail?.let { detail ->
                            val id = detail.getId()

                            Log.d("PostUserAddressDetails", "User _id: $id"+"~~Pref~~"+"ds"+response.raw()+"~"+json.toString())



                        }

                        Log.d("PostAddressDetails", "onResponse: $message")
                        Toast.makeText(this@UserBasicDetail, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostAddressDetails", "onResponse Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@UserBasicDetail, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostUserAddressMainData?>?, t: Throwable) {
                Log.d("PostAddressDetails", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@UserBasicDetail, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun PostIdentificationDetail(id: String?) {
        val json: JSONObject = JSONObject()
        json.put("personalId", id)
        json.put("panCard", JSONObject().apply {
            put("idNumber", panCardTIET.text.toString().trim())
            put("status", true)
        })
        json.put("aadharCard", JSONObject().apply {
            put("idNumber", aadharCardTIET.text.toString().trim())
            put("status", true)
        })
        json.put("passport", JSONObject().apply {
            put("idNumber", passportNoTIET.text.toString().trim())
            put("status", true)
        })

        Log.d("PostIdentinDetail", "Request JSON: $json")


        val call: Call<PostIdentificationDetailMainData?>? = MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
            .postIdentificationDetail(id.toString(),json.toString())

        call?.enqueue(object : Callback<PostIdentificationDetailMainData?> {
            override fun onResponse(call: Call<PostIdentificationDetailMainData?>, response: Response<PostIdentificationDetailMainData?>) {
                if (response.isSuccessful) {
                    val UserIdentificationDetail = response.body()

                    Log.d("PostIdentinDetail", "onResponse: $UserIdentificationDetail"+"~"+response.raw()+"~~"+json.toString())
//                    Toast.makeText(this@UserBasicDetail, "Identification details posted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("PostIdentinDetail", "onResponse Error: ${response.errorBody()?.string()}"+"~~"+response.raw()+"~~"+json.toString())
//                    Toast.makeText(this@UserBasicDetail, "Failed to post identification details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostIdentificationDetailMainData?>, t: Throwable) {
                Log.d("PostIdentinDetail", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@UserBasicDetail, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun PostAdditionalDetail(id: String?) {

        val json: JSONObject = JSONObject()
        json.put("personalId", id)
        json.put("maritalStatus", MartialStatusSpinner.text.toString().trim().toLowerCase())
        json.put("occupation", JSONObject().apply {
            put("employeType", OccupationSpinner.text.toString().toLowerCase())
            put("annualIncome", AnnualIncomeSpinner.selectedIndex.toString().toInt() * 100000)
//            put("annualIncome", annualIncomeMap[AnnualIncomeSpinner.selectedIndex.toString()] ?: 0)
        })

        json.put("qualification", EducationSpinner.text.toString().toLowerCase())

        Log.d("PostAdditionalDetail", "Request JSON: $json")

        val call: Call<AdditionalDetailMainData?>? = MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
            .postAdditionalDetail(id.toString(),json.toString())

        call?.enqueue(object : Callback<AdditionalDetailMainData?> {
            override fun onResponse(call: Call<AdditionalDetailMainData?>, response: Response<AdditionalDetailMainData?>) {
                if (response.isSuccessful) {
                    val PostIdentificationDetailMainData = response.body()
                    showCommonAlertDialog()
                    Log.d("PostAdditionalDetail", "onResponse: $PostIdentificationDetailMainData"+"~"+response.raw()+"~~"+json.toString())
//                    Toast.makeText(this@UserBasicDetail, "Additional details posted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("PostAdditionalDetail", "onResponse Error: ${response.errorBody()?.string()}"+"~~"+response.raw()+"~~"+json.toString())
//                    Toast.makeText(this@UserBasicDetail, "Additional details Not posted Successfully", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AdditionalDetailMainData?>, t: Throwable) {
                Log.d("PostAdditionalDetail", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@UserBasicDetail, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAnnualIncomeValue(selectedItem: String): Int {
        return when (selectedItem) {
            "25 Lac+" -> 2500000
            "15 Lac to 24.9 Lac" -> 1500000
            "10 Lac to 14.9 Lac" -> 1000000
            "8 Lac to 9.9 Lac" -> 800000
            "5 Lac to 7.9 Lac" -> 500000
            "3 Lac to 4.9 Lac" -> 300000
            "2 Lac to 2.9 Lac" -> 200000
            "Less than 2 Lac" -> 100000
            else -> 0
        }
    }


    private fun FindPincodeDetails(pincode: String) {
        val call: Call<GetPincodeDetailsMainGet?>? = MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java).findPincodedetails(pincode)
        Log.d("FindPincodeDetails", "Find Pincode Details URL: ${call?.request()?.url}")
        call?.enqueue(object : Callback<GetPincodeDetailsMainGet?> {
            override fun onResponse(call: Call<GetPincodeDetailsMainGet?>, response: Response<GetPincodeDetailsMainGet?>) {
                if (response.isSuccessful) {
                    val userDetails: GetPincodeDetailsMainGet? = response.body()
                    Log.d("FindPincodeDetails", "Find Pincode Details onResponse: Success")
                    if (userDetails != null) {

                        val pincodeDetails = response.body()!!.data
//                        cityTxtipEditText.setText(pincodeDetails?.pOName!!.get(0))
                        stateTxtipEditText.setText(pincodeDetails?.state)
                        cityTxtipEditText.setText(pincodeDetails?.district)

                    } else {

                    }
                } else {

                    Log.e("FindPincodeDetails", "Find Pincode Details onResponse: Error ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetPincodeDetailsMainGet?>, t: Throwable) {

                Log.e("FindPincodeDetails", "Find Pincode Details onFailure: ${t.message}", t)
            }
        })
    }

    private fun PostContactDetails(id: String?) {
        val json: JSONObject = JSONObject()
        json.put("personalId", id)
        json.put("mobileNumber", UserphoneNumberTxtET.text.toString().trim())
        json.put("email", EmailIdtxtinputET.text.toString().trim())


        Log.d("PostContactDetails", "Resp : "+json.toString())
        val call: Call<ContactDetailMainData?> =
            MyApplicationPort3009.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).postContactDetail(id.toString(),json.toString())

        call?.enqueue(object : Callback<ContactDetailMainData?> {
            override fun onResponse(
                call: Call<ContactDetailMainData?>,
                response: retrofit2.Response<ContactDetailMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: ContactDetailMainData? = response.body()
                    try {
                        if (main != null) {

                            Log.d("PostContactDetails", "onResponse 1: "+response.raw()+"~"+json.toString())
//                            Toast.makeText(this@UserBasicDetail, main.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostContactDetails", "onResponse 2: "+response.raw()+"~"+json.toString())
                        Toast.makeText(this@UserBasicDetail, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostContactDetails", "onResponse 3: "+response.raw()+"~"+json.toString())
                    Toast.makeText(this@UserBasicDetail, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ContactDetailMainData?>?, t: Throwable) {
                Log.d("PostContactDetails", "onFailure: "+t.localizedMessage+"~"+json.toString())
                Toast.makeText(this@UserBasicDetail, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }


}
