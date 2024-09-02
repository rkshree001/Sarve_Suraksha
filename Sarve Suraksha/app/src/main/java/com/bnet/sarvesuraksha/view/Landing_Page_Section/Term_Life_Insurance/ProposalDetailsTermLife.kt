package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3006
import com.bnet.sarvesuraksha.model_api.GetTLAdditionalDetailMainData
import com.bnet.sarvesuraksha.model_api.VerifyDocTypeKycTLMainData
import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.skydoves.powerspinner.PowerSpinnerView
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ProposalDetailsTermLife : AppCompatActivity() {

    private lateinit var panNumberET: TextInputEditText
    private lateinit var dobTIET: TextInputEditText
    private lateinit var dobBasicDetails: TextInputEditText
    private lateinit var NomdobTIET: TextInputEditText
    private lateinit var aadharCardNumber: TextInputEditText
    private lateinit var fullNameTxtEditText: TextInputEditText
    private lateinit var panNumberLL: LinearLayout
    private lateinit var aadharCardLL: LinearLayout
    private lateinit var confirmAndProceed: LinearLayout
    private lateinit var verify_KYC: LinearLayout
    private lateinit var saveTextView: TextView
    private lateinit var drawableTV: ImageView
    private lateinit var confirmAndProccedTV: TextView

    private lateinit var pincodetxtinputEditText: TextInputEditText
    private lateinit var cityTxtipEditText: TextInputEditText
    private lateinit var stateTxtipEditText: TextInputEditText
    private lateinit var flatNoFloorTxtInputEditText: TextInputEditText
    private lateinit var areaLocalityTxtInputEditText: TextInputEditText
    private lateinit var landMarkTxtInputEditText: TextInputEditText

    private lateinit var phoneNumber: TextInputEditText
    private lateinit var emailID: TextInputEditText
    private lateinit var fullNameBasicDetails: TextInputEditText

    private lateinit var indicator1: LinearLayout
    private lateinit var indicator2: LinearLayout
    private lateinit var proposerKYC: LinearLayout
    private lateinit var proposerNomineeKYC: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var verifED_KYC: LinearLayout
    private lateinit var male_LL: LinearLayout
    private lateinit var female_LL: LinearLayout
    private lateinit var other_LL: LinearLayout

    private lateinit var male_LLB: LinearLayout
    private lateinit var female_LLB: LinearLayout
    private lateinit var other_LLB: LinearLayout
    private lateinit var progressBar: CircularProgressBar
    private lateinit var progressText: TextView
    private lateinit var descTV: TextView
    private lateinit var headerText: TextView

    private var TermID: String? = ""
    private var QuoteId: String? = "66d2971f895b87413f309b10"
    private var QuoteIdVerify_KYC: String? = ""

    private val KYC_Type = listOf("PAN Card", "Aadhar Card")
    private lateinit var documentsKyc: PowerSpinnerView
    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private var GenderString: String= "M"
    private var GenderStringBD: String= "M"


    private lateinit var proposerAddressDetails:LinearLayout
    private lateinit var basicDetails:LinearLayout
    private lateinit var additionalDetails:LinearLayout
    private lateinit var medicalHistory:LinearLayout
    private lateinit var nomineeDetails:LinearLayout
    private lateinit var AnnualIncomeSpinner:PowerSpinnerView
    private lateinit var contactDetails:LinearLayout
    private lateinit var OccupationSpinner: PowerSpinnerView
    private lateinit var MartialStatusSpinner: PowerSpinnerView
    private lateinit var EducationSpinner: PowerSpinnerView
    private lateinit var RetalionSpinner: PowerSpinnerView


    private lateinit var confirmAndProceed1:LinearLayout
    private lateinit var confirmAndProccedTV1:TextView
    private lateinit var drawableTV1:ImageView

    private lateinit var med2LL:LinearLayout
    private lateinit var med1LL:LinearLayout

    private lateinit var no_med1:LinearLayout
    private lateinit var yes_med:LinearLayout

    private lateinit var yes_med1:LinearLayout
    private lateinit var no_med:LinearLayout


    private lateinit var med1:TextInputEditText
    private lateinit var med2:TextInputEditText
    private lateinit var NomineefullName:TextInputEditText


    private var isFirstDiseaseSuffered = false
    private var isSecondDiseaseSuffered = false

//    private lateinit var confirmAndProceed2:LinearLayout
//    private lateinit var confirmAndProccedTV2:TextView
//    private lateinit var drawableTV2:ImageView

//    private lateinit var confirmAndProceed3:LinearLayout
//    private lateinit var confirmAndProccedTV3:TextView
//    private lateinit var drawableTV3:ImageView

//    private lateinit var confirmAndProceed4:LinearLayout
//    private lateinit var confirmAndProccedTV4:TextView
//    private lateinit var drawableTV4:ImageView

    val AnnualIncomeList = listOf("25 Lac+", "15 Lac to 24.9 Lac","10 Lac to 14.9 Lac","8 Lac to 9.9 Lac","5 Lac to 7.9 Lac","3 Lac to 4.9 Lac","2 Lac to 2.9 Lac","Less than 2 Lac")
    val OccupationList = listOf("Salaried", "Self Employed / Business","Housewife")
    val MaritalStatus = listOf("Single", "Married", "Divorced", "Widow")
    val EducationList = listOf("postGradAndAbove", "Graduate", "diploma", "12thPass", "10thPass", "below10thPass", "illiterate")
    val RelationList = listOf("Father", "Mother","Wife","Brother","Sister","Son","Daughter")

    val apiOccupationMap = mapOf(
        "salaried" to "Salaried",
        "self_employed" to "Self Employed / Business",
        "business" to "Self Employed / Business",
        "house_wife" to "Housewife",

        "public_sector" to "Salaried",
        "private_sector" to "Salaried",
        "government_sector" to "Salaried",
        "student" to "Student",
        "retired" to "Retired",
        "unemployed" to "Unemployed"
    )

    val apiEducationMap = mapOf(
        "graduate_and_above" to "Graduate and Above",
        "diploma" to "Diploma",
        "12th_pass" to "12th Pass",
        "10th_pass" to "10th Pass",
        "below_10th_pass" to "Below 10th Pass",
        "illiterate" to "Illiterate"
    )

    val AnnualIncomeMap = mapOf(
        "25 Lac+" to Pair(2500000, Int.MAX_VALUE),
        "15 Lac to 24.9 Lac" to Pair(1500000, 2499000),
        "10 Lac to 14.9 Lac" to Pair(1000000, 1499000),
        "8 Lac to 9.9 Lac" to Pair(800000, 990000),
        "5 Lac to 7.9 Lac" to Pair(500000, 790000),
        "3 Lac to 4.9 Lac" to Pair(300000, 490000),
        "2 Lac to 2.9 Lac" to Pair(200000, 290000),
        "Less than 2 Lac" to Pair(0, 199999)
    )


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proposal_details_term_life)
//        TermID = intent.getStringExtra("TermID")
//        QuoteId = intent.getStringExtra("QuoteId")
        pincodetxtinputEditText = findViewById(R.id.pincodetxtinputEditText)
        cityTxtipEditText = findViewById(R.id.cityTxtipEditText)
        stateTxtipEditText = findViewById(R.id.stateTxtipEditText)
        flatNoFloorTxtInputEditText = findViewById(R.id.flatNoFloorTxtInputEditText)
        areaLocalityTxtInputEditText = findViewById(R.id.areaLocalityTxtInputEditText)
        landMarkTxtInputEditText = findViewById(R.id.landMarkTxtInputEditText)
        indicator1 = findViewById(R.id.indicator1)
        indicator2 = findViewById(R.id.indicator2)
        drawableTV = findViewById(R.id.drawableTV)
        proposerNomineeKYC = findViewById(R.id.proposerNomineeKYC)
        goBack = findViewById(R.id.goBack)
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        descTV = findViewById(R.id.descTV)
        headerText = findViewById(R.id.headerText)
        proposerAddressDetails = findViewById(R.id.proposerAddressDetails)
        basicDetails = findViewById(R.id.basicDetails)
        contactDetails = findViewById(R.id.contactDetails)
        additionalDetails = findViewById(R.id.additionalDetails)
        medicalHistory = findViewById(R.id.medicalHistory)
        nomineeDetails = findViewById(R.id.nomineeDetails)
        AnnualIncomeSpinner = findViewById(R.id.AnnualIncomeSpinner)
        OccupationSpinner = findViewById(R.id.occupationSpinner)
        MartialStatusSpinner = findViewById(R.id.maritalStatusSpinner)
        EducationSpinner = findViewById(R.id.educationSpinner)
        RetalionSpinner = findViewById(R.id.RetalionSpinner)
        med1 = findViewById(R.id.med1)
        med2 = findViewById(R.id.med2)
        NomineefullName = findViewById(R.id.NomineefullName)


//        confirmAndProceed2 = findViewById(R.id.confirmAndProceed2)
//        confirmAndProccedTV2 = findViewById(R.id.confirmAndProccedTV2)
//        drawableTV2 = findViewById(R.id.drawableTV2)
        emailID = findViewById(R.id.emailID)
        phoneNumber = findViewById(R.id.phoneNumber)
        fullNameBasicDetails = findViewById(R.id.fullNameBasicDetails)
        med2LL = findViewById(R.id.med2LL)
        med1LL = findViewById(R.id.med1LL)
        no_med = findViewById(R.id.no_med)
        no_med1 = findViewById(R.id.no_med1)
        yes_med = findViewById(R.id.yes_med)
        yes_med1 = findViewById(R.id.yes_med1)


        panNumberET = findViewById(R.id.panNumberET)
        dobTIET = findViewById(R.id.dobTIET)
        dobBasicDetails = findViewById(R.id.dobBasicDetails)
        NomdobTIET = findViewById(R.id.NomdobTIET)
        documentsKyc = findViewById(R.id.documentsKyc)
        verify_KYC = findViewById(R.id.verify_KYC)
        saveTextView = findViewById(R.id.saveTextView)
        aadharCardNumber = findViewById(R.id.aadharCardNumber)
        panNumberLL = findViewById(R.id.panNumberLL)
        aadharCardLL = findViewById(R.id.aadharCardLL)
        confirmAndProceed = findViewById(R.id.confirmAndProceed)
        confirmAndProccedTV = findViewById(R.id.confirmAndProccedTV)
        fullNameTxtEditText = findViewById(R.id.fullNameTxtEditText)
        proposerKYC = findViewById(R.id.proposerKYC)
        verifED_KYC = findViewById(R.id.verifED_KYC)
        confirmAndProceed1 = findViewById(R.id.confirmAndProceed1)
        confirmAndProccedTV1 = findViewById(R.id.confirmAndProccedTV1)
        drawableTV1 = findViewById(R.id.drawableTV1)
//
//        confirmAndProceed3 = findViewById(R.id.confirmAndProceed3)
//        confirmAndProccedTV3 = findViewById(R.id.confirmAndProccedTV3)
//        drawableTV3 = findViewById(R.id.drawableTV3)
//
//        confirmAndProceed4 = findViewById(R.id.confirmAndProceed4)
//        confirmAndProccedTV4 = findViewById(R.id.confirmAndProccedTV4)
//        drawableTV4 = findViewById(R.id.drawableTV4)

        AnnualIncomeSpinner.setItems(AnnualIncomeList)
        OccupationSpinner.setItems(OccupationList)
        MartialStatusSpinner.setItems(MaritalStatus)
        EducationSpinner.setItems(EducationList)
        RetalionSpinner.setItems(RelationList)
        male_LL = findViewById(R.id.male_LL)
        female_LL = findViewById(R.id.female_LL)
        other_LL = findViewById(R.id.other_LL)

        male_LLB = findViewById(R.id.male_LLB)
        female_LLB = findViewById(R.id.female_LLB)
        other_LLB = findViewById(R.id.other_LLB)

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

        male_LLB.setOnClickListener {
            GenderStringBD="M"
            selectGenderLayoutB(male_LLB)
        }

        female_LLB.setOnClickListener {
            GenderStringBD="F"
            selectGenderLayoutB(female_LLB)
        }

        other_LLB.setOnClickListener {
            GenderStringBD="O"
            selectGenderLayoutB(other_LLB)
        }

        dobTIET.setOnClickListener {
            showDatePicker(dobTIET)
        }
        dobBasicDetails.setOnClickListener {
            showDatePicker(dobBasicDetails)
        }

        NomdobTIET.setOnClickListener {
            showDatePicker(NomdobTIET)
        }

        setupDocumentSpinner()
        setupTextWatchers()

        GetPropserDetails(QuoteId.toString())
        GetAdditionalDetails(QuoteId.toString())

        verify_KYC.setOnClickListener {
            VerifyDocTypeKycTL()
        }
        confirmAndProceed.setOnClickListener {
            if (verifED_KYC.isVisible && verify_KYC.visibility == View.GONE) {
                proposerKYC.visibility = View.GONE
                confirmAndProceed.visibility = View.GONE
                confirmAndProceed1.visibility = View.VISIBLE
                proposerNomineeKYC.visibility = View.VISIBLE
                indicator2.visibility = View.VISIBLE
            }

        }

        confirmAndProceed1.setOnClickListener {
            if (proposerNomineeKYC.isVisible){
                PatchPropserDetails()
            }else if (proposerAddressDetails.isVisible){
                PatchAddressDetails()
            }else if (contactDetails.isVisible){
                patchContactDetails()
            }else if (basicDetails.isVisible){
                patchMemberDetails()
            }else if (additionalDetails.isVisible){
                PatchAdditionalDetails()
            }else if (medicalHistory.isVisible){
                PatchMedicalDetails()
            }else if (nomineeDetails.isVisible){
                PatchPropserNomineeDetail()
            }

        }

        goBack.setOnClickListener {
            if (nomineeDetails.isVisible){
                medicalHistory.visibility=View.VISIBLE
                nomineeDetails.visibility=View.GONE
                indicator1.visibility=View.VISIBLE
                indicator2.visibility=View.VISIBLE
                descTV.setText("Next: Summary")
                headerText.setText("Nominee Details")
                progressBar.progress = 100f
                progressText.setText("5/5")
            }else if (medicalHistory.isVisible){
                medicalHistory.visibility=View.GONE
                additionalDetails.visibility=View.VISIBLE
                indicator1.visibility=View.VISIBLE
                indicator2.visibility=View.VISIBLE
                descTV.setText("Next:Nominee details")
                headerText.setText("Medical details")
                progressBar.progress = 80F
                progressText.setText("4/5")
            }else if (additionalDetails.isVisible){

                contactDetails.visibility=View.GONE
                basicDetails.visibility=View.VISIBLE
                additionalDetails.visibility=View.GONE
                indicator1.visibility=View.VISIBLE
                indicator2.visibility=View.VISIBLE
                headerText.setText("Contact details")
                descTV.setText("Next:Medical details")
                headerText.setText("Member details")
                progressBar.progress = 60f
                progressText.setText("3/5")



            }
        }
/*//        confirmAndProceed2.setOnClickListener {
//
//        }
//
//        confirmAndProceed3.setOnClickListener {
//        }*/

        no_med1.setOnClickListener {
            med2LL.visibility = View.GONE
            isFirstDiseaseSuffered = false
            handleLayoutClick("no_med1")
        }
        yes_med1.setOnClickListener {
            med2LL.visibility = View.VISIBLE
            isFirstDiseaseSuffered = true
            handleLayoutClick("yes_med1")
        }
        no_med.setOnClickListener {
            med1LL.visibility = View.GONE
            isSecondDiseaseSuffered = false
            handleLayoutClick("no_med")
        }
        yes_med.setOnClickListener {
            med1LL.visibility = View.VISIBLE
            isSecondDiseaseSuffered = true
            handleLayoutClick("yes_med")
        }
    }

    private fun handleLayoutClick(clickedLayout: String) {
        when (clickedLayout) {
            "no_med1" -> {

                updateLayoutState(no_med1, yes_med1)
            }
            "yes_med1" -> {

                updateLayoutState(yes_med1, no_med1)
            }
            "no_med" -> {

                updateLayoutState(no_med, yes_med)
            }
            "yes_med" -> {

                updateLayoutState(yes_med, no_med)
            }
        }
    }

    private fun updateLayoutState(selected: LinearLayout, deselected: LinearLayout) {
        selected.setBackgroundResource(R.drawable.bg_curve_3dp_clicked)
        deselected.setBackgroundResource(R.drawable.bg_curve_3dp_plain_stroke)
    }

    private fun selectGenderLayout(selectedLayout: LinearLayout) {

        male_LL.setBackgroundResource(R.drawable.gender_default)
        female_LL.setBackgroundResource(R.drawable.gender_default)
        other_LL.setBackgroundResource(R.drawable.gender_default)


        selectedLayout.setBackgroundResource(R.drawable.gender_selected)

    }

    private fun selectGenderLayoutB(selectedLayout: LinearLayout) {

        male_LLB.setBackgroundResource(R.drawable.gender_default)
        female_LLB.setBackgroundResource(R.drawable.gender_default)
        other_LLB.setBackgroundResource(R.drawable.gender_default)


        selectedLayout.setBackgroundResource(R.drawable.gender_selected)
    }
    private fun GetPropserDetails(quoteId: String?) {
        if (!APIClient.isNetworkAvailable(this@ProposalDetailsTermLife)) {
            Toast.makeText(this@ProposalDetailsTermLife, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetPropserDetails", "Request : $quoteId")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getDocTypeKycTLMainData(quoteId.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {
            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: retrofit2.Response<VerifyDocTypeKycTLMainData?>
            ) {
                Log.d("GetPropserDetails", "Server error:" + response.raw())
                if (response.isSuccessful) {
                    val main: VerifyDocTypeKycTLMainData? = response.body()
                    main?.let {
                        if (main.statusCode == 200) {
                            val docTypee = main.data!!.docDetail!!.docType
                            val DOB = main.data!!.docDetail!!.dob

                            if (docTypee.equals("pan", ignoreCase = true)) {
                                documentsKyc.selectItemByIndex(0)
                            } else {
                                documentsKyc.selectItemByIndex(1)
                            }

                            if (main.data!!.docDetail!!.docType.equals("pan")) {
                                documentsKyc.visibility = View.VISIBLE
                                aadharCardNumber.visibility = View.GONE
                                panNumberET.setText(main.data!!.docDetail!!.docNumber.toString())
                            } else {
                                panNumberET.visibility = View.GONE
                                aadharCardNumber.visibility = View.VISIBLE
                                aadharCardNumber.setText(main.data!!.docDetail!!.docNumber.toString())
                            }

                            fullNameTxtEditText.setText(main.data!!.perposerDetail!!.fullName.toString())
                            flatNoFloorTxtInputEditText.setText(main.data!!.addressDetail!!.houseNo.toString())
                            areaLocalityTxtInputEditText.setText(main.data!!.addressDetail!!.street.toString())
                            landMarkTxtInputEditText.setText(main.data!!.addressDetail!!.landmark.toString())
                            pincodetxtinputEditText.setText(main.data!!.addressDetail!!.pincode.toString())
                            cityTxtipEditText.setText(main.data!!.addressDetail!!.city.toString())
                            stateTxtipEditText.setText(main.data!!.addressDetail!!.state.toString())
                            emailID.setText(main.data!!.contactDetail!!.email.toString())
                            phoneNumber.setText(main.data!!.contactDetail!!.mobileNumber.toString())

                            val gender = main.data!!.perposerDetail?.gender
                            when (gender) {
                                "M" -> selectGenderLayout(male_LL)
                                "Y" -> selectGenderLayout(female_LL)
                                else -> selectGenderLayout(other_LL)
                            }


                            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            val date: Date? = inputFormat.parse(DOB)
                            val formattedDate: String = if (date != null) outputFormat.format(date) else ""
                            dobTIET.setText(formattedDate)
                            checkAndSetKycStatus()
                            checkAndUpdateUI()
                        }
                    }
                } else {
                    Log.d("GetPropserDetails", "Server error: ${response.code()} - ${response.message()}" + "~~" + response.raw())
                    Toast.makeText(this@ProposalDetailsTermLife, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.e("GetPropserDetails", "Failed to get user details", t)
                Toast.makeText(this@ProposalDetailsTermLife, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun GetAdditionalDetails(quoteId: String) {
        if (!APIClient.isNetworkAvailable(this@ProposalDetailsTermLife)) {
            Toast.makeText(this@ProposalDetailsTermLife, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetPropserDetails", "Request : $quoteId")
        val call: Call<GetTLAdditionalDetailMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getAdditionalDetails(quoteId.toString())

        call?.enqueue(object : Callback<GetTLAdditionalDetailMainData?> {
            override fun onResponse(
                call: Call<GetTLAdditionalDetailMainData?>,
                response: retrofit2.Response<GetTLAdditionalDetailMainData?>
            ) {
                Log.d("GetPropserDetails", "Server error:" + response.raw())
                if (response.isSuccessful) {
                    val main: GetTLAdditionalDetailMainData? = response.body()
                    main?.let {

                        val maxValue = main.data!!.additionalDetail!!.annuallIncome!!.max.toString()
                        val minValue = main.data!!.additionalDetail!!.annuallIncome!!.min.toString()

                        val matchedIncomeRange = getMatchingIncomeRange(maxValue, minValue, AnnualIncomeList)

                        matchedIncomeRange?.let {
                            AnnualIncomeSpinner.setText(it)
                        }


                        val occupationValue = main.data!!.additionalDetail!!.occupation.toString().lowercase()
                        val mappedOccupation = apiOccupationMap[occupationValue] ?: occupationValue

                        OccupationSpinner.setText(mappedOccupation)

                        val educationValue = main.data!!.additionalDetail!!.qualification.toString().lowercase()
                        val mappedEducation = apiEducationMap[educationValue] ?: educationValue

                        EducationSpinner.setText(mappedEducation)
                    }
                } else {
                    Log.d("GetPropserDetails", "Server error: ${response.code()} - ${response.message()}" + "~~" + response.raw())
                    Toast.makeText(this@ProposalDetailsTermLife, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetTLAdditionalDetailMainData?>?, t: Throwable) {
                Log.e("GetPropserDetails", "Failed to get user details", t)
                Toast.makeText(this@ProposalDetailsTermLife, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun getMatchingIncomeRange(maxValue: String, minValue: String, incomeList: List<String>): String? {
        val max = maxValue.replace("Lac", "").replace("+", "").toDoubleOrNull() ?: return null
        val min = minValue.replace("Lac", "").replace("+", "").toDoubleOrNull() ?: return null

        for (incomeRange in incomeList) {
            when {
                incomeRange.contains("Lac+") -> {
                    val value = incomeRange.replace("Lac+", "").trim().toDoubleOrNull()
                    if (value != null && max >= value) {
                        return incomeRange
                    }
                }
                incomeRange.contains("to") -> {
                    val (start, end) = incomeRange.split("to").map { it.trim().replace("Lac", "").toDouble() }
                    if (min >= start && max <= end) {
                        return incomeRange
                    }
                }
                incomeRange.contains("Less than") -> {
                    val value = incomeRange.replace("Less than", "").replace("Lac", "").trim().toDoubleOrNull()
                    if (value != null && max < value) {
                        return incomeRange
                    }
                }
            }
        }
        return null
    }
    private fun setupDocumentSpinner() {
        documentsKyc.setItems(KYC_Type)
        panNumberLL.visibility = View.VISIBLE
        aadharCardLL.visibility = View.GONE
        documentsKyc.selectItemByIndex(0)

        documentsKyc.setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
            when (newItem) {
                "PAN Card" -> {
                    panNumberLL.visibility = View.VISIBLE
                    aadharCardLL.visibility = View.GONE
                }
                "Aadhar Card" -> {
                    panNumberLL.visibility = View.GONE
                    aadharCardLL.visibility = View.VISIBLE
                }
            }
            checkAndSetKycStatus()
        }
    }
    private fun isValidFullName(fullName: String): Boolean {
        val namePattern = Regex("^[a-zA-Z\\s]*$")
        return fullName.matches(namePattern)
    }

    private fun setupTextWatchers() {
        fullNameTxtEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val fullName = s.toString().trim()

                val canProceed = isValidFullName(fullName) && fullName.length >= 3

                if (fullName.isEmpty() || fullName.length < 3 || !isValidFullName(fullName)) {
                    fullNameTxtEditText.error = "Invalid full name. Only letters and spaces are allowed, and it must be at least 3 characters long."
                } else {
                    fullNameTxtEditText.error = null
                }

                if (canProceed) {
                    confirmAndProceed1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
                    confirmAndProccedTV1.setTextColor(Color.BLACK)
                    drawableTV1.setImageResource(R.drawable.arrow_right_direction)
                } else {
                    confirmAndProceed1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
                    confirmAndProccedTV1.setTextColor(resources.getColor(R.color.verify_def_grey))
                    drawableTV1.setImageResource(R.drawable.arrow_right_direction_grey)
                }
            }
        })




        fullNameTxtEditText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source?.filter { it.isLetter() || it.isWhitespace() }
        })


        panNumberET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val panNumber = it.toString().trim().toUpperCase()

                    if (panNumberET.text.toString() != panNumber) {
                        panNumberET.setText(panNumber)
                        panNumberET.setSelection(panNumber.length)
                    }

                    if (!isValidPanNumber(panNumber)) {
                        panNumberET.error = "Invalid PAN Card number"
                    } else {
                        panNumberET.error = null
                    }
                    checkAndSetKycStatus()
                }
            }
        })

        aadharCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val aadharNumber = s.toString().trim()

                if (!isValidAadharNumber(aadharNumber)) {
                    aadharCardNumber.error = "Invalid Aadhar Card number"
                } else {
                    aadharCardNumber.error = null
                }
                checkAndSetKycStatus()
            }
        })

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkAndUpdateUI()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        pincodetxtinputEditText.addTextChangedListener(textWatcher)
        cityTxtipEditText.addTextChangedListener(textWatcher)
        stateTxtipEditText.addTextChangedListener(textWatcher)
        flatNoFloorTxtInputEditText.addTextChangedListener(textWatcher)
        areaLocalityTxtInputEditText.addTextChangedListener(textWatcher)
        landMarkTxtInputEditText.addTextChangedListener(textWatcher)

        phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateUI()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        emailID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateUI()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateUI() {
//        if (validateData()) {
//            confirmAndProceed3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
//            confirmAndProccedTV3.setTextColor(Color.BLACK)
//            drawableTV3.setImageResource(R.drawable.arrow_right_direction)
//        } else {
//            confirmAndProceed3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
//            confirmAndProccedTV3.setTextColor(resources.getColor(R.color.verify_def_grey))
//            drawableTV3.setImageResource(R.drawable.arrow_right_direction_grey)
//        }
    }
    private fun validateData(): Boolean {
        val phoneNumberText = phoneNumber.text.toString().trim()
        val emailIDText = emailID.text.toString().trim()
        val pincode = pincodetxtinputEditText.text.toString().trim()

        val isPhoneNumberValid = isValidPhoneNumber(phoneNumberText)
        val isEmailValid = isValidEmail(emailIDText)

        return  isPhoneNumberValid && isEmailValid
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex("^[6789]\\d{9}$"))
    }

    private fun checkAndSetKycStatus() {
        val selectedDocument = documentsKyc.text.toString()

        val isPanNumberFilled = !panNumberET.text.isNullOrBlank()
        val isAadharNumberFilled = !aadharCardNumber.text.isNullOrBlank()
        val isDobFilled = !dobTIET.text.isNullOrBlank()

        val isPanNumberValid = isValidPanNumber(panNumberET.text.toString())
        val isAadharNumberValid = isValidAadharNumber(aadharCardNumber.text.toString())

        val canProceed = when (selectedDocument) {
            "PAN Card" -> isPanNumberFilled && isPanNumberValid && isDobFilled
            "Aadhar Card" -> isAadharNumberFilled && isAadharNumberValid && isDobFilled
            else -> false
        }

        if (canProceed) {
            confirmAndProceed.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
            confirmAndProccedTV.setTextColor(Color.BLACK)
            saveTextView.setTextColor(resources.getColor(R.color.black))
            drawableTV.setImageResource(R.drawable.arrow_right_direction)
        } else {
            saveTextView.setTextColor(resources.getColor(R.color.shimmer_placeholder))
            confirmAndProceed.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
            confirmAndProccedTV.setTextColor(resources.getColor(R.color.verify_def_grey))
            drawableTV.setImageResource(R.drawable.arrow_right_direction_grey)

        }
    }


    private fun checkAndUpdateUI() {
        val pincode = pincodetxtinputEditText.text.toString()
        val isPincodeValid = pincode.length == 6

        val allDataEntered = listOf(
            pincodetxtinputEditText.text,
            cityTxtipEditText.text,
            stateTxtipEditText.text,
            flatNoFloorTxtInputEditText.text,
            areaLocalityTxtInputEditText.text,
            landMarkTxtInputEditText.text
        ).all { it?.isNotEmpty() == true } && isPincodeValid

//        if (allDataEntered) {
//            confirmAndProceed2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffb534"))
//            confirmAndProccedTV2.setTextColor(Color.BLACK)
//            drawableTV2.setImageResource(R.drawable.arrow_right_direction)
//        } else {
//            confirmAndProceed2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fce3b9"))
//            confirmAndProccedTV2.setTextColor(resources.getColor(R.color.verify_def_grey))
//            drawableTV2.setImageResource(R.drawable.arrow_right_direction_grey)
//        }
    }


    private fun isValidPanNumber(pan: String): Boolean {
        val panPattern = Regex("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        return pan.matches(panPattern)
    }

    private fun isValidAadharNumber(aadhar: String): Boolean {
        val aadharPattern = Regex("\\d{12}")
        return aadhar.matches(aadharPattern)
    }
    private fun showDatePicker(dobTIET: TextInputEditText) {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val selectedDate = calendar.time

                if (isAgeValid(selectedDate)) {
                    dobTIET.setText(dateFormat.format(selectedDate))

                } else {

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

    private fun VerifyDocTypeKycTL(){
        checkAndSetKycStatus()
        val docType = if (documentsKyc.text == "PAN Card") "pan" else "aadhar"
        val docNumber = if (docType == "pan") {
            panNumberET.text.toString().trim()
        } else {
            aadharCardNumber.text.toString().trim()
        }

        if (docType == "pan" && !isValidPanNumber(docNumber)) {
            panNumberET.error = "Invalid PAN Card number"
            return
        } else if (docType == "aadhar" && !isValidAadharNumber(docNumber)) {
            aadharCardNumber.error = "Invalid Aadhar Card number"
            return
        }

        val dobString = dobTIET.text.toString().trim()
        val isoDob = convertToISOFormat(dobString)
        val json = JSONObject().apply {
            put("docDetail", JSONObject().apply {
                put("docType", docType)
                put("docNumber", docNumber)
                put("DOB", isoDob)
                put("docStatus", true)

            })
            put("quoteId", QuoteId)

        }

        Log.d("PostSummaryCartDetails", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).verifyDocTypeKycTL(json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {
            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: VerifyDocTypeKycTLMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostSummaryCartDetails", "onResponse 1: " + response.raw() + "~" + json.toString()+"~~~"+mains!!.data!!.quoteId)
                            Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()
//                            verifED_KYC.visibility=View.VISIBLE
                            verifED_KYC.visibility=View.VISIBLE
                            verify_KYC.visibility=View.GONE
//                            indicator1.visibility=View.INVISIBLE
//                            indicator2.visibility=View.VISIBLE

                            QuoteIdVerify_KYC=mains.data!!.quoteId
                        }
                    } catch (e: Exception) {
                        Log.d("PostSummaryCartDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalDetailsTermLife, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("PostSummaryCartDetails", "onResponse: "+e.localizedMessage.toString())
                    }
                } else {
                    Log.d("PostSummaryCartDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("PostSummaryCartDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun PatchPropserDetails(){

        Log.d("PatchPropserDetails", "PatchPropserDetails: ")
        val json = JSONObject().apply {

            put("memberType", "self")
            put("fullName", fullNameTxtEditText.text.toString().trim())
            put("gender", GenderString)

        }
        Log.d("PatchPropserDetails", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).patchPropserDetails(QuoteIdVerify_KYC.toString(),json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {
            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: VerifyDocTypeKycTLMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PatchPropserDetails", "onResponse 1: " + response.raw() + "~" + json.toString()+"~~~"+mains!!.data!!.quoteId)
                            Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()

                            proposerAddressDetails.visibility=View.VISIBLE
                            proposerNomineeKYC.visibility=View.GONE
//                            confirmAndProceed1.visibility=View.GONE
//                            confirmAndProceed2.visibility=View.VISIBLE
                            indicator1.visibility=View.VISIBLE
                            indicator2.visibility=View.INVISIBLE
                            descTV.setText("Next:Member details")
                            headerText.setText("Contact details")
                            progressBar.progress = 40f
                            progressText.setText("2/5")



                        }
                    } catch (e: Exception) {
                        Log.d("PatchPropserDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalDetailsTermLife, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("PatchPropserDetails", "onResponse: "+e.localizedMessage.toString())
                    }
                } else {
                    Log.d("PatchPropserDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("PatchPropserDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun PatchAddressDetails(){

        Log.d("PatchPropserDetails", "PatchPropserDetails: ")
        val json = JSONObject().apply {

            put("houseNo", flatNoFloorTxtInputEditText.text.toString().trim())
            put("street", areaLocalityTxtInputEditText.text.toString().trim())
            put("landmark", landMarkTxtInputEditText.text.toString().trim())
            put("pincode", pincodetxtinputEditText.text.toString().trim())
            put("city", cityTxtipEditText.text.toString().trim())
            put("state", stateTxtipEditText.text.toString().trim())
        }
//        {"houseNo":"502","street":"8th Cross","landmark":"Near Dmart","pincode":"760001","state":"Odisha","city":"BAM"}
        Log.d("PatchAddressDetails", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).patchAddressDetails(QuoteIdVerify_KYC.toString(),json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {
            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: VerifyDocTypeKycTLMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PatchPropserDetails", "onResponse 1: " + response.raw() + "~" + json.toString()+"~~~"+mains!!.data!!.quoteId)
                            Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()

                            contactDetails.visibility=View.VISIBLE
//                            confirmAndProceed2.visibility=View.GONE
//                            confirmAndProceed3.visibility=View.VISIBLE
                            proposerAddressDetails.visibility=View.GONE
//                            proposerAddressDetails.visibility=View.VISIBLE
//                            proposerNomineeKYC.visibility=View.GONE
//                            confirmAndProceed1.visibility=View.GONE
//                            confirmAndProceed2.visibility=View.VISIBLE
                            indicator1.visibility=View.VISIBLE
                            indicator2.visibility=View.VISIBLE
//                            descTV.setText("Next:Member details")
//                            progressBar.progress = 40f
//                            progressText.setText("2/5")
//


                        }
                    } catch (e: Exception) {
                        Log.d("PatchPropserDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalDetailsTermLife, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("PatchPropserDetails", "onResponse: "+e.localizedMessage.toString())
                    }
                } else {
                    Log.d("PatchPropserDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("PatchPropserDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun patchContactDetails(){

        Log.d("PatchPropserDetails", "PatchPropserDetails: ")
        val json = JSONObject().apply {

            put("email", emailID.text.toString().trim())
            put("mobileNumber", phoneNumber.text.toString().trim())

        }

//        {"email":"shreebhargav@yahoo.com","mobileNumber":"7845333689"}
        Log.d("patchContactDetails", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).patchContactDetails(QuoteIdVerify_KYC.toString(),json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {
            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: VerifyDocTypeKycTLMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("patchContactDetails", "onResponse 1: " + response.raw() + "~" + json.toString()+"~~~"+mains!!.data!!.quoteId)
                            Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()

                            contactDetails.visibility=View.GONE
//                            confirmAndProceed3.visibility=View.GONE
//                            confirmAndProceed4.visibility=View.VISIBLE

                            basicDetails.visibility=View.VISIBLE
                            indicator1.visibility=View.VISIBLE
                            indicator2.visibility=View.INVISIBLE
                            headerText.setText("Contact details")
                            descTV.setText("Next:Medical details")
                            headerText.setText("Member details")
                            progressBar.progress = 60f
                            progressText.setText("3/5")



                        }
                    } catch (e: Exception) {
                        Log.d("patchContactDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalDetailsTermLife, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("patchContactDetails", "onResponse: "+e.localizedMessage.toString())
                    }
                } else {
                    Log.d("patchContactDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("patchContactDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun patchMemberDetails(){
        val dobString = dobBasicDetails.text.toString().trim()
        val isoDob = convertToISOFormat(dobString)
        Log.d("PatchPropserDetails", "PatchPropserDetails: ")
        val json = JSONObject().apply {

            put("memberType", "self")
            put("fullName", fullNameBasicDetails.text.toString().trim())
            put("gender", GenderStringBD)
            put("DOB", isoDob)

        }

        Log.d("patchContactDetails", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).patchMemberDetails(QuoteIdVerify_KYC.toString(),json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {
            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: VerifyDocTypeKycTLMainData? = response.body()

                    if (response.isSuccessful) {
                        Log.d("patchContactDetails", "onResponse 1: " + response.raw() + "~" + json.toString()+"~~~"+mains!!.data!!.quoteId)
                        Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()

                        contactDetails.visibility=View.GONE
                        basicDetails.visibility=View.GONE
                        additionalDetails.visibility=View.VISIBLE
                        indicator1.visibility=View.VISIBLE
                        indicator2.visibility=View.VISIBLE
                        progressBar.progress = 60f
                        progressText.setText("3/5")

//                        MartialStatusSpinner.setText(mains.data!!.additionalDetail!!.maritalStatus.toString())

                    }

                    Log.d("patchContactDetails", "onResponse 2: " + response.raw() + "~" + json.toString())


                } else {
                    Log.d("patchContactDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("patchContactDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun PatchAdditionalDetails(){

        val apiOccupationMap = mapOf(
            "Salaried" to "salaried",
            "Self Employed / Business" to "self_employed",
            "Housewife" to "house_wife"

        )

        val apiEducationMap = mapOf(
            "Graduate and Above" to "graduate_and_above",
            "Diploma" to "diploma",
            "12th Pass" to "12th_pass",
            "10th Pass" to "10th_pass",
            "Below 10th Pass" to "below_10th_pass",
            "Illiterate" to "illiterate"
        )

        val occupationValue = OccupationSpinner.text.toString().trim()
        val qualificationValue = EducationSpinner.text.toString().trim()

        val apiOccupationValue = apiOccupationMap[occupationValue] ?: ""
        val apiQualificationValue = apiEducationMap[qualificationValue] ?: ""

        Log.d("occupationValue", "PatchAdditionalDetails: "+qualificationValue+"~~~"+occupationValue)

        val selectedIncome = AnnualIncomeSpinner.text.toString().trim()
        val incomeRange = AnnualIncomeMap[selectedIncome]

        val maxValue = incomeRange?.second ?: 0
        val minValue = incomeRange?.first ?: 0

        val json = JSONObject().apply {
            put("occupation", apiOccupationValue)
            put("qualification", apiQualificationValue)
            put("maritalStatus", MartialStatusSpinner.text.toString().trim().toLowerCase())
            put("annuallIncome", JSONObject().apply {
                put("memberType", "self")
                put("max", maxValue.toString())
                put("min", minValue.toString())
                put("docStatus", true)
            })
        }


        Log.d("PatchAdditionalDetails", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).patchPropserAdditionalDtl(QuoteIdVerify_KYC.toString(),json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {

            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {

                    val mains: VerifyDocTypeKycTLMainData? = response.body()
                    Log.d("PatchAdditionalDetails", "Request URL: ${response.raw().request.url}")
                    Log.d("PatchAdditionalDetails", "Request Headers: ${response.raw().request.headers}")
                    Log.d("PatchAdditionalDetails", "Response Headers: ${response.raw().headers}")

                    Log.d("PatchAdditionalDetails", "onResponse 1: " + response.raw() + "~" + json.toString() + "~~~" + mains!!.data!!.quoteId)
                    Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    additionalDetails.visibility=View.GONE
                    medicalHistory.visibility=View.VISIBLE

                    indicator1.visibility=View.VISIBLE
                    indicator2.visibility=View.VISIBLE
                    descTV.setText("Next:Nominee details")
                    headerText.setText("Medical details")
                    progressBar.progress = 80F
                    progressText.setText("4/5")


                } else {

                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.d("PatchAdditionalDetails", "onResponse 3: Error body: $errorBody")
                    Log.d("PatchAdditionalDetails", "Request URL: ${response.raw().request.url}")
                    Log.d("PatchAdditionalDetails", "Request Headers: ${response.raw().request.headers}")
                    Log.d("PatchAdditionalDetails", "Response Headers: ${response.raw().headers}")


                    Log.d("PatchAdditionalDetails", "onResponse 3: URL: ${response.raw().request.url} ~ $json")
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("PatchAdditionalDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun PatchMedicalDetails(){

        val medicalHistoryArray = JSONArray().apply {

            put(JSONObject().apply {
//                put("diseaseName", "Is there any personal medical history of Diabetes / Cardiomyopathy / Any cancer / Stroke / Brain Tumour/ Poliomyelitis / Deafness / Muscular dystrophy / Blindness / Loss of Limbs / Organ transplant / Loss of speech/ Chronic kidney disease/ Kidney failure/ Chronic liver disease?")
                put("diseaseName", "aaaaaav")
                put("isSuffered", isFirstDiseaseSuffered)
//                put("description", if (isFirstDiseaseSuffered) med1.text.toString() else "")
                put("description", if (isFirstDiseaseSuffered) "aAaAaAaAaAaAaAaAv" else "")
            })


            put(JSONObject().apply {
//                put("diseaseName", "Is there any personal medical history of Heart Disease / Open Chest CABG / Open heart replacement or repair of Heart Valves / Heart Surgery / Angioplasty?")
                put("diseaseName", "aaaaaav")
                put("isSuffered", isSecondDiseaseSuffered)
//                put("description", if (isSecondDiseaseSuffered) med2.text.toString() else "")
                put("description", if (isSecondDiseaseSuffered) "aAaAaAaAaAaAaAaAv" else "")
            })
        }

        val json = JSONObject().apply {
            put("memberType", "self")
            put("medicalHistory", medicalHistoryArray)
        }
        Log.d("PatchMedicalDetails", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).patchMedicalDetail(QuoteIdVerify_KYC.toString(),json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {

            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {

                    val mains: VerifyDocTypeKycTLMainData? = response.body()
                    Log.d("PatchMedicalDetails", "Request URL: ${response.raw().request.url}")
                    Log.d("PatchMedicalDetails", "Request Headers: ${response.raw().request.headers}")
                    Log.d("PatchMedicalDetails", "Response Headers: ${response.raw().headers}")

                    Log.d("PatchMedicalDetails", "onResponse 1: " + response.raw() + "~" + json.toString() + "~~~" + mains!!.data!!.quoteId)
                    Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    additionalDetails.visibility=View.GONE
                    medicalHistory.visibility=View.GONE
                    nomineeDetails.visibility=View.VISIBLE
                    indicator1.visibility=View.VISIBLE
                    indicator2.visibility=View.VISIBLE
                    descTV.setText("Next: Summary")
                    headerText.setText("Nominee Details")
                    progressBar.progress = 100f
                    progressText.setText("5/5")


                } else {

                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.d("PatchMedicalDetails", "onResponse 3: Error body: $errorBody")
                    Log.d("PatchMedicalDetails", "Request URL: ${response.raw().request.url}")
                    Log.d("PatchMedicalDetails", "Request Headers: ${response.raw().request.headers}")
                    Log.d("PatchMedicalDetails", "Response Headers: ${response.raw().headers}")


                    Log.d("PatchMedicalDetails", "onResponse 3: URL: ${response.raw().request.url} ~ $json")
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("PatchMedicalDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun PatchPropserNomineeDetail(){

        val dobString = NomdobTIET.text.toString().trim()
        val isoDob = convertToISOFormat(dobString)
        val json = JSONObject().apply {
            put("fullName", RetalionSpinner.text.toString().trim().toLowerCase())
            put("relationShip", "self")
            put("DOB", isoDob)

        }
        Log.d("PatchPropserNomineeDetail", "Request JSON: $json")
        val call: Call<VerifyDocTypeKycTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@ProposalDetailsTermLife).create<ApiInterface>(
                ApiInterface::class.java
            ).patchPropserNomineeDetail(QuoteIdVerify_KYC.toString(),json.toString())

        call?.enqueue(object : Callback<VerifyDocTypeKycTLMainData?> {

            override fun onResponse(
                call: Call<VerifyDocTypeKycTLMainData?>,
                response: Response<VerifyDocTypeKycTLMainData?>
            ) {
                if (response.isSuccessful) {

                    val mains: VerifyDocTypeKycTLMainData? = response.body()
                    Log.d("PatchPropserNomineeDetail", "Request URL: ${response.raw().request.url}")
                    Log.d("PatchPropserNomineeDetail", "Request Headers: ${response.raw().request.headers}")
                    Log.d("PatchPropserNomineeDetail", "Response Headers: ${response.raw().headers}")

                    Log.d("PatchPropserNomineeDetail", "onResponse 1: " + response.raw() + "~" + json.toString() + "~~~" + mains!!.data!!.quoteId)
                    Toast.makeText(this@ProposalDetailsTermLife, response.body()!!.message, Toast.LENGTH_SHORT).show()
//                    additionalDetails.visibility=View.GONE
//                    medicalHistory.visibility=View.GONE
//                    nomineeDetails.visibility=View.VISIBLE
//                    indicator1.visibility=View.VISIBLE
//                    indicator2.visibility=View.VISIBLE
//                    descTV.setText("Next: Summary")
//                    headerText.setText("Nominee Details")
//                    progressBar.progress = 100f
//                    progressText.setText("5/5")


                } else {

                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.d("PatchPropserNomineeDetail", "onResponse 3: Error body: $errorBody")
                    Log.d("PatchPropserNomineeDetail", "Request URL: ${response.raw().request.url}")
                    Log.d("PatchPropserNomineeDetail", "Request Headers: ${response.raw().request.headers}")
                    Log.d("PatchPropserNomineeDetail", "Response Headers: ${response.raw().headers}")


                    Log.d("PatchPropserNomineeDetail", "onResponse 3: URL: ${response.raw().request.url} ~ $json")
                    Toast.makeText(this@ProposalDetailsTermLife, response.message(), Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<VerifyDocTypeKycTLMainData?>?, t: Throwable) {
                Log.d("PatchPropserNomineeDetail", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalDetailsTermLife, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
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