package com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3010
import com.bnet.sarvesuraksha.model_api.GetVehicleRcDetailMain
import com.bnet.sarvesuraksha.model_api.PostVehcileIndMainData
import com.bnet.sarvesuraksha.model_api.PostVehicleAndUserMainData
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Landing_Page.LandingPage
import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.skydoves.powerspinner.PowerSpinnerView
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class CarInsurance : ComponentActivity() {

    private lateinit var landingPage: View
    private lateinit var enterCarNumber: TextInputEditText
    private lateinit var enterCarNumberTxtInputLayout: TextInputLayout
    private lateinit var view_free_qoutes: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var parentRcDetail: LinearLayout
    private lateinit var Verify_TV: TextView
    private lateinit var addCardNumber: TextView
    private lateinit var storedMobileNumber: String
    private lateinit var OwndamageParentLL: LinearLayout
    private lateinit var ThirdpartyparentLL: LinearLayout
    private lateinit var thirdPartyLL: LinearLayout
    private lateinit var OwndamageLL: LinearLayout
    private lateinit var VehicleRegistationNumber: String


    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_insurance)

        landingPage = findViewById(R.id.landing_page)
        enterCarNumber = findViewById(R.id.enterCarNumber)
        addCardNumber = findViewById(R.id.addCardNumber)
        view_free_qoutes = findViewById(R.id.view_free_qoutes)
        Verify_TV = findViewById(R.id.Verify_TV)
        goBack = findViewById(R.id.goBack)
        parentRcDetail = findViewById(R.id.parentRcDetail)
        enterCarNumberTxtInputLayout = findViewById(R.id.enterCarNumberTxtInputLayout)
        sharedPreferences = getSharedPreferences("com.bnet.sarvesuraksha", Context.MODE_PRIVATE)
        storedMobileNumber = sharedPreferences.getString("mobile_number", null).toString()
        view_free_qoutes.isClickable = false
        enterCarNumberTxtInputLayout.isHintAnimationEnabled = false

        enterCarNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val uppercaseText = s.toString().toUpperCase(Locale.ROOT)
                if (s.toString() != uppercaseText) {
                    enterCarNumber.setText(uppercaseText)
                    enterCarNumber.setSelection(uppercaseText.length)
                }

                if (s != null && s.length == 10) {
                    enterCarNumberTxtInputLayout.boxStrokeColor = ContextCompat.getColor(this@CarInsurance, R.color.green_focused)
                    view_free_qoutes.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#560FE5"))
                    view_free_qoutes.isClickable = true

                    view_free_qoutes.setOnClickListener {

                        val enteredCarNumber = enterCarNumber.text.toString().toUpperCase()
                        PostRcDetails(enteredCarNumber)
                    }

                    enterCarNumber.hideKeyboard()
                } else {
                    enterCarNumberTxtInputLayout.boxStrokeColor = ContextCompat.getColor(this@CarInsurance, R.color.purple_focused)

                    view_free_qoutes.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#cacddc"))
                    view_free_qoutes.isClickable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        enterCarNumber.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

                landingPage.visibility = View.GONE
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
                    }
                }
            }
            false
        }



        goBack.setOnClickListener {
            val intent = Intent(this@CarInsurance, LandingPage::class.java)
            startActivity(intent)
        }
        addCardNumber.setOnClickListener {
            val intent = Intent(this@CarInsurance, AddVehicleData::class.java)
            startActivity(intent)
        }
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@CarInsurance, LandingPage::class.java)
        startActivity(intent)
        finish()
    }
    private fun ShowWeFoundYourCarAletDialog(
        enteredCarNumber: String,
        main: GetVehicleRcDetailMain?
    ) {
        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.we_found_your_car, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog1 = builder.create()
        alertDialog1.setCancelable(false)


        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val carNumber = alertDialogView.findViewById<TextView>(R.id.carNumber)
        val edit_car_number = alertDialogView.findViewById<LinearLayout>(R.id.edit_car_number)
        val confirmAndProceed = alertDialogView.findViewById<LinearLayout>(R.id.confirmAndProceed)
        val VehicleModelName = alertDialogView.findViewById<TextView>(R.id.VehicleModelName)
        val vheicleType = alertDialogView.findViewById<TextView>(R.id.vheicleType)
        val carImageSet = alertDialogView.findViewById<ImageView>(R.id.carImageSet)
        val editCarDetails = alertDialogView.findViewById<LinearLayout>(R.id.editCarDetails)


//        Glide.with(this)
//            .load(main?.data!!.vehicleDetail!!.vehiclePictures!!.get(0).picture)
//            .into(carImageSet)



        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateFormatOutput = SimpleDateFormat("yyyy")
        val registrationDateStr= main?.data!!.vehicleDetail!!.registrationDate

        val year = try {
            val date = dateFormatInput.parse(registrationDateStr)
            date?.let { dateFormatOutput.format(it) } ?: "Unknown Year"
        } catch (e: ParseException) {
            "Unknown Year"
        }


        val makerName = main?.data?.vehicleDetail?.makerName ?: ""
        val modelName = main?.data?.vehicleDetail?.modelName ?: ""
        val variantName = main?.data?.vehicleDetail?.variantName ?: ""
        val fuelType = main?.data?.vehicleDetail?.fuelType ?: ""

        val truncatedMakerName = if (makerName.length > 5) {
            makerName.split(" ")[0] // Get the first word
        } else {
            makerName
        }

        val vehcileType = "$truncatedMakerName $modelName"
        val vehcileTypeVal = "$variantName-$fuelType-$year"

        vheicleType.text = vehcileTypeVal.toUpperCase()


        carNumber.text= main?.data!!.vehicleDetail!!.registrationNumber!!.toUpperCase()
        VehicleModelName.text= vehcileType.toUpperCase()

        VehicleRegistationNumber=main?.data!!.vehicleDetail!!.registrationNumber!!.toUpperCase()

        editCarDetails.setOnClickListener {
            val intent = Intent(this@CarInsurance, AddVehicleData::class.java)
            intent.putExtra("VehicleNumberPost",VehicleRegistationNumber)
            intent.putExtra("Type","Edit")
            startActivity(intent)
        }

        confirmAndProceed.setOnClickListener {

            Log.d("INSDETAIL_S", "ShowWeFoundYourCarAletDialog: "+main?.data!!.vehicleDetail!!.insuranceDetail!!.size)
            if (main?.data!!.vehicleDetail!!.insuranceDetail!!.size>1){
                ConfirmExistingPolicyAlertDialogMore(alertDialog1,main)
            }else{
                ConfirmExistingPolicyAlertDialog(alertDialog1,main)
            }

           
            alertDialog1.hide()
        }



        closeIcon.setOnClickListener {
            alertDialog1.dismiss()
        }


        alertDialog1.show()
    }
    private fun ConfirmExistingPolicyAlertDialog(
        alertDialog1: AlertDialog,
        main: GetVehicleRcDetailMain
    ) {

        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.confirm_existing_policy_details, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog2 = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val confirmAndProceed = alertDialogView.findViewById<LinearLayout>(R.id.confirmAndProceed)

        val policyType = alertDialogView.findViewById<PowerSpinnerView>(R.id.policyType)
        val insurancename = alertDialogView.findViewById<PowerSpinnerView>(R.id.insurancename)
        val expiryDate = alertDialogView.findViewById<TextInputEditText>(R.id.expiryDate)

        val InsurerList = listOf("Tata Aig", "Bajaj Aig", "Digit", "Reliance")
        val InsurerTypeList = listOf("Own Damage", "Third Party")

        insurancename.setItems(InsurerList)
        policyType.setItems(InsurerTypeList)

        val vehicleInsuranceDetails = main?.data?.vehicleDetail?.insuranceDetail
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val currentDate = Date()
        var policyNumberOd: String = ""
        var InsuranceType: String = ""
        var vehicleId: String = ""

        vehicleInsuranceDetails?.forEach { insuranceDetail ->
            val expiryDates = dateFormat.parse(insuranceDetail.policyExpiryDate)

            val normalizedInsurerName = insuranceDetail.insurerName!!.trim().lowercase()
            val insurerIndex = InsurerList.indexOfFirst {
                it.trim().lowercase() == normalizedInsurerName
            }
            vehicleId = main?.data?.vehicleDetail?.id.toString()
            Log.d("insurerIndex", "insurer Index: $insurerIndex ~~ ${insuranceDetail.insurerName}")


            if (insurerIndex != -1) {
                insurancename.selectItemByIndex(insurerIndex)
            }
            InsuranceType=insuranceDetail.insuranceType.toString()

            if (insuranceDetail.insuranceType.equals("own_damage", ignoreCase = true)) {
                policyType.selectItemByIndex(0)
            } else if (insuranceDetail.insuranceType.equals("third_party", ignoreCase = true)) {
                policyType.selectItemByIndex(1)
            }

            Log.d("APIFORMAT", "API FORMAT NonConvert: ${insuranceDetail.policyExpiryDate}")
            Log.d("APIFORMAT", "API FORMAT Converted: ${formatDate(insuranceDetail.policyExpiryDate.toString())}")
            expiryDate.setText(formatDate(insuranceDetail.policyExpiryDate.toString()))
            policyNumberOd = insuranceDetail.policyNumber.toString()
        }

        expiryDate.setOnClickListener { showDatePickerDialog(expiryDate) }

        alertDialog2.setCancelable(false)


        confirmAndProceed.setOnClickListener {
//            [
//                {
//                    "insuranceType": "own_damage",
//                    "insurerName": "Tata AIG",
//                    "policyNumber": "01710859910000",
//                    "policyExpiryDate": "2026-11-18T00:00:00.000Z"
//                }
//            ]
            val ownDamageInsurer = insurancename.text.toString()
            val ownDamageExpiryDate = expiryDate.text.toString()


            val insuranceDetails = listOf(
                createInsuranceDetailJson(InsuranceType, ownDamageInsurer, policyNumberOd.toString(), ownDamageExpiryDate)

            )

            PostVehicleInsuranceDetail(alertDialog2, vehicleId, insuranceDetails)

        }

        closeIcon.setOnClickListener {
            alertDialog2.dismiss()
            alertDialog1.show()
        }

        alertDialog2.show()
    }

    private fun AddVehcileData(VehicleRegistationNumber: String) {

        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.unable_to_find_car, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val addCarDetails = alertDialogView.findViewById<LinearLayout>(R.id.addCarDetails)

        alertDialog.setCancelable(false)

        addCarDetails.setOnClickListener {
            val intent = Intent(this@CarInsurance, AddVehicleData::class.java)
            intent.putExtra("VehicleNumberPost",VehicleRegistationNumber)
            intent.putExtra("Type","Edit")
            startActivity(intent)

        }

        closeIcon.setOnClickListener {
            alertDialog.dismiss()
        }


        alertDialog.show()
    }

    private fun ConfirmExistingPolicyAlertDialogMore(
        alertDialog1: AlertDialog,
        main: GetVehicleRcDetailMain
    ) {
        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.confirm_existing_policy_details_combo, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog2 = builder.create()

        alertDialog2.setCancelable(false)

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val ProceedOwnDamage = alertDialogView.findViewById<LinearLayout>(R.id.ProceedOwnDamage)
        OwndamageParentLL = alertDialogView.findViewById<LinearLayout>(R.id.OwndamageParentLL)
        ThirdpartyparentLL = alertDialogView.findViewById<LinearLayout>(R.id.ThirdpartyparentLL)
        thirdPartyLL = alertDialogView.findViewById<LinearLayout>(R.id.thirdPartyLL)
        OwndamageLL = alertDialogView.findViewById<LinearLayout>(R.id.OwndamageLL)
        val InsuresNameSpinnerOD = alertDialogView.findViewById<PowerSpinnerView>(R.id.InsuresNameSpinnerOD)
        val thirdPartySpinner = alertDialogView.findViewById<PowerSpinnerView>(R.id.thirdPartySpinner)
        val expiryDateOD = alertDialogView.findViewById<TextInputEditText>(R.id.expiryDateOD)
        val expirydateThirdParty = alertDialogView.findViewById<TextInputEditText>(R.id.expirydateThirdParty)
        val confirmAndProceed = alertDialogView.findViewById<LinearLayout>(R.id.confirmAndProceed)
        val alertTextViewOD = alertDialogView.findViewById<TextView>(R.id.alertTextViewOD)
        val alertODLL = alertDialogView.findViewById<LinearLayout>(R.id.alertODLL)
        val alertTPLL = alertDialogView.findViewById<LinearLayout>(R.id.alertTPLL)
        val alertTextViewTP = alertDialogView.findViewById<TextView>(R.id.alertTextViewTP)
        alertTPLL.visibility = View.GONE
        alertODLL.visibility = View.GONE
        var policyNumberOd:String=""
        var policyNumberTp:String=""
        val InsurerList = listOf("Tata Aig", "Bajaj Aig", "Digit", "Reliance")
        InsuresNameSpinnerOD.setItems(InsurerList)
        thirdPartySpinner.setItems(InsurerList)

        val vehicleInsuranceDetails = main?.data?.vehicleDetail?.insuranceDetail
        val vehicleId = main?.data?.vehicleDetail?.id
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val currentDate = Date()

        vehicleInsuranceDetails?.forEach { insuranceDetail ->
            val expiryDate = dateFormat.parse(insuranceDetail.policyExpiryDate)
            val daysDifference = getDateDifference(currentDate, expiryDate)

            InsuresNameSpinnerOD.selectItemByIndex(InsurerList.indexOf(insuranceDetail.insurerName))
            Log.d("APIFORMAT", "API FORMAT NonConvert: "+insuranceDetail.policyExpiryDate.toString())
            Log.d("APIFORMAT", "API FORMAT Converted: "+formatDate(insuranceDetail.policyExpiryDate.toString()))
            expiryDateOD.setText(formatDate(insuranceDetail.policyExpiryDate.toString()))
            policyNumberOd = insuranceDetail.policyNumber.toString()
            if (daysDifference != null) {
                if (daysDifference <= 0) {
                    alertODLL.visibility = View.VISIBLE
                    alertTextViewOD.text = "Your own damage policy has expired. Please renew it!"
                } else if (daysDifference <= 15) {
                    alertODLL.visibility = View.VISIBLE
                    alertTextViewOD.text = "Your own damage policy is about to get expired in $daysDifference days. Renew today!"
                }
            }
            when (insuranceDetail.insuranceType) {
                "own_damage" -> {
                    InsuresNameSpinnerOD.selectItemByIndex(InsurerList.indexOf(insuranceDetail.insurerName))
                    Log.d("APIFORMAT", "API FORMAT NonConvert: "+insuranceDetail.policyExpiryDate.toString())
                    Log.d("APIFORMAT", "API FORMAT Converted: "+formatDate(insuranceDetail.policyExpiryDate.toString()))
                    expiryDateOD.setText(formatDate(insuranceDetail.policyExpiryDate.toString()))
                    policyNumberOd = insuranceDetail.policyNumber.toString()
                    if (daysDifference != null) {
                        if (daysDifference <= 0) {
                            alertODLL.visibility = View.VISIBLE
                            alertTextViewOD.text = "Your own damage policy has expired. Please renew it!"
                        } else if (daysDifference <= 15) {
                            alertODLL.visibility = View.VISIBLE
                            alertTextViewOD.text = "Your own damage policy is about to get expired in $daysDifference days. Renew today!"
                        }
                    }
                }
                "third_party" -> {
                    thirdPartySpinner.selectItemByIndex(InsurerList.indexOf(insuranceDetail.insurerName))
                    expirydateThirdParty.setText(formatDate(insuranceDetail.policyExpiryDate.toString()))
                    policyNumberTp = insuranceDetail.policyNumber.toString()
                    if (daysDifference != null) {
                        if (daysDifference <= 0) {
                            alertTPLL.visibility = View.VISIBLE
                            alertTextViewTP.text = "Your third party policy has expired. Please renew it!"
                        } else if (daysDifference <= 15) {
                            alertTPLL.visibility = View.VISIBLE
                            alertTextViewTP.text = "Your third party policy is about to get expired in $daysDifference days. Renew today!"
                        }
                    }
                }
            }
        }

        expiryDateOD.setOnClickListener { showDatePickerDialog(expiryDateOD) }
        expirydateThirdParty.setOnClickListener { showDatePickerDialog(expirydateThirdParty) }

        OwndamageParentLL.setOnClickListener { selectLayout(OwndamageParentLL, OwndamageLL) }
        ThirdpartyparentLL.setOnClickListener { selectLayout(ThirdpartyparentLL, thirdPartyLL) }

        ProceedOwnDamage.setOnClickListener {
            selectLayout(ThirdpartyparentLL, thirdPartyLL)
        }

        confirmAndProceed.setOnClickListener {
            val ownDamageInsurer = InsuresNameSpinnerOD.text.toString()
            val ownDamageExpiryDate = expiryDateOD.text.toString()

            val thirdPartyInsurer = thirdPartySpinner.text.toString()
            val thirdPartyExpiryDate = expirydateThirdParty.text.toString()

            val insuranceDetails = listOf(
                createInsuranceDetailJson("own_damage", ownDamageInsurer, policyNumberOd.toString(), ownDamageExpiryDate),
                createInsuranceDetailJson("third_party", thirdPartyInsurer, policyNumberTp.toString(), thirdPartyExpiryDate)
            )

            PostVehicleInsuranceDetail(alertDialog2, vehicleId, insuranceDetails)
        }

        closeIcon.setOnClickListener {
            alertDialog2.dismiss()
            alertDialog1.show()
        }

        alertDialog2.show()
    }

    private fun showDatePickerDialog(editText: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            val selectedDate = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }.time


            val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(selectedDate)
            editText.setText(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }


    private fun createInsuranceDetailJson(
        insuranceType: String,
        insurerName: String,
        policyNumber: String,
        policyExpiryDate: String
    ): JSONObject {
        return JSONObject().apply {
            put("insuranceType", insuranceType)
            put("insurerName", insurerName)
            put("policyNumber", policyNumber) // Add policyNumber to the JSON
            put("policyExpiryDate", formatExpiryDateForPost(policyExpiryDate))
        }
    }


    private fun PostVehicleInsuranceDetail(
        alertDialog2: AlertDialog,
        vehicleId: String?,
        insuranceDetails: List<JSONObject>
    ) {
        val jsonArray = JSONArray(insuranceDetails).toString()

        Log.d("LOGPostBrfTRan", "Resp : $jsonArray")
        val call: Call<PostVehcileIndMainData?> =
            MyApplicationPort3010.getRetrofitClient(this).create(ApiInterface::class.java)
                .postVehicleInsData(vehicleId.toString(), jsonArray)
        call.enqueue(object : Callback<PostVehcileIndMainData?> {
            override fun onResponse(
                call: Call<PostVehcileIndMainData?>,
                response: retrofit2.Response<PostVehcileIndMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostVehcileIndMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("ResponseRawa", "onResponse 1: ${response.raw()}~$jsonArray")
                            ClaimInExistingPolciy(alertDialog2,main,vehicleId)
                            alertDialog2.hide()

                            Toast.makeText(
                                this@CarInsurance,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@CarInsurance,
                            e.localizedMessage.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {

                    Log.d("ResponseRawa", "onResponse 3: ${response.raw()}~$jsonArray")
                    Toast.makeText(this@CarInsurance, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostVehcileIndMainData?>, t: Throwable) {
                Log.d("ResponseRawa", "onResponse: ${t.localizedMessage}~$jsonArray")
                Toast.makeText(this@CarInsurance, "Server Error $t", Toast.LENGTH_SHORT).show()
            }
        })
    }




    private fun formatExpiryDateForPost(dateStr: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return try {
            val date = inputFormat.parse(dateStr)
            date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: ParseException) {
            "Invalid Date"
        }
    }


    private fun getDateDifference(date1: Date, date2: Date?): Long? {
        return date2?.let {
            val diff = it.time - date1.time
            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())
        return try {
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: ParseException) {
            ""
        }
    }

    private fun checkExpiryDate(expiryDate: String, alertTextView: TextView, alertLayout: LinearLayout, insuranceType: String) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        val expiryDateObj = sdf.parse(expiryDate)

        expiryDateObj?.let {
            val diffInMillis = expiryDateObj.time - currentDate.time
            val diffInDays = diffInMillis / (1000 * 60 * 60 * 24)

            if (diffInDays in 0..15) {
                alertTextView.text = "Your $insuranceType policy is about to get expired in $diffInDays days. Renew today!"
                alertLayout.visibility = View.VISIBLE
            } else {
                alertLayout.visibility = View.GONE
            }
        }
    }

    private fun selectLayout(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {

        OwndamageParentLL.background = null
        ThirdpartyparentLL.background = null


        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg)


        thirdPartyLL.visibility = if (detailsLayout == thirdPartyLL) LinearLayout.VISIBLE else LinearLayout.GONE
        OwndamageLL.visibility = if (detailsLayout == OwndamageLL) LinearLayout.VISIBLE else LinearLayout.GONE
    }

    private fun ClaimInExistingPolciy(
        alertDialog2: AlertDialog,
        main: PostVehcileIndMainData?,
        vehicleId: String?
    ) {
        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.claim_in_existing_policy, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog3 = builder.create()

        alertDialog3.setCancelable(false)

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val proceedButton = alertDialogView.findViewById<LinearLayout>(R.id.Proceed)
        val radioGroup = alertDialogView.findViewById<RadioGroup>(R.id.radioGroup)
        val radioGroupCNg = alertDialogView.findViewById<RadioGroup>(R.id.radioGroupCNg)

        proceedButton.isEnabled = false

        val checkRadioGroups = {
            val isRadioGroupChecked = radioGroup.checkedRadioButtonId != -1
            val isRadioGroupCNgChecked = radioGroupCNg.checkedRadioButtonId != -1
            proceedButton.isEnabled = isRadioGroupChecked && isRadioGroupCNgChecked
        }

        radioGroup.setOnCheckedChangeListener { _, _ ->
            checkRadioGroups()
        }

        radioGroupCNg.setOnCheckedChangeListener { _, _ ->
            checkRadioGroups()
        }
        proceedButton.setOnClickListener {
            val isClaimed = when (radioGroup.checkedRadioButtonId) {
                R.id.radioYes -> true
                R.id.radioNo -> false
                else -> null
            }

            val cngKitInstalled = when (radioGroupCNg.checkedRadioButtonId) {
                R.id.radioYesCNG -> true
                R.id.radioNoCNG -> false
                else -> null
            }


            EnterUserDetails(alertDialog3, isClaimed, cngKitInstalled, main,vehicleId)

            alertDialog3.dismiss()
        }

        closeIcon.setOnClickListener {
            alertDialog2.show()
            alertDialog3.dismiss()
        }

        alertDialog3.show()
    }



    private fun EnterUserDetails(
        alertDialog3: AlertDialog,
        isClaimed: Boolean?,
        cngKitInstalled: Boolean?,
        main: PostVehcileIndMainData?,
        vehicleId: String?
    ) {
        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.enter_your_details, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        alertDialog.setCancelable(false)

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val proceedButton = alertDialogView.findViewById<LinearLayout>(R.id.Proceed)
        val fullNameTxtEditText = alertDialogView.findViewById<TextInputEditText>(R.id.FullNameTxtEditText)
        val userPhoneNumberTxtET = alertDialogView.findViewById<TextInputEditText>(R.id.UserphoneNumberTxtET)
        val pincode = alertDialogView.findViewById<TextInputEditText>(R.id.pincode)



        fullNameTxtEditText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source?.filter { it.isLetter() || it.isWhitespace() }
        })


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

        proceedButton.setOnClickListener {
            val fullName = fullNameTxtEditText.text.toString()
            val mobileNumber = userPhoneNumberTxtET.text.toString()
            val pinCode = pincode.text.toString()

            if (fullName.isNotEmpty() && mobileNumber.isNotEmpty() && pinCode.isNotEmpty()) {
                val jsonObject = JSONObject().apply {
                    put("vehicleId", main?.data?.id ?: "")
                    put("vehicleSegment", "CAR")
                    put("isClaimed", isClaimed ?: false)
                    put("cngKitInstalled", cngKitInstalled ?: false)
                    put("loginId", main?.data!!.ownerDetail?.mobileNumber ?: "")

                    val userDetail = JSONObject().apply {
                        put("fullName", fullName)
                        put("mobileNumber", mobileNumber)
                        put("pincode", pinCode)
                    }

                    put("userDetail", userDetail)
                }

                PostVehicleAndUser(jsonObject, vehicleId)
                alertDialog.dismiss()

//                val call: Call<GetPincodeDetailsMainGet?>? = MyApplicationPort3009
//                    .getRetrofitClient(this@CarInsurance)
//                    .create(ApiInterface::class.java).findPincodedetails(pinCode)
//                Log.d("FindPincodeDetails", "Find Pincode Details URL: ${call?.request()?.url}")
//
//                call?.enqueue(object : Callback<GetPincodeDetailsMainGet?> {
//                    override fun onResponse(call: Call<GetPincodeDetailsMainGet?>, response: Response<GetPincodeDetailsMainGet?>) {
//                        if (response.isSuccessful) {
//                            val userDetails: GetPincodeDetailsMainGet? = response.body()
//                            Log.d("FindPincodeDetails", "Find Pincode Details onResponse: Success")
//
//                            if (userDetails!!.status==200){
//
//                                if (userDetails != null) {
//                                    val jsonObject = JSONObject().apply {
//                                        put("vehicleId", main?.data?.id ?: "")
//                                        put("vehicleSegment", "CAR")
//                                        put("isClaimed", isClaimed ?: false)
//                                        put("cngKitInstalled", cngKitInstalled ?: false)
//                                        put("loginId", main?.data!!.ownerDetail?.mobileNumber ?: "")
//
//                                        val userDetail = JSONObject().apply {
//                                            put("fullName", fullName)
//                                            put("mobileNumber", mobileNumber)
//                                            put("pincode", pinCode)
//                                        }
//
//                                        put("userDetail", userDetail)
//                                    }
//
//                                    PostVehicleAndUser(jsonObject, vehicleId)
//                                    alertDialog.dismiss()
//
//                                }
//                                else {
//                                    Toast.makeText(this@CarInsurance, "Invalid pincode. Please enter a valid one.", Toast.LENGTH_SHORT).show()
//                                }
//                            }else{
//                                Toast.makeText(this@CarInsurance, "Invalid pincode. Please enter a valid one.", Toast.LENGTH_SHORT).show()
//                            }
//
//                        } else {
//                            Log.e("FindPincodeDetails", "Find Pincode Details onResponse: Error ${response.code()} - ${response.message()}")
//                            Toast.makeText(this@CarInsurance, "Failed to validate pincode. Please try again.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<GetPincodeDetailsMainGet?>, t: Throwable) {
//                        Log.e("FindPincodeDetails", "Find Pincode Details onFailure: ${t.message}", t)
//                        Toast.makeText(this@CarInsurance, "Server error. Please try again later.", Toast.LENGTH_SHORT).show()
//                    }
//                })
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }


        closeIcon.setOnClickListener {
            alertDialog3.show()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }



    private fun PostVehicleAndUser(jsonObject: JSONObject, vehicleId: String?) {

        Log.d("PostVehicleAndUser", "Resp : "+jsonObject.toString())
        val call: Call<PostVehicleAndUserMainData> =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).postVehicleAndUser(jsonObject.toString())
        call?.enqueue(object : Callback<PostVehicleAndUserMainData?> {
            override fun onResponse(
                call: Call<PostVehicleAndUserMainData?>,
                response: retrofit2.Response<PostVehicleAndUserMainData?>) {

                if (response.isSuccessful()) {
                    val main: PostVehicleAndUserMainData? = response.body()
                    try {
                        if (response.isSuccessful){
                            Log.d("PostVehicleAndUser", "onResponse 1: "+response.raw()+"~"+jsonObject.toString()+"vehicleId"+vehicleId)

                            val intent = Intent(this@CarInsurance, CarInsuranceQoutePlanForm::class.java)
                            intent.putExtra("QuoteId", main?.data!!.id)
                            intent.putExtra("vehicleId", vehicleId)
                            startActivity(intent)

                            Toast.makeText(this@CarInsurance, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {

                        Toast.makeText(this@CarInsurance, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {

                    Log.d("PostVehicleAndUser", "onResponse 3: "+response.raw()+"~"+jsonObject.toString())
                    Toast.makeText(this@CarInsurance, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostVehicleAndUserMainData?>, t: Throwable) {
                Log.d("PostVehicleAndUser", "onResponse: "+t.localizedMessage+"~"+jsonObject.toString())
                Toast.makeText(this@CarInsurance, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }


        })

    }


    private fun PostRcDetails(VehicleRegistationNumber: String) {
        val json: JSONObject = JSONObject()
//        json.put("logInId", storedMobileNumber)
        json.put("vehicleNo", VehicleRegistationNumber)
        json.put("vehicleType", "vehicle_with_number")
        json.put("vehicleSegment", "CAR")

        Log.d("LOGPostBrfTRan", "Resp : "+json.toString())
        val call: Call<GetVehicleRcDetailMain> =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).postRcDetailAndGetRc(json.toString())
        call?.enqueue(object : Callback<GetVehicleRcDetailMain?> {
            override fun onResponse(
                call: Call<GetVehicleRcDetailMain?>,
                response: retrofit2.Response<GetVehicleRcDetailMain?>
            ) {
                if (response.isSuccessful()) {
                    val main: GetVehicleRcDetailMain? = response.body()
                    try {
                        if (response.isSuccessful){
                            Log.d("ResponseRawa", "onResponse 1: "+response.raw()+"~"+json.toString())
                            ShowWeFoundYourCarAletDialog(VehicleRegistationNumber,main)
                            Toast.makeText(this@CarInsurance, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {

                        Toast.makeText(this@CarInsurance, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    AddVehcileData(VehicleRegistationNumber)
                    Log.d("ResponseRawa", "onResponse 3: "+response.raw()+"~"+json.toString())
//                    Toast.makeText(this@CarInsurance, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetVehicleRcDetailMain?>, t: Throwable) {
                Log.d("ResponseRawa", "onResponse: "+t.localizedMessage+"~"+json.toString())
                Toast.makeText(this@CarInsurance, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }



//    override fun onBackPressed() {
//
//
//        if (currentFocus != null) {
//            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//            currentFocus?.clearFocus()
//        }
//        landingPage.visibility = View.VISIBLE
//        super.onBackPressed()
//
//
//    }
}
