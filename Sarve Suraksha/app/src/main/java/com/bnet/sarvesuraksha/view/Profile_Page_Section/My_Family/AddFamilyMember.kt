package com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Family

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
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3009
import com.bnet.sarvesuraksha.api.MyApplicationPort9001
import com.bnet.sarvesuraksha.model_api.CommonData
import com.bnet.sarvesuraksha.model_api.ContactDetailMainData
import com.bnet.sarvesuraksha.model_api.GetAddedFamilyMemberData
import com.bnet.sarvesuraksha.model_api.GetPincodeDetailsMainGet
import com.bnet.sarvesuraksha.model_api.GetUserDeatilsMainGet
import com.bnet.sarvesuraksha.model_api.PostUserAddressMainData
import com.bnet.sarvesuraksha.model_api.PostUserDeatilsMainData
import com.bnet.sarvesuraksha.model_api.PostUserProfilePicMainData
import com.bnet.sarvesuraksha.model_api.UploadImageMainGet

import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.skydoves.powerspinner.PowerSpinnerView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

class AddFamilyMember : AppCompatActivity() {

    private lateinit var goBack: LinearLayout
    private lateinit var removeDetails: LinearLayout
    private lateinit var save_LL: LinearLayout
    private lateinit var saveTextView: TextView

    private lateinit var dateOfBirthTxtInputLayout: TextInputLayout
    private lateinit var male_LL: LinearLayout
    private lateinit var female_LL: LinearLayout
    private lateinit var other_LL: LinearLayout
    private lateinit var personalLL: LinearLayout
    private lateinit var addressLL: LinearLayout
    private lateinit var personalDetailsChild: LinearLayout
    private lateinit var addressDetailsChild: LinearLayout
    private lateinit var RetalionSpinner: PowerSpinnerView
    val RelationList = listOf("Father", "Mother","Father in Law","Mother in Law","Spouse","Brother","Sister")
    private var GenderString: String= "M"
    private lateinit var helperDOB: TextView
    private lateinit var ToolBarText: TextView
    private lateinit var helperTextEmailId: TextView

    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private lateinit var datePickerLL: LinearLayout
    private lateinit var edit_LL: LinearLayout
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var pincodetxtinputEditText: TextInputEditText
    private lateinit var cityTxtipEditText: TextInputEditText
    private lateinit var stateTxtipEditText: TextInputEditText
    private lateinit var flatNoFloorTxtInputEditText: TextInputEditText
    private lateinit var areaLocalityTxtInputEditText: TextInputEditText
    private lateinit var landMarkTxtInputEditText: TextInputEditText

    private lateinit var FullNameTxtEditText: TextInputEditText
    private lateinit var UserphoneNumberTxtET: TextInputEditText
    private lateinit var dateOfBirthTxtEditTxt: TextInputEditText
    private lateinit var EmailIdtxtinputET: TextInputEditText
    private lateinit var checkBoxSameAddress: CheckBox
    private lateinit var uploadImage: LinearLayout
    private lateinit var parentGender: LinearLayout



    private var CheckedString: String = "N"
    private var popupWindow: PopupWindow? = null


    private lateinit var UploadTxtDescArea: LinearLayout
    private lateinit var previewAreaLL: LinearLayout
    private lateinit var fileName: TextView
    private lateinit var previewImageArea: ImageView
    private lateinit var ImageViewPopUp: ImageView
    private lateinit var uploadOrSaveTxtView: TextView
    private  var profilrePhoto: String =""
    private var selectedImageUri: Uri? = null
    private var imageSelected = false

    private var userIdFromPostUserDetails: String = ""
    private lateinit var storedMobileNumber: String
    private var isEditable = false
    companion object {
        var send: String? = null
        var fullName: String? = null
        var profilePic: String? = null
        var UserPersonalId: String? = null
        var relation: String? = null
        private const val REQUEST_CODE_PERMISSIONS = 123
        private const val REQUEST_CODE_PICK_IMAGE = 456
        private const val REQUEST_CODE_TAKE_PHOTO = 1001


    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_family_member)

        sharedPreferences = getSharedPreferences("com.bnet.sarvesuraksha", Context.MODE_PRIVATE)

        storedMobileNumber = sharedPreferences.getString("mobile_number", null).toString()
        val UserID = sharedPreferences.getString("user_id", null) ?: ""

        UploadTxtDescArea = findViewById(R.id.UploadTxtDescArea)
        uploadImage = findViewById(R.id.uploadImage)
        parentGender = findViewById(R.id.parentGender)
        previewImageArea = findViewById(R.id.previewImageArea)
        previewAreaLL = findViewById(R.id.previewAreaLL)
        fileName = findViewById(R.id.fileName)
        helperDOB = findViewById(R.id.helperDOB)
        ToolBarText = findViewById(R.id.ToolBarText)
        edit_LL = findViewById(R.id.edit_LL)
        EmailIdtxtinputET = findViewById(R.id.EmailIdtxtinputET)
        helperTextEmailId = findViewById(R.id.helperTextEmailId)
        datePickerLL = findViewById(R.id.datePickerLL)
        goBack = findViewById(R.id.goBack)
        removeDetails = findViewById(R.id.removeDetails)
        FullNameTxtEditText = findViewById(R.id.FullNameTxtEditText)
        UserphoneNumberTxtET = findViewById(R.id.UserphoneNumberTxtET)
        dateOfBirthTxtEditTxt = findViewById(R.id.dateOfBirthTxtEditTxt)
        dateOfBirthTxtInputLayout = findViewById(R.id.dateOfBirthTxtInputLayout)
        save_LL = findViewById(R.id.save_LL)
        saveTextView = findViewById(R.id.saveTextView)
        male_LL = findViewById(R.id.male_LL)
        female_LL = findViewById(R.id.female_LL)
        other_LL = findViewById(R.id.other_LL)
        RetalionSpinner = findViewById(R.id.RetalionSpinner)
        personalLL = findViewById(R.id.personalLL)
        addressLL = findViewById(R.id.addressLL)
        personalDetailsChild = findViewById(R.id.personalDetailsChild)
        addressDetailsChild = findViewById(R.id.addressDetailsChild)
        checkBoxSameAddress = findViewById(R.id.checkBoxSameAddress)
        pincodetxtinputEditText = findViewById(R.id.pincodetxtinputEditText)
        cityTxtipEditText = findViewById(R.id.cityTxtipEditText)
        stateTxtipEditText = findViewById(R.id.stateTxtipEditText)
        flatNoFloorTxtInputEditText = findViewById(R.id.flatNoFloorTxtInputEditText)
        areaLocalityTxtInputEditText = findViewById(R.id.areaLocalityTxtInputEditText)
        landMarkTxtInputEditText = findViewById(R.id.landMarkTxtInputEditText)


        uploadImage.setOnClickListener {
            ShowUploadImageDialog()
        }

        checkBoxSameAddress.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                CheckedString="Y"
                GetUserDetail(storedMobileNumber.toString())
                setAddressFields(
                    pincodetxtinputEditText.text.toString(),
                    flatNoFloorTxtInputEditText.text.toString(),
                    areaLocalityTxtInputEditText.text.toString(),
                    landMarkTxtInputEditText.text.toString(),
                    cityTxtipEditText.text.toString(),
                    stateTxtipEditText.text.toString()
                )
            } else {
                CheckedString="N"
                clearAddressFields()
            }
        }

        GetUserDetail(storedMobileNumber.toString())

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

        personalLL.setOnClickListener { selectLayout(personalLL, personalDetailsChild) }
        addressLL.setOnClickListener { selectLayout(addressLL, addressDetailsChild) }

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

        RetalionSpinner.setItems(RelationList)

        FullNameTxtEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 4) {
                    updateSaveButtonState()
//                    save_LL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
//                    saveTextView.setTextColor(Color.BLACK)
                }
//                else {
//                    save_LL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
//                    saveTextView.setTextColor(resources.getColor(R.color.verify_def_grey))
//                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        setupDatePicker()
        setupEmailTextWatcher()




        RetalionSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
            showToast("Selected Marital Status: $item")
        }

        goBack.setOnClickListener {
            startActivity(Intent(this@AddFamilyMember, MyFamilyProfilePage::class.java))
        }



        val intent = intent
        send = intent.getStringExtra("send")
        if (send != null && send == "edit") {
            edit_LL.visibility=View.VISIBLE
            removeDetails.visibility=View.VISIBLE
            save_LL.visibility=View.GONE

            ToolBarText.text="Member Details"
            fullName = intent.getStringExtra("fullName")
            profilePic = intent.getStringExtra("profilePic")
            relation = intent.getStringExtra("relation")
            UserPersonalId = intent.getStringExtra("UserPersonalId")
            setEditable(false)
            GetUserMemberDetail(UserPersonalId.toString())
            edit_LL.setOnClickListener {
                edit_LL.visibility=View.GONE
                removeDetails.visibility=View.GONE
                save_LL.visibility=View.VISIBLE
                ToolBarText.text="Edit Member Details"

                isEditable = true
                setEditable(isEditable)


            }

            removeDetails.setOnClickListener {
                showRemoveDetailsPopup(removeDetails)

            }



        }


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


        save_LL.setOnClickListener {

            val fullName = FullNameTxtEditText.text.toString().trim()
            val dob = dateOfBirthTxtEditTxt.text.toString().trim()
            val email = EmailIdtxtinputET.text.toString().trim()
            val phoneNum = UserphoneNumberTxtET.text.toString().trim()

            if (fullName.isEmpty()) {
                showToast("Please enter full name")
                return@setOnClickListener
            }

            if (dob.isEmpty()) {
                showToast("Please enter date of birth")
                return@setOnClickListener
            }

            if (!isValidIndianPhoneNumber(phoneNum)) {
                showToast("Please enter a valid phone number starting with 6, 7, 8, or 9 and 10 digits long")
                return@setOnClickListener
            }

            if (!isValidEmailFormat(email)) {
                showToast("Please enter a valid Email Id")
                return@setOnClickListener
            }


            PostUserDetails(UserID,storedMobileNumber, UserPersonalId)


        }


    }

    private fun isValidIndianPhoneNumber(phone: String): Boolean {
        val pattern = Pattern.compile("^[6789]\\d{9}$")
        return pattern.matcher(phone).matches()
    }
//    560FE5

    private fun updateSaveButtonState() {
        val fullName = FullNameTxtEditText.text.toString().trim()
        val dob = dateOfBirthTxtEditTxt.text.toString().trim()
        val email = EmailIdtxtinputET.text.toString().trim()
        val mobileNum = UserphoneNumberTxtET.text.toString().trim()

        if (fullName.isEmpty() || dob.isEmpty() || email.isEmpty()|| mobileNum.isEmpty()) {
            save_LL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            saveTextView.setTextColor(resources.getColor(R.color.verify_def_grey))
        } else {
            save_LL.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            saveTextView.setTextColor(Color.BLACK)
        }
    }

    private fun showRemoveDetailsPopup(anchorView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.remove_alert_dialog, null)

        val removeDetailsCardView = view.findViewById<CardView>(R.id.removeDetailsCardView)



        removeDetailsCardView.setOnClickListener {
            showCommonAlertDialog(anchorView, inflater)
            popupWindow?.dismiss()


        }


        popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow?.elevation = 10.0f


        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1] + anchorView.height


        popupWindow?.showAtLocation(anchorView, Gravity.START or Gravity.TOP, x, y)
    }

    private fun showCommonAlertDialog(anchorView: View, inflater: LayoutInflater) {
        val alertDialogView = inflater.inflate(R.layout.common_alert_dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val removeLL = alertDialogView.findViewById<LinearLayout>(R.id.removeLL)
        val cancelLL = alertDialogView.findViewById<LinearLayout>(R.id.cancel_ll)
        val signInText = alertDialogView.findViewById<TextView>(R.id.signInText)

        signInText.setText("Remove "+FullNameTxtEditText.text.toString()+" from your family?")

        closeIcon.setOnClickListener {
            alertDialog.dismiss()
        }

        removeLL.setOnClickListener {
            DeleteMemberdetail(UserPersonalId.toString(),alertDialog)

        }

        cancelLL.setOnClickListener {

            alertDialog.dismiss()
        }

        alertDialog.show()
    }


    private fun FindPincodeDetails(pincode: String) {
        val call: Call<GetPincodeDetailsMainGet?>? = MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create(ApiInterface::class.java).findPincodedetails(pincode)
        Log.d("FindPincodeDetails", "Find Pincode Details URL: ${call?.request()?.url}")
        call?.enqueue(object : Callback<GetPincodeDetailsMainGet?> {
            override fun onResponse(call: Call<GetPincodeDetailsMainGet?>, response: Response<GetPincodeDetailsMainGet?>) {
                if (response.isSuccessful) {
                    val userDetails: GetPincodeDetailsMainGet? = response.body()
                    Log.d("FindPincodeDetails", "Find Pincode Details onResponse: Success")
                    if (userDetails != null) {

                        val pincodeDetails = response.body()!!.data
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
    private fun selectLayout(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {

        personalLL.background = null
        addressLL.background = null


        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg)


        personalDetailsChild.visibility = if (detailsLayout == personalDetailsChild) LinearLayout.VISIBLE else LinearLayout.GONE
        addressDetailsChild.visibility = if (detailsLayout == addressDetailsChild) LinearLayout.VISIBLE else LinearLayout.GONE
    }

    private fun selectGenderLayout(selectedLayout: LinearLayout) {

        male_LL.setBackgroundResource(R.drawable.gender_default)
        female_LL.setBackgroundResource(R.drawable.gender_default)
        other_LL.setBackgroundResource(R.drawable.gender_default)


        selectedLayout.setBackgroundResource(R.drawable.gender_selected)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@AddFamilyMember, MyFamilyProfilePage::class.java))
        finish()
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

    private fun setupDatePicker() {
        dateOfBirthTxtEditTxt.setOnClickListener {
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


    private fun PostUserDetails(
        UserID: String,
        storedMobileNumber: String?,
        UserPersonalId: String?
    ) {
        val json: JSONObject = JSONObject()
        json.put("logInId", storedMobileNumber)
        json.put("fullName", FullNameTxtEditText.text.toString().trim())
        json.put("gender", GenderString)
        val originalDOB = dateOfBirthTxtEditTxt.text.toString()
        val convertedDOB = convertDateFormat(originalDOB)
        json.put("DOB", convertedDOB)
        json.put("memberType", RetalionSpinner.text.toString().toLowerCase())
        json.put("personalId", UserPersonalId)

        Log.d("PostUserDetails", "Request JSON: $json"+"~"+ UserPersonalId)



        val call: Call<PostUserDeatilsMainData?>? = if (UserPersonalId.toString().contains("null")) {
            MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create(ApiInterface::class.java)
                .postNewUserpersonalDetails(json.toString())
        } else {
            MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create(ApiInterface::class.java)
                .postUserpersonalDetails(UserPersonalId.toString(), json.toString())
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
                        Log.d("PostUserDetails", "Request JSON: $json"+"~"+response.raw())
                        personalDetail?.let { detail ->
                            val id = detail.getId()
                            if (isValidEmailFormat(EmailIdtxtinputET.text.toString().trim())) {
                                PostContactDetails(id)
                            }
//
                            PostUserAddressDetails(id)

                            setEditable(false)
//
//                            val editor = sharedPreferences.edit()
//                            editor.putString("user_id", id)
//                            editor.apply()
//
////                            userIdFromPostUserDetails = id ?: ""
                            Log.d("USERID_POST", "User _id: $id")



                        }

                        Log.d("PostUserDetails", "onResponse: $message")
                        Toast.makeText(this@AddFamilyMember, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostUserDetails", "onResponse Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@AddFamilyMember, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostUserDeatilsMainData?>?, t: Throwable) {
                Log.d("PostUserDetails", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@AddFamilyMember, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun PostContactDetails(id: String?) {

        val email = EmailIdtxtinputET.text.toString().trim()

        if (!isValidEmailFormat(email)) {
            Toast.makeText(this, "Invalid Email address. Please correct it before saving.", Toast.LENGTH_SHORT).show()
            return
        }

        val json = JSONObject().apply {
            put("personalId", id)
            put("mobileNumber", UserphoneNumberTxtET.text.toString().trim())
            put("email", email)
        }

//        val json: JSONObject = JSONObject()
//        json.put("personalId", id)
//        json.put("mobileNumber", UserphoneNumberTxtET.text.toString().trim())
//        json.put("email", EmailIdtxtinputET.text.toString().trim())


        Log.d("PostContactDetails", "Resp : "+json.toString())
        val call: Call<ContactDetailMainData?> =
            MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create<ApiInterface>(
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
                            Toast.makeText(this@AddFamilyMember, main.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostContactDetails", "onResponse 2: "+response.raw()+"~"+json.toString())
                        Toast.makeText(this@AddFamilyMember, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostContactDetails", "onResponse 3: "+response.raw()+"~"+json.toString())
                    Toast.makeText(this@AddFamilyMember, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ContactDetailMainData?>?, t: Throwable) {
                Log.d("PostContactDetails", "onFailure: "+t.localizedMessage+"~"+json.toString())
                Toast.makeText(this@AddFamilyMember, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setEditable(editable: Boolean) {
        val editTextList = listOf(
            FullNameTxtEditText, dateOfBirthTxtEditTxt, UserphoneNumberTxtET, EmailIdtxtinputET,
            pincodetxtinputEditText, cityTxtipEditText, stateTxtipEditText,
            flatNoFloorTxtInputEditText, areaLocalityTxtInputEditText, landMarkTxtInputEditText
        )



        for (editText in editTextList) {
            editText.isFocusable = editable
            editText.isFocusableInTouchMode = editable
            editText.isClickable = editable
            editText.isEnabled = editable
        }


        val spinnerList = listOf(
            RetalionSpinner
        )

        for (spinner in spinnerList) {
            spinner.isEnabled = editable
        }


        val linearLayoutList = listOf(
            uploadImage, parentGender, datePickerLL,male_LL,female_LL,other_LL
        )


        val checkBoxSameAddress = listOf(
            checkBoxSameAddress
        )

        for (linearLayout in linearLayoutList) {
            linearLayout.isEnabled = editable
        }

        for (checkBoxSameAddress in checkBoxSameAddress) {
            checkBoxSameAddress.isEnabled = editable
        }
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
            MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create(ApiInterface::class.java)
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

                            startActivity(Intent(this@AddFamilyMember, MyFamilyProfilePage::class.java).putExtra("PostSucces","Yes"))
                            finish()

                            Log.d("PostUserAddressDetails", "User _id: $id"+"~~Pref~~"+"ds"+response.raw()+"~"+json.toString())



                        }

                        Log.d("PostAddressDetails", "onResponse: $message")
                        Toast.makeText(this@AddFamilyMember, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    startActivity(Intent(this@AddFamilyMember, MyFamilyProfilePage::class.java).putExtra("PostSucces","Yes"))
                    finish()

                    Log.d("PostAddressDetails", "onResponse Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@AddFamilyMember, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostUserAddressMainData?>?, t: Throwable) {
                Log.d("PostAddressDetails", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@AddFamilyMember, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }






    private fun GetUserMemberDetail(id: String) {
        if (!APIClient.isNetworkAvailable(this@AddFamilyMember)) {
            Toast.makeText(this@AddFamilyMember, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("GetUserDetailAddfam", "Request : $id")
        val call: Call<GetAddedFamilyMemberData?>? =
            MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create<ApiInterface>(
                ApiInterface::class.java
            ).getUserMemberDetailDtl(id)
        call?.enqueue(object : Callback<GetAddedFamilyMemberData?> {
            override fun onResponse(
                call: Call<GetAddedFamilyMemberData?>,
                response: retrofit2.Response<GetAddedFamilyMemberData?>
            ) {
                if (response.isSuccessful) {
                    val main: GetAddedFamilyMemberData? = response.body()
                    main?.let {
                        Log.d("GetUserDetailAddfam", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")

                        val personalDetail = it.data?.memberDetail?.personalDetail
                        val contactDetail = it.data?.memberDetail?.contactDetail
                        val addressDetail = it.data?.memberDetail?.addressDetail

                        val userID = personalDetail?.id
                        userIdFromPostUserDetails = userID.toString()

                        profilrePhoto= main.data!!.memberDetail!!.profilePicture.toString()
                        fetchImage(main.data!!.memberDetail!!.profilePicture.toString())



                        val pinCode = addressDetail?.getPincode()?.takeIf { it != "null" }?.takeIf { it.isNotEmpty() }
                        val address = addressDetail?.getAddress()?.takeIf { it != "null" }?.takeIf { it.isNotEmpty() }
                        val street = addressDetail?.getStreet()?.takeIf { it != "null" }?.takeIf { it.isNotEmpty() }
                        val landmark = addressDetail?.getLandmark()?.takeIf { it != "null" }?.takeIf { it.isNotEmpty() }
                        val city = addressDetail?.getCity()?.takeIf { it != "null" }?.takeIf { it.isNotEmpty() }
                        val state = addressDetail?.getState()?.takeIf { it != "null" }?.takeIf { it.isNotEmpty() }
                        val emailID = contactDetail?.getEmail()?.takeIf { it != "null" }?.takeIf { it.isNotEmpty() }


                        Log.d("DateOfBirth", "onResponse: "+personalDetail?.dob)

//                        dateOfBirthTxtEditTxt.setText(personalDetail?.dob ?: "")
                        RetalionSpinner.setText(personalDetail?.memberType ?: "")

                        // Set the date of birth in DD-MM-YYYY format
                        personalDetail?.dob?.let { dob ->
                            val formattedDOB = convertIsoDateToCustomFormat(dob)
                            dateOfBirthTxtEditTxt.setText(formattedDOB)
                        }

                        val gender = personalDetail?.gender
                        when (gender) {
                            "M" -> selectGenderLayout(male_LL)
                            "Y" -> selectGenderLayout(female_LL)
                            else -> selectGenderLayout(other_LL)
                        }

                        EmailIdtxtinputET.setText(emailID ?: "")

                        val fullName = personalDetail?.fullName
                        val phoneNumString = contactDetail?.getMobileNumber()?.toString() ?: ""
                        val phoneNum = if (phoneNumString == "null" || phoneNumString.isEmpty()) "" else phoneNumString

                        FullNameTxtEditText.setText(fullName ?: "")
                        UserphoneNumberTxtET.setText(phoneNum)

                        pincodetxtinputEditText.setText(pinCode ?: "")
                        flatNoFloorTxtInputEditText.setText(address ?: "")
                        areaLocalityTxtInputEditText.setText(street ?: "")
                        landMarkTxtInputEditText.setText(landmark ?: "")
                        cityTxtipEditText.setText(city ?: "")
                        stateTxtipEditText.setText(state ?: "")
                    }
                } else {
                    Log.d("GetUserDetailAddfam", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@AddFamilyMember, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAddedFamilyMemberData?>?, t: Throwable) {
                Log.e("GetUserDetailAddfam", "Failed to get user details", t)
                Toast.makeText(this@AddFamilyMember, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun convertIsoDateToCustomFormat(isoDate: String): String {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val customFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = isoFormat.parse(isoDate)
        return customFormat.format(date)
    }



    private fun GetUserDetail(mobileNumberString: String) {
        if (!APIClient.isNetworkAvailable(this@AddFamilyMember)) {
            Toast.makeText(this@AddFamilyMember, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetUserDetailMain", "Request : $mobileNumberString")
        val call: Call<GetUserDeatilsMainGet?>? =
            MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create<ApiInterface>(
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
                        val memberType: String? = it.getData()?.getPersonalDetail()?.getMemberType()
                        val addressDetail = it.getData()?.getAddressDetail()

                        val pinCode = addressDetail?.getPincode()
                        val address = addressDetail?.getAddress()
                        val street = addressDetail?.getStreet()
                        val landmark = addressDetail?.getLandmark()
                        val city = addressDetail?.getCity()
                        val state = addressDetail?.getState()

                        if (!pinCode.isNullOrEmpty()) {
                            if (checkBoxSameAddress.isChecked) {
                                pinCode?.let { pincodetxtinputEditText.setText(it) }
                                address?.let { flatNoFloorTxtInputEditText.setText(it) }
                                street?.let { areaLocalityTxtInputEditText.setText(it) }
                                landmark?.let { landMarkTxtInputEditText.setText(it) }
                                city?.let { cityTxtipEditText.setText(it) }
                                state?.let { stateTxtipEditText.setText(it) }
                            }
                        } else {
                            checkBoxSameAddress.visibility = View.GONE
                        }

                        if (!fullName.isNullOrEmpty()) {
                            checkBoxSameAddress.text = "$fullName ($memberType)"
                        } else {
                            checkBoxSameAddress.visibility = View.GONE
                        }

                        if (checkBoxSameAddress.isChecked) {
                            setAddressFields(
                                pinCode ?: "",
                                address ?: "",
                                street ?: "",
                                landmark ?: "",
                                city ?: "",
                                state ?: ""
                            )
                        }

                        Log.d("GetUserDetailMain", "Full name: $fullName ~pinCode~ $pinCode ~address~ $address")
                    }
                } else {
                    Log.d("GetUserDetailMain", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@AddFamilyMember, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetUserDeatilsMainGet?>?, t: Throwable) {
                Log.e("GetUserDetailMain", "Failed to get user details", t)
                Toast.makeText(this@AddFamilyMember, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun DeleteMemberdetail(id: String, alertDialog: AlertDialog) {

        Log.d("LOGPostBrfTRan", "Resp : "+id.toString())
        val call: Call<CommonData?>? =
            MyApplicationPort3009.getRetrofitClient(this@AddFamilyMember).create<ApiInterface>(
                ApiInterface::class.java
            ).deleteMemberDetail(id.toString())
        call?.enqueue(object : Callback<CommonData?> {
            override fun onResponse(
                call: Call<CommonData?>,
                response: retrofit2.Response<CommonData?>
            ) {
                if (response.isSuccessful()) {
                    val main: CommonData? = response.body()
                    try {

                        if (response.isSuccessful){

                            alertDialog.dismiss()


                            startActivity(Intent(this@AddFamilyMember, MyFamilyProfilePage::class.java).putExtra("PostSucces","Delete"))
                            finish()

                            Log.d("ResponseRawa", "onResponse 1: "+response.raw()+"~"+id.toString())

                            Toast.makeText(this@AddFamilyMember, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("ResponseRawa", "onResponse 2: "+response.raw()+"~"+id.toString())
                        Toast.makeText(this@AddFamilyMember, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("ResponseRawa", "onResponse 3: "+response.raw()+"~"+id.toString())
                    Toast.makeText(this@AddFamilyMember, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            public override fun onFailure(call: Call<CommonData?>?, t: Throwable) {
                Log.d("ResponseRawa", "onResponse: "+t.localizedMessage+"~"+id.toString())
                Toast.makeText(this@AddFamilyMember, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAddressFields(pinCode: String, address: String, street: String, landmark: String, city: String, state: String) {
        pincodetxtinputEditText.setText(pinCode)
        flatNoFloorTxtInputEditText.setText(address)
        areaLocalityTxtInputEditText.setText(street)
        landMarkTxtInputEditText.setText(landmark)
        cityTxtipEditText.setText(city)
        stateTxtipEditText.setText(state)
    }

    private fun clearAddressFields() {
        pincodetxtinputEditText.text = null
        flatNoFloorTxtInputEditText.text = null
        areaLocalityTxtInputEditText.text = null
        landMarkTxtInputEditText.text = null
        cityTxtipEditText.text = null
        stateTxtipEditText.text = null
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

        val call = MyApplicationPort9001.getRetrofitClient(this@AddFamilyMember).create(ApiInterface::class.java)
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

                    Toast.makeText(this@AddFamilyMember, "Failed to fetch image", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("FetchImage", "Failure: ${t.message}", t)
                Log.d("FetchImage", "Request URL3: ${call.request().url}"+profilePhoto)

                Toast.makeText(this@AddFamilyMember, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchImage(profilePhoto: String?) {
        if (profilePhoto == null) {
            Log.e("FetchImage", "Profile photo URL is null")
            return
        }

        val call = MyApplicationPort9001.getRetrofitClient(this@AddFamilyMember).create(ApiInterface::class.java)
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

                    Toast.makeText(this@AddFamilyMember, "Failed to fetch image", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("FetchImage", "Failure: ${t.message}", t)
                Log.d("FetchImage", "Request URL3: ${call.request().url}"+profilePhoto)

                Toast.makeText(this@AddFamilyMember, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
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

        val call: Call<UploadImageMainGet> = MyApplicationPort9001.getRetrofitClient(this@AddFamilyMember).create(ApiInterface::class.java)
            .uploadImage(body, mobileNumberBody, docTypeBody, docIdBody)

        call.enqueue(object : Callback<UploadImageMainGet> {
            override fun onResponse(call: Call<UploadImageMainGet>, response: Response<UploadImageMainGet>) {
                if (response.isSuccessful) {
                    Log.d("UploadImageData", "Success: ${response.body()?.message} ~~~ ${response.body()!!.data!!.docName}")
                    PostUserProfilePic(userIdFromPostUserDetails, response.body()!!.data!!.docName, alertDialog)
                    Toast.makeText(this@AddFamilyMember, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("UploadImageData", "Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@AddFamilyMember, response.body()?.message ?: "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UploadImageMainGet>, t: Throwable) {
                Log.e("UploadImageData", "Failure: ${t.message}", t)
                Toast.makeText(this@AddFamilyMember, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
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

        val call: Call<PostUserProfilePicMainData?>? = MyApplicationPort3009.
        getRetrofitClient(this@AddFamilyMember)
            .create(ApiInterface::class.java)
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
                Toast.makeText(this@AddFamilyMember, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
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
}