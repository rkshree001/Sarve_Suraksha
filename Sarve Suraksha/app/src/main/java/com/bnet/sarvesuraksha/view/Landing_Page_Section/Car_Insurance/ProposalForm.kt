package com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3010
import com.bnet.sarvesuraksha.model_api.GetCartDetailsMainData
import com.bnet.sarvesuraksha.model_api.GetPropserDetailsMainGet
import com.bnet.sarvesuraksha.model_api.GetQouteFormListDataMain
import com.bnet.sarvesuraksha.model_api.GetVehicleRcDetailMain
import com.bnet.sarvesuraksha.model_api.KYCVerificationDocDetailMainData
import com.bnet.sarvesuraksha.model_api.PostExternalDetailsMainData
import com.bnet.sarvesuraksha.model_api.PostNomineDataMainGet
import com.bnet.sarvesuraksha.model_api.PostNomineeDetailMainGet
import com.bnet.sarvesuraksha.model_api.PostVehcileIndMainData
import com.bnet.sarvesuraksha.model_api.PostVehicleBasicDetailMainData
import com.bnet.sarvesuraksha.model_api.PostVehicleInsDecMainData
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Bike_Insurance.BikeInsuranceQoutePlanForm
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Landing_Page.LandingPage
import com.bnet.savresuraksha.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.skydoves.powerspinner.PowerSpinnerView
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ProposalForm : AppCompatActivity() {

    private lateinit var companyLL: LinearLayout
    private lateinit var CNGLL: LinearLayout
    private lateinit var bankreferenceNo: LinearLayout
    private lateinit var privateLL: LinearLayout
    private lateinit var nomineeOwnerDetailLL: LinearLayout
    private lateinit var confirmAndProcvehicle: LinearLayout
    private lateinit var vehicleTintLL: LinearLayout
    private lateinit var vehicleTintIV: ImageView
    private lateinit var confirmAndProcvehTxt: TextView
    private lateinit var vehicleTintTxtView: TextView
    private lateinit var existingPolicyTxtView: TextView
    private lateinit var ownerTintll: LinearLayout
    private lateinit var ownerTintIV: ImageView
    private lateinit var privateTextView: TextView
    private lateinit var companyTextView: TextView
    private lateinit var radioGroupCNG: RadioGroup
    private lateinit var radioGroupCarLoan: RadioGroup
    private lateinit var radioYesCNg: RadioButton
    private lateinit var radioNoCNG: RadioButton
    private lateinit var radioYesCarLoan: RadioButton
    private lateinit var radioNoCarLoan: RadioButton
    private lateinit var existingPolicyIV: ImageView
    private lateinit var existingPolicyLL: LinearLayout
    private lateinit var ownerPropasalForm: LinearLayout
    private lateinit var vehiclePropasalForm: LinearLayout
    private lateinit var confirmAndPayLL: LinearLayout
    private lateinit var confirmAndProceedOwner: LinearLayout
    private lateinit var existingPolciy: LinearLayout
    private lateinit var idvValue: TextView
    private lateinit var ncbValue: TextView
    private lateinit var planType: TextView
    private lateinit var priceBreakup: TextView
    private lateinit var totalAmount: TextView
    private lateinit var documentsKyc: PowerSpinnerView
    private lateinit var IdvCover: TextView
    private lateinit var ncbBTM: TextView
    private lateinit var planTypeBTM: TextView
    private lateinit var ODPremium: TextView
    private lateinit var basicODPremium: TextView
    private lateinit var ODDiscount: TextView
    private lateinit var noClaimBonus: TextView
    private lateinit var totalPremium: TextView
    private lateinit var gstNumber: TextView
    private lateinit var gstPecentage: TextView
    private lateinit var payableAmount: TextView
    private lateinit var panNumberLL: LinearLayout
    private lateinit var proceedAndPayLL: LinearLayout
    private lateinit var proceedAndPayLLBtm: LinearLayout
    private lateinit var aadharCardLL: LinearLayout
    private lateinit var vefied_KYC: LinearLayout
    private lateinit var panNumberET: TextInputEditText
    private lateinit var dobTIET: TextInputEditText
    private lateinit var NomdobTIET: TextInputEditText
    private lateinit var aadharCardNumber: TextInputEditText
    private lateinit var verify_KYC: LinearLayout
    private lateinit var carOwnedType: LinearLayout
    private lateinit var DOBLLL: LinearLayout
    private lateinit var panCompanyTIET: TextInputEditText
    private lateinit var fullName: TextInputEditText
    private lateinit var mobileNumber: TextInputEditText
    private lateinit var emailID: TextInputEditText
    private lateinit var Nommdob: TextInputLayout
    private lateinit var NomineefullName: TextInputEditText
    private lateinit var RegnNumTxtEditText: TextInputEditText
    private lateinit var EngineNumberTxtEditText: TextInputEditText
    private lateinit var ChassisNumberTxtEditText: TextInputEditText
    private lateinit var RegistrationDateTxtEditText: TextInputEditText
    private lateinit var ManufactureMonth: TextInputEditText
    private lateinit var ManufactureYear: TextInputEditText
    private lateinit var policyNumber: TextInputEditText
    private lateinit var thirdpartypolicyNumber: TextInputEditText
    private lateinit var plicyDamageExpiryDate: TextInputEditText
    private lateinit var thirdPartyExpirydate: TextInputEditText
    private lateinit var ownDamaqgeInsurer: PowerSpinnerView
    private lateinit var thirdPartyInsurer: PowerSpinnerView
    private lateinit var OD_LL: LinearLayout
    private lateinit var TP_LL: LinearLayout

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var declarationChecckBox: CheckBox
    private lateinit var goBack: LinearLayout
    private lateinit var closeIcon: LinearLayout


    private lateinit var saveTextView: TextView
    private lateinit var confirmAndProceedTxtView: TextView
    private lateinit var previousPolicyDetails: TextView
    private lateinit var CarName: TextView
    private lateinit var regNo: TextView
    private lateinit var modelNameAndType: TextView

    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    private var quoteId: String? = ""
    private var QuoteId: String? = ""
    private var vehicleNumber: String? = ""
    private var VehicleId: String? = ""
    private var vehicleID: String? = ""
    private var nomineePropserID: String? = ""

    private var fullNameStr: String? = ""
    private var mobileNumberStr: String? = ""
    private var emailIDStr: String? = ""
    private var _idNominee: String? = ""
    private var _IDDECLA: String? = ""
    private var QuoteId_nominee: String? = ""
    private var _id_VehilceOD: String? = ""

    val KYC_Type = listOf("PAN Card", "Aadhar Card")

    private lateinit var RetalionSpinner: PowerSpinnerView

    val RelationList = listOf("Father", "Mother","Brother","Sister","Son")

    private var isCompanySectionVisible = false


    private lateinit var ODPremiumP: TextView
    private lateinit var basicODPremiumP: TextView
    private lateinit var ODDiscountp: TextView
    private lateinit var noClaimBonusp: TextView
    private lateinit var totalPremiump: TextView
    private lateinit var gstPecentagep: TextView
    private lateinit var payableAmountp: TextView
    private lateinit var IdvCoverp: TextView
    private lateinit var ncbP: TextView
    private lateinit var planTypep: TextView



    private lateinit var propsalSummartLL: LinearLayout
    private lateinit var propsalLL: LinearLayout
    private lateinit var vehicleOwnerDetailsLL: LinearLayout
    private lateinit var vehicleDetailsLL: LinearLayout
    private lateinit var existingPolciyLL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proposal_form)
        quoteId = intent.getStringExtra("quoteId")
        QuoteId = intent.getStringExtra("QuoteId")
        vehicleNumber = intent.getStringExtra("vehicleNumber")
        VehicleId = intent.getStringExtra("VehicleId")

        initializeViews()
        setClickListeners()
        setupDocumentSpinner()
        GetSummaryDetails(quoteId)
        getAllQouteListTemp(quoteId)
        GetPropserDetails(quoteId)
        PostRcDetails(vehicleNumber.toString())



    }

    private fun initializeViews() {
        ODPremiumP = findViewById(R.id.ODPremiumP)
        basicODPremiumP = findViewById(R.id.basicODPremiump)
        ODDiscountp = findViewById(R.id.ODDiscountp)
        noClaimBonusp = findViewById(R.id.noClaimBonusp)
        totalPremiump = findViewById(R.id.totalPremiump)
        gstPecentagep = findViewById(R.id.gstPecentagep)
        payableAmountp = findViewById(R.id.payableAmountp)
        IdvCoverp = findViewById(R.id.IdvCoverp)
        ncbP = findViewById(R.id.ncbP)
        planTypep = findViewById(R.id.planTypep)
        vehicleOwnerDetailsLL = findViewById(R.id.vehicleOwnerDetailsLL)
        vehicleDetailsLL = findViewById(R.id.vehicleDetailsLL)
        existingPolciyLL = findViewById(R.id.existingPolciyLL)
        declarationChecckBox = findViewById(R.id.declarationChecckBox)
        goBack = findViewById(R.id.goBack)



        companyLL = findViewById(R.id.companyLL)
        privateLL = findViewById(R.id.privateLL)
        privateTextView = findViewById(R.id.privateTextView)
        companyTextView = findViewById(R.id.companyTextView)
        nomineeOwnerDetailLL = findViewById(R.id.nomineeOwnerDetailLL)
        CNGLL = findViewById(R.id.CNGLL)
        bankreferenceNo = findViewById(R.id.bankreferenceNo)
        radioGroupCNG = findViewById(R.id.radioGroupCNG)
        radioYesCNg = findViewById(R.id.radioYesCNg)
        radioNoCNG = findViewById(R.id.radioNoCNG)
        radioGroupCarLoan = findViewById(R.id.radioGroupCarLoan)
        radioYesCarLoan = findViewById(R.id.radioYesCarLoan)
        radioNoCarLoan = findViewById(R.id.radioNoCarLoan)

        confirmAndProcvehicle = findViewById(R.id.confirmAndProcvehicle)
        confirmAndProcvehTxt = findViewById(R.id.confirmAndProcvehTxt)

        ownerTintll = findViewById(R.id.ownerTintll)
        vehicleTintLL = findViewById(R.id.vehicleTintLL)
        ownerTintIV = findViewById(R.id.ownerTintIV)
        vehicleTintTxtView = findViewById(R.id.vehicleTintTxtView)
        vehicleTintIV = findViewById(R.id.vehicleTintIV)
        existingPolicyTxtView = findViewById(R.id.existingPolicyTxtView)
        existingPolicyIV = findViewById(R.id.existingPolicyIV)
        existingPolicyLL = findViewById(R.id.existingPolicyLL)
        ownerPropasalForm = findViewById(R.id.ownerPropasalForm)
        vehiclePropasalForm = findViewById(R.id.vehiclePropasalForm)
        confirmAndPayLL = findViewById(R.id.confirmAndPayLL)
        confirmAndProceedOwner = findViewById(R.id.confirmAndProceedOwner)
        existingPolciy = findViewById(R.id.existingPolciy)
        idvValue = findViewById(R.id.idvValue)
        ncbValue = findViewById(R.id.ncbValue)
        planType = findViewById(R.id.planType)
        priceBreakup = findViewById(R.id.priceBreakup)
        totalAmount = findViewById(R.id.totalAmount)
        documentsKyc = findViewById(R.id.documentsKyc)
        panNumberLL = findViewById(R.id.panNumberLL)
        aadharCardLL = findViewById(R.id.aadharCardLL)
        panNumberET = findViewById(R.id.panNumberET)
        aadharCardNumber = findViewById(R.id.aadharCardNumber)
        verify_KYC = findViewById(R.id.verify_KYC)
        dobTIET = findViewById(R.id.dobTIET)
        NomdobTIET = findViewById(R.id.NomdobTIET)
        panCompanyTIET = findViewById(R.id.panCompanyTIET)
        fullName = findViewById(R.id.fullName)
        mobileNumber = findViewById(R.id.mobileNumber)
        emailID = findViewById(R.id.emailID)
        RetalionSpinner = findViewById(R.id.RetalionSpinner)
        vefied_KYC = findViewById(R.id.vefied_KYC)
        carOwnedType = findViewById(R.id.carOwnedType)
        Nommdob = findViewById(R.id.Nommdob)
        DOBLLL = findViewById(R.id.DOBLLL)
        NomineefullName = findViewById(R.id.NomineefullName)
        saveTextView = findViewById(R.id.saveTextView)
        confirmAndProceedTxtView = findViewById(R.id.confirmAndProceedTxtView)
        CarName = findViewById(R.id.CarName)
        regNo = findViewById(R.id.regNo)
        modelNameAndType = findViewById(R.id.modelNameAndType)
        RegnNumTxtEditText = findViewById(R.id.RegnNumTxtEditText)
        EngineNumberTxtEditText = findViewById(R.id.EngineNumberTxtEditText)
        ChassisNumberTxtEditText = findViewById(R.id.ChassisNumberTxtEditText)
        RegistrationDateTxtEditText = findViewById(R.id.RegistrationDateTxtEditText)
        ManufactureMonth = findViewById(R.id.ManufactureMonth)
        ManufactureYear = findViewById(R.id.ManufactureYear)
        policyNumber = findViewById(R.id.policyNumber)
        thirdpartypolicyNumber = findViewById(R.id.thirdpartypolicyNumber)
        ownDamaqgeInsurer = findViewById(R.id.ownDamaqgeInsurer)
        thirdPartyExpirydate = findViewById(R.id.thirdPartyExpirydate)
        thirdPartyInsurer = findViewById(R.id.thirdPartyInsurer)
        plicyDamageExpiryDate = findViewById(R.id.plicyDamageExpiryDate)
        OD_LL = findViewById(R.id.OD_LL)
        TP_LL = findViewById(R.id.TP_LL)
        propsalSummartLL = findViewById(R.id.propsalSummartLL)
        propsalLL = findViewById(R.id.propsalLL)


    }

    private fun setClickListeners() {

        companyTextView.setOnClickListener {
            toggleCompanySection()
        }

        privateTextView.setOnClickListener {
            togglePrivateSection()
        }

        radioGroupCNG.setOnCheckedChangeListener { _, checkedId ->
            CNGLL.visibility = if (checkedId == R.id.radioYesCNg) LinearLayout.VISIBLE else LinearLayout.GONE
        }

        radioGroupCarLoan.setOnCheckedChangeListener { _, checkedId ->
            bankreferenceNo.visibility = if (checkedId == R.id.radioYesCarLoan) LinearLayout.VISIBLE else LinearLayout.GONE
        }

        confirmAndProceedOwner.setOnClickListener {
            PostNomineeDetails()

        }

        confirmAndProcvehicle.setOnClickListener {
            PostExternalData()
            PostvehicleBasicDetail()

        }

        confirmAndPayLL.setOnClickListener {
            onPostInsuranceButtonClick()


        }

        priceBreakup.setOnClickListener {
            ShowSummaryBottomSheet()
        }

        dobTIET.setOnClickListener {
            showDatePicker(dobTIET)
        }
        NomdobTIET.setOnClickListener {
            showDatePicker(NomdobTIET)
        }

        RetalionSpinner.setItems(RelationList)

        proceedAndPayLL = findViewById(R.id.proceedAndPayLL)

        proceedAndPayLL.setOnClickListener {
            showDeclarationBottomSheet()
        }
        goBack.setOnClickListener {

            val intent = Intent(this@ProposalForm, CarInsuranceQoutePlanForm::class.java)
            intent.putExtra("quoteId",quoteId)
            intent.putExtra("QuoteId",QuoteId)
            intent.putExtra("vehicleNumber",vehicleNumber)
            intent.putExtra("VehicleId",VehicleId)

            startActivity(intent)
        }


        vehicleOwnerDetailsLL.setOnClickListener {

            propsalLL.visibility=View.VISIBLE
            propsalSummartLL.visibility=View.GONE
            existingPolciy.visibility=View.GONE
            ownerPropasalForm.visibility = LinearLayout.VISIBLE
            ownerTintIV.visibility = LinearLayout.VISIBLE
            vehicleTintLL.visibility = LinearLayout.VISIBLE
            vehicleTintIV.visibility = LinearLayout.GONE
            vehicleTintTxtView.visibility = LinearLayout.VISIBLE

            ownerTintll.setBackgroundResource(0)
            vehicleTintLL.setBackgroundResource(0)
            vehicleTintLL.setBackgroundResource(R.drawable.small_round_icon)
            ownerTintll.setBackgroundResource(R.drawable.small_round_icon)
//            ownerTintll.setBackground(null)
//            vehicleTintLL.setBackground(null)

//            vehiclePropasalForm.visibility = LinearLayout.GONE

//            vehicleTintTxtView.visibility = LinearLayout.GONE

        }

        vehicleDetailsLL.setOnClickListener {
            propsalLL.visibility=View.VISIBLE
            propsalSummartLL.visibility=View.GONE
            ownerPropasalForm.visibility = LinearLayout.GONE
            ownerTintIV.visibility = LinearLayout.GONE
            ownerTintll.setBackgroundResource(R.drawable.check_circle)
            vehiclePropasalForm.visibility = LinearLayout.VISIBLE
            vehicleTintIV.visibility = LinearLayout.GONE
            vehicleTintTxtView.visibility = LinearLayout.VISIBLE
            existingPolciy.visibility=View.GONE
            vehicleTintLL.setBackgroundResource(R.drawable.small_round_icon)
        }

        existingPolciyLL.setOnClickListener {

            propsalLL.visibility=View.VISIBLE
            propsalSummartLL.visibility=View.GONE
            vehiclePropasalForm.visibility = LinearLayout.GONE
            vehicleTintIV.visibility = LinearLayout.GONE
            vehicleTintTxtView.visibility = LinearLayout.GONE
            vehicleTintLL.setBackgroundResource(R.drawable.check_circle)
            existingPolciy.visibility = LinearLayout.VISIBLE

        }


    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this@ProposalForm, CarInsuranceQoutePlanForm::class.java)
        intent.putExtra("quoteId",quoteId)
        intent.putExtra("QuoteId",QuoteId)
        intent.putExtra("vehicleNumber",vehicleNumber)
        intent.putExtra("VehicleId",VehicleId)

        startActivity(intent)
    }
    private fun showDeclarationBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.declaration_bottom_sheet, null)

        setupBottomSheetView(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(false)

        bottomSheetDialog.show()


    }

    private fun setupBottomSheetView(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        checkBox1 = bottomSheetView.findViewById(R.id.checkBox1)
        checkBox2 = bottomSheetView.findViewById(R.id.checkBox2)
        closeIcon = bottomSheetView.findViewById(R.id.closeIcon)
        proceedAndPayLLBtm = bottomSheetView.findViewById(R.id.proceedAndPayLL)


        proceedAndPayLLBtm.setOnClickListener {
            PostDeclarationDetail()
            bottomSheetDialog.dismiss()
        }

        closeIcon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }


    private fun PostDeclarationDetail() {
        val json = JSONObject()

        val declareInformation = checkBox1.isChecked
        val kycConsent = checkBox2.isChecked

        json.put("declareInformation", declareInformation)
        json.put("KycConsent", kycConsent)
        json.put("vehicleSegment", "CAR")

        Log.d("PostDeonDetail", "Request JSON: $json")

        val call: Call<PostVehicleInsDecMainData?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java
            ).postDeclarationDetail(_IDDECLA.toString(), json.toString())

        call?.enqueue(object : Callback<PostVehicleInsDecMainData?> {
            override fun onResponse(
                call: Call<PostVehicleInsDecMainData?>,
                response: Response<PostVehicleInsDecMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostVehicleInsDecMainData? = response.body()
                    try {
                        if (response.isSuccessful) {

                            val intent = Intent(this@ProposalForm, LandingPage::class.java)
                            startActivity(intent)

                            Log.d("PostDeonDetail", "onResponse 1: " + response.raw() + "~" + json.toString())
                            Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostDeonDetail", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalForm, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostDeonDetail", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostVehicleInsDecMainData?>?, t: Throwable) {
                Log.d("PostDeonDetail", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalForm, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun checkAndSetConfirmButtonState() {
        val isNomineeNameFilled = !NomineefullName.text.isNullOrBlank()
        val isRelationFilled = !RetalionSpinner.text.isNullOrBlank()
        val isDobFilled = !NomdobTIET.text.isNullOrBlank()
        val isEmailFilled = !emailID.text.isNullOrBlank()

        if (isNomineeNameFilled && isRelationFilled && isDobFilled && isEmailFilled) {
            confirmAndProceedOwner.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#560FE5"))
            confirmAndProceedTxtView.setTextColor(resources.getColor(R.color.white))
        } else {
            confirmAndProceedOwner.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#cdd0df"))
            confirmAndProceedTxtView.setTextColor(resources.getColor(R.color.white))
        }
    }
    private fun getAllQouteListTemp(quoteId: String?) {

        if (!APIClient.isNetworkAvailable(this@ProposalForm)) {
            Toast.makeText(this@ProposalForm, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("getAllQouteListTemp", "Request : $quoteId")
        val call: Call<GetQouteFormListDataMain?>? =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getAllQouteListTemp(quoteId.toString())
        call?.enqueue(object : Callback<GetQouteFormListDataMain?> {
            override fun onResponse(
                call: Call<GetQouteFormListDataMain?>,
                response: retrofit2.Response<GetQouteFormListDataMain?>
            ) {
                if (response.isSuccessful) {
                    val main: GetQouteFormListDataMain? = response.body()
                    main?.let {

/*
                        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        val dateFormatOutput = SimpleDateFormat("yyyy")
                        val registrationDateStr= main?.data!!.vehicleDetail!!.registrationDate
*/

                        fullNameStr = main.data?.ownerDetail?.ownerName
                        mobileNumberStr = main.data?.ownerDetail?.mobileNumber
                        emailIDStr = main.data?.ownerDetail?.email

                        fullName.apply {
                            setText(fullNameStr)
                            isFocusable = false
                            isClickable = false
                            isFocusableInTouchMode = false
                        }

                        mobileNumber.apply {
                            setText(mobileNumberStr)
                            isFocusable = false
                            isClickable = false
                            isFocusableInTouchMode = false
                        }

                        emailID.apply {
                            setText(emailIDStr)
//                            isFocusable = false
//                            isClickable = false
//                            isFocusableInTouchMode = false
                        }



                        Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()

                        Log.d("getAllQouteListTemp", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")

                    }
                } else {
                    Log.d("getAllQouteListTemp", "Server error: ${response.code()} - ${response.message()}"+"~~"+response.raw())
                    Toast.makeText(this@ProposalForm, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetQouteFormListDataMain?>?, t: Throwable) {
                Log.e("getAllQouteListTemp", "Failed to get user details", t)
                Toast.makeText(this@ProposalForm, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun GetPropserDetails(quoteId: String?) {

        if (!APIClient.isNetworkAvailable(this@ProposalForm)) {
            Toast.makeText(this@ProposalForm, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("GetPropserDetails", "Request : $quoteId")
        val call: Call<GetPropserDetailsMainGet?>? =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getPropserDetails(quoteId.toString())
        call?.enqueue(object : Callback<GetPropserDetailsMainGet?> {
            override fun onResponse(
                call: Call<GetPropserDetailsMainGet?>,
                response: retrofit2.Response<GetPropserDetailsMainGet?>
            ) {
                if (response.isSuccessful) {
                    val main: GetPropserDetailsMainGet? = response.body()
                    main?.let {

                        if (main.statusCode == 200) {
                            vefied_KYC.visibility=View.VISIBLE
                            verify_KYC.visibility=View.GONE

                            carOwnedType.isFocusable = false
                            carOwnedType.isClickable = false

                            dobTIET.isClickable = false
                            dobTIET.isFocusable = false
                            dobTIET.isFocusableInTouchMode = false
                            dobTIET.setOnClickListener(null)

                            panNumberET.isClickable = false
                            panNumberET.isFocusable = false

                            dobTIET.isClickable = false
                            dobTIET.isFocusable = false

                            documentsKyc.isClickable = false
                            documentsKyc.isFocusable = false

                            aadharCardNumber.isClickable = false
                            aadharCardNumber.isFocusable = false

                            DOBLLL.isClickable = false
                            DOBLLL.isFocusable = false

                            nomineePropserID=main!!.data!!.id

                            if (main.data!!.docDetail!!.docType.equals("pan")) {
                                documentsKyc.visibility = View.VISIBLE
                                aadharCardNumber.visibility = View.GONE
                                panNumberET.setText(main.data!!.docDetail!!.docNumber.toString())
                            } else {
                                panNumberET.visibility = View.GONE
                                aadharCardNumber.visibility = View.VISIBLE
                                aadharCardNumber.setText(main.data!!.docDetail!!.docNumber.toString())
                            }


                            val dobString = main.data!!.docDetail!!.dob
                            val formattedDob = convertToDisplayFormat(dobString)
                            dobTIET.setText(formattedDob)

                            val docTypee = main!!.data!!.docDetail!!.docType
                            if (docTypee.equals("pan", ignoreCase = true)) {
                                documentsKyc.selectItemByIndex(0)
                            } else {
                                documentsKyc.selectItemByIndex(1)
                            }

                        } else {
                            Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                        Log.d("GetPropserDetails", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")

                    }
                } else {
                    Log.d("GetPropserDetails", "Server error: ${response.code()} - ${response.message()}" + "~~" + response.raw())
                    Toast.makeText(this@ProposalForm, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetPropserDetailsMainGet?>?, t: Throwable) {
                Log.e("GetPropserDetails", "Failed to get user details", t)
                Toast.makeText(this@ProposalForm, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkAndSetKycStatus() {

        val selectedDocument = documentsKyc.text.toString()

        val isPanNumberFilled = !panNumberET.text.isNullOrBlank()
        val isAadharNumberFilled = !aadharCardNumber.text.isNullOrBlank()


        val isPanNumberValid = isValidPanNumber(panNumberET.text.toString())
        val isAadharNumberValid = isValidAadharNumber(aadharCardNumber.text.toString())

        if ((selectedDocument == "PAN Card" && isPanNumberFilled && isPanNumberValid) ||
            (selectedDocument == "Aadhar Card" && isAadharNumberFilled && isAadharNumberValid)) {


            verify_KYC.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#560FE5"))
            saveTextView.setTextColor(resources.getColor(R.color.white))

        } else {

            verify_KYC.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#cdd0df"))
            saveTextView.setTextColor(resources.getColor(R.color.white))
        }



    }

    private fun convertToDisplayFormat(isoDate: String?): String? {
        return try {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")


            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())


            val date = inputFormat.parse(isoDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun setupDocumentSpinner() {
        documentsKyc.setItems(KYC_Type)

        panNumberLL.visibility = View.VISIBLE
        aadharCardLL.visibility = View.GONE


        documentsKyc.selectItemByIndex(0)


        documentsKyc.setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
            when (newItem) {
                "PAN Card" -> {
                    checkAndSetKycStatus()
                    panNumberLL.visibility = View.VISIBLE
                    aadharCardLL.visibility = View.GONE

                }
                "Aadhar Card" -> {
                    checkAndSetKycStatus()
                    panNumberLL.visibility = View.GONE
                    aadharCardLL.visibility = View.VISIBLE
                }

            }
        }



        panNumberET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val panNumber = it.toString().trim()


                    val uppercasePanNumber = panNumber.toUpperCase()

                    if (panNumber != uppercasePanNumber) {
                        panNumberET.setText(uppercasePanNumber)
                        panNumberET.setSelection(uppercasePanNumber.length)
                    }

                    if (!isValidPanNumber(uppercasePanNumber)) {
                        panNumberET.error = "Invalid PAN Card number"
                    } else {
                        checkAndSetKycStatus()
                        panNumberET.error = null
                    }
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
                    checkAndSetKycStatus()
                    aadharCardNumber.error = null
                }
            }
        })

        NomineefullName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val filtered = it.toString().filter { char ->
                        char.isLetter() || char.isWhitespace()
                    }

                    if (it.toString() != filtered) {
                        NomineefullName.setText(filtered)
                        NomineefullName.setSelection(filtered.length)
                        Toast.makeText(this@ProposalForm, "Only alphabets are allowed", Toast.LENGTH_SHORT).show()
                    }
                }

                checkAndSetConfirmButtonState()
            }
        })

        NomdobTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkAndSetConfirmButtonState()
            }
        })

        emailID.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkAndSetConfirmButtonState()
            }
        })

        RetalionSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, _ ->
            checkAndSetConfirmButtonState()
        }

        verify_KYC.setOnClickListener {
            VerifyKyc()
        }

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

                            vehicleID=main!!.data!!.vehicleDetail!!.id

                            Log.d("vehicleIDWSef", "onResponse: "+vehicleID)


                            val dateFormatInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                            val dateFormatOutput = SimpleDateFormat("yyyy")
                            val registrationDateStr= main?.data!!.vehicleDetail!!.registrationDate

                            val year = try {
                                val date = dateFormatInput.parse(registrationDateStr)
                                date?.let { dateFormatOutput.format(it) } ?: "Unknown Year"
                            } catch (e: ParseException) {
                                "Unknown Year"
                            }
                            val vehcileType=main?.data!!.vehicleDetail!!.makerName+""+main?.data!!.vehicleDetail!!.modelName
                            val vehcileTypeVal=main?.data!!.vehicleDetail!!.variantName+"-"+main?.data!!.vehicleDetail!!.fuelType+"-"+year


                            Log.d("ResponseRawa", "onResponse 1: "+response.raw()+"~"+json.toString()+vehicleID)

                        }
                    } catch (e: Exception) {

                    }
                } else {
                    Log.d("ResponseRawa", "onResponse 3: "+response.raw()+"~"+json.toString())
                }
            }

            override fun onFailure(call: Call<GetVehicleRcDetailMain?>, t: Throwable) {
                Log.d("ResponseRawa", "onResponse: "+t.localizedMessage+"~"+json.toString())

            }


        })
    }


    private fun VerifyKyc() {
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


        val companyInfo = if (isCompanySectionVisible) {
            panCompanyTIET.text.toString().trim()
        } else {
            ""
        }

        val json = JSONObject().apply {
            put("docDetail", JSONObject().apply {
                put("docNumber", docNumber)
                put("docType", docType)
                put("DOB", isoDob)
                put("DOI", "")
                put("docStatus", true)
                put("ownerType", if (isCompanySectionVisible) "company" else "private")
                put("loanDetail", JSONObject().apply {
                    put("isLoan", radioYesCarLoan.isChecked)
                    put("bankName", "")
                })
                put("CNGKitDetail", JSONObject().apply {
                    put("isInstalled", radioYesCNg.isChecked)
                    put("kitValue", 0)
                })
            })


            put("vehicleSegment", "CAR")
            put("quoteId", quoteId)
            put("companyInfo", companyInfo)


        }

        Log.d("VerifyKyc", "Request JSON: $json")

        val call: Call<KYCVerificationDocDetailMainData?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java
            ).verifyKyc(json.toString())
        call?.enqueue(object : Callback<KYCVerificationDocDetailMainData?> {
            override fun onResponse(
                call: Call<KYCVerificationDocDetailMainData?>,
                response: Response<KYCVerificationDocDetailMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: KYCVerificationDocDetailMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("VerifyKyc", "onResponse 1: " + response.raw() + "~" + json.toString())

                            vefied_KYC.visibility=View.VISIBLE
                            verify_KYC.visibility=View.GONE

                            carOwnedType.isFocusable = false
                            carOwnedType.isClickable = false

                            documentsKyc.isFocusable = false
                            documentsKyc.isClickable = false

                            panNumberET.isClickable = false
                            panNumberET.isFocusable = false

                            dobTIET.isClickable = false
                            dobTIET.isFocusable = false

                            Nommdob.isClickable = false
                            Nommdob.isFocusable = false

                            DOBLLL.isClickable = false
                            DOBLLL.isFocusable = false

                            nomineePropserID=main!!.data!!.id

                            Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("VerifyKyc", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalForm, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("VerifyKyc", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<KYCVerificationDocDetailMainData?>?, t: Throwable) {
                Log.d("VerifyKyc", "onResponse: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalForm, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun PostNomineeDetails() {
        val fullNameText = fullName.text.toString().trim()
        val mobileNumberText = mobileNumber.text.toString().trim()
        val emailText = emailID.text.toString().trim()

        if (emailText.isEmpty()) {
            Toast.makeText(this@ProposalForm, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(emailText)) {
            Toast.makeText(this@ProposalForm, "Invalid email address", Toast.LENGTH_SHORT).show()
            return
        }

        val json = JSONObject()
        json.put("ownerName", fullNameText)
        json.put("mobileNumber", mobileNumberText)
        json.put("email", emailText)
        json.put("vehicleSegment", "CAR")

        Log.d("PostNomineeDetails", "Request JSON: $json")

        val call: Call<PostNomineDataMainGet?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java
            ).postNomineeDetails(vehicleID.toString(), json.toString())
        call?.enqueue(object : Callback<PostNomineDataMainGet?> {
            override fun onResponse(
                call: Call<PostNomineDataMainGet?>,
                response: Response<PostNomineDataMainGet?>
            ) {
                if (response.isSuccessful) {
                    val main: PostNomineDataMainGet? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostNomineeDetails", "onResponse 1: " + response.raw() + "~" + json.toString())

                            PostvehicleNomineeDetail()
                            CarName.setText(main!!.data!!.vehicleDetail!!.makerName)
                            regNo.setText(main!!.data!!.vehicleBasicDetail!!.registrationNumber)

                            RegnNumTxtEditText.setText(main!!.data!!.vehicleBasicDetail!!.registrationNumber)
                            EngineNumberTxtEditText.setText(main!!.data!!.vehicleBasicDetail!!.engineNumber)
                            ChassisNumberTxtEditText.setText(main!!.data!!.vehicleBasicDetail!!.chassisNumber)

                            val formattedExpiryDate = formatDate(main!!.data!!.vehicleBasicDetail!!.registrationDate.toString())

                            RegistrationDateTxtEditText.setText(formattedExpiryDate)

                            ManufactureYear.setText(main!!.data!!.vehicleBasicDetail!!.manufacturingYear)

                            ManufactureYear.isFocusable = false
                            ManufactureYear.isClickable = false

                            ManufactureMonth.isFocusable = false
                            ManufactureMonth.isClickable = false

                            ChassisNumberTxtEditText.isFocusable = false
                            ChassisNumberTxtEditText.isClickable = false

                            RegnNumTxtEditText.isFocusable = false
                            RegnNumTxtEditText.isClickable = false

                            EngineNumberTxtEditText.isFocusable = false
                            EngineNumberTxtEditText.isClickable = false

                            RegistrationDateTxtEditText.isFocusable = false
                            RegistrationDateTxtEditText.isClickable = false


                            val monthNumber = main!!.data!!.vehicleBasicDetail!!.manufacturingMonth!!.toIntOrNull()

                            val monthNames = arrayOf(
                                "January", "February", "March", "April", "May", "June",
                                "July", "August", "September", "October", "November", "December"
                            )

                            monthNumber?.let {
                                if (it in 1..12) {
                                    ManufactureMonth.setText(monthNames[it - 1])
                                } else {

                                    ManufactureMonth.setText("Invalid Month")
                                }
                            } ?: run {

                                ManufactureMonth.setText("Unknown Month")
                            }
                            var hasOwnDamage = false
                            var hasThirdParty = false

                            main!!.data!!.insuranceDetail!!.forEach { insuranceDetail ->


                                if (insuranceDetail.insuranceType.equals("own_damage", ignoreCase = true)) {
                                    val formattedExpiryDate = formatDate(insuranceDetail.policyExpiryDate.toString())
                                    OD_LL.visibility = View.VISIBLE
                                    ownDamaqgeInsurer.setText(insuranceDetail.insurerName)
                                    policyNumber.setText(insuranceDetail.policyNumber)
                                    plicyDamageExpiryDate.setText(formattedExpiryDate)
                                    hasOwnDamage = true
                                } else if (insuranceDetail.insuranceType.equals("third_party", ignoreCase = true)) {
                                    val formattedExpiryDate = formatDate(insuranceDetail.policyExpiryDate.toString())
                                    TP_LL.visibility = View.VISIBLE
                                    thirdPartyInsurer.setText(insuranceDetail.insurerName)
                                    thirdpartypolicyNumber.setText(insuranceDetail.policyNumber)
                                    thirdPartyExpirydate.setText(formattedExpiryDate)
                                    hasThirdParty = true
                                }
                            }

                            if (!hasOwnDamage) {
                                OD_LL.visibility = View.GONE
                            }
                            if (!hasThirdParty) {
                                TP_LL.visibility = View.GONE
                            }

//                            if (main!!.data!!.insuranceDetail!!.get(0).policyExpiryDate.equals("own_damage")){
//                                OD_LL.visibility=View.VISIBLE
//                                TP_LL.visibility=View.GONE
//                                ownDamaqgeInsurer.setText(main!!.data!!.insuranceDetail!!.get(0).insurerName)
//                                policyNumber.setText(main!!.data!!.insuranceDetail!!.get(0).policyNumber)
//                                plicyDamageExpiryDate.setText(main!!.data!!.insuranceDetail!!.get(0).policyExpiryDate)
//
//                            }else if (main!!.data!!.insuranceDetail!!.get(0).policyExpiryDate.equals("third_party")){
//                                OD_LL.visibility=View.GONE
//                                TP_LL.visibility=View.VISIBLE
//                                thirdPartyInsurer.setText(main!!.data!!.insuranceDetail!!.get(0).insurerName)
//                                thirdpartypolicyNumber.setText(main!!.data!!.insuranceDetail!!.get(0).policyNumber)
//                                thirdPartyExpirydate.setText(main!!.data!!.insuranceDetail!!.get(0).policyExpiryDate)
//                            }

                            val variantname = main!!.data!!.vehicleDetail!!.variantName
                            val fuelType = main!!.data!!.vehicleDetail!!.fuelType
                            val regYear = main!!.data!!.vehicleBasicDetail!!.manufacturingYear

                            _id_VehilceOD=main!!.data!!.id

                            val truncatedVariantName = if (variantname!!.length > 5) {
                                variantname!!.substring(0, 5)
                            } else {
                                variantname
                            }

                            modelNameAndType.setText("$truncatedVariantName - $fuelType - $regYear")

                            Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostNomineeDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalForm, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostNomineeDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostNomineDataMainGet?>?, t: Throwable) {
                Log.d("PostNomineeDetails", "onResponse: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalForm, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun formatDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = inputFormat.parse(isoDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            isoDate
        }
    }
    private fun onPostInsuranceButtonClick() {
        val insuranceDetails = mutableListOf<JSONObject>()


        if (OD_LL.visibility == View.VISIBLE) {
            val ownDamageJson = JSONObject()
            ownDamageJson.put("insuranceType", "own_damage")
            ownDamageJson.put("insurerName", ownDamaqgeInsurer.text.toString().trim())
            ownDamageJson.put("policyNumber", policyNumber.text.toString().trim())
            val dobString = plicyDamageExpiryDate.text.toString().trim()
            val isoDob = convertToISOFormat(dobString)
            ownDamageJson.put("policyExpiryDate", isoDob)
            insuranceDetails.add(ownDamageJson)
        }


        if (TP_LL.visibility == View.VISIBLE) {
            val thirdPartyJson = JSONObject()
            thirdPartyJson.put("insuranceType", "third_party")
            thirdPartyJson.put("insurerName", thirdPartyInsurer.text.toString().trim())
            thirdPartyJson.put("policyNumber", thirdpartypolicyNumber.text.toString().trim())

            val dobString = thirdPartyExpirydate.text.toString().trim()
            val isoDob = convertToISOFormat(dobString)
            thirdPartyJson.put("policyExpiryDate", isoDob)
            insuranceDetails.add(thirdPartyJson)
        }


        if (insuranceDetails.isNotEmpty()) {
            PostVehicleInsuranceDetail(_idNominee.toString(), insuranceDetails)
        } else {
            Toast.makeText(this@ProposalForm, "No insurance details to post", Toast.LENGTH_SHORT).show()
        }


    }


    private fun PostVehicleInsuranceDetail(
        vehicleId: String?,
        insuranceDetails: List<JSONObject>
    ) {

//        [
//            {
//                "insuranceType": "own_damage",
//                "insurerName": "Tata Aig",
//                "policyNumber": "01710859910000",
//                "policyExpiryDate": "2027-01-13T00:00:00.000Z"
//            }
//        ]
        val jsonArray = JSONArray(insuranceDetails).toString()

        Log.d("LOGPostBrfTRan", "Resp : $jsonArray")
        val call: Call<PostVehcileIndMainData?> =
            MyApplicationPort3010.getRetrofitClient(this).create(ApiInterface::class.java)
                .postVehicleInsData(_idNominee.toString(), jsonArray)
        call.enqueue(object : Callback<PostVehcileIndMainData?> {
            override fun onResponse(
                call: Call<PostVehcileIndMainData?>,
                response: retrofit2.Response<PostVehcileIndMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostVehcileIndMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("ResponseRawa", "PostVehicleInsuranceDetail 1: ${response.raw()}~$jsonArray")

                            propsalLL.visibility=View.GONE
                            propsalSummartLL.visibility=View.VISIBLE

//                            val intent = Intent(this@ProposalForm, SummaryActivity::class.java)
//                            startActivity(intent)
                        }
                    } catch (e: Exception) {

                    }
                } else {

                    Log.d("ResponseRawa", "PostVehicleInsuranceDetail 3: ${response.raw()}~$jsonArray")
                }
            }

            override fun onFailure(call: Call<PostVehcileIndMainData?>, t: Throwable) {
                Log.d("ResponseRawa", "PostVehicleInsuranceDetail: ${t.localizedMessage}~$jsonArray")
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(Regex(emailRegex))
    }


    private fun PostvehicleNomineeDetail() {
        val json = JSONObject()


        val dobString = NomdobTIET.text.toString().trim()
        val isoDob = convertToISOFormat(dobString)


        json.put("relationship", RetalionSpinner.text.toString().toLowerCase())
        json.put("fullName", NomineefullName.text.toString())
        json.put("DOB", isoDob)
        json.put("email", NomdobTIET.text.toString())
        json.put("vehicleSegment", "CAR")
        json.put("proposerId", nomineePropserID)


        Log.d("PostcleNomineeDetail", "Request JSON: $json")

        val call: Call<PostNomineeDetailMainGet?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java
            ).postNomineeDetails1(nomineePropserID.toString(),json.toString())
        call?.enqueue(object : Callback<PostNomineeDetailMainGet?> {
            override fun onResponse(
                call: Call<PostNomineeDetailMainGet?>,
                response: Response<PostNomineeDetailMainGet?>
            ) {
                if (response.isSuccessful) {
                    val main: PostNomineeDetailMainGet? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostcleNomineeDetail", "onResponse 1: " + response.raw() + "~" + json.toString())
                            ownerPropasalForm.visibility = LinearLayout.GONE
                            ownerTintIV.visibility = LinearLayout.GONE
                            ownerTintll.setBackgroundResource(R.drawable.check_circle)
                            vehiclePropasalForm.visibility = LinearLayout.VISIBLE
                            vehicleTintIV.visibility = LinearLayout.VISIBLE
                            vehicleTintTxtView.visibility = LinearLayout.GONE

                            _idNominee=main!!.data!!.id
                            QuoteId_nominee=main!!.data!!.quoteId

                            Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostcleNomineeDetail", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalForm, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostcleNomineeDetail", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostNomineeDetailMainGet?>?, t: Throwable) {
                Log.d("PostcleNomineeDetail", "onResponse: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalForm, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun PostExternalData() {

        val json = JSONObject().apply {

            put("loanDetail", JSONObject().apply {
                put("isLoan", radioYesCarLoan.isChecked)
                put("bankName", "")
            })
            put("CNGKitDetail", JSONObject().apply {
                put("isInstalled", radioYesCNg.isChecked)
                put("kitValue", 0)
            })
            put("quoteId", QuoteId_nominee)
            put("vehicleSegment", "CAR")
            put("proposerId", _idNominee)

        }

        Log.d("PostExternalData", "Request JSON: $json")

        val call: Call<PostExternalDetailsMainData?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java
            ).postExternalData(nomineePropserID.toString(),json.toString())
        call?.enqueue(object : Callback<PostExternalDetailsMainData?> {
            override fun onResponse(
                call: Call<PostExternalDetailsMainData?>,
                response: Response<PostExternalDetailsMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostExternalDetailsMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostExternalData", "onResponse 1: " + response.raw() + "~" + json.toString()+"~~"+main!!.data!!.id)
                            ownerPropasalForm.visibility = LinearLayout.GONE
                            ownerTintIV.visibility = LinearLayout.GONE
                            ownerTintll.setBackgroundResource(R.drawable.check_circle)
                            vehiclePropasalForm.visibility = LinearLayout.VISIBLE
                            vehicleTintIV.visibility = LinearLayout.VISIBLE
                            vehicleTintTxtView.visibility = LinearLayout.GONE

                            _idNominee=main!!.data!!.id
                            _IDDECLA=main!!.data!!.id

                            Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostExternalData", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalForm, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostExternalData", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostExternalDetailsMainData?>?, t: Throwable) {
                Log.d("PostExternalData", "onResponse: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalForm, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun PostvehicleBasicDetail() {

        val json = JSONObject()


        val dobString = RegistrationDateTxtEditText.text.toString().trim()
        val isoDob = convertToISOFormat(dobString)
        val monthName = ManufactureMonth.text.toString()
        val monthMap = mapOf(
            "January" to 1,
            "February" to 2,
            "March" to 3,
            "April" to 4,
            "May" to 5,
            "June" to 6,
            "July" to 7,
            "August" to 8,
            "September" to 9,
            "October" to 10,
            "November" to 11,
            "December" to 12
        )

        val monthNumber = monthMap[monthName].toString()

        json.put("registrationNumber", RegnNumTxtEditText.text.toString())
        json.put("manufacturingYear", ManufactureYear.text.toString())
        json.put("registrationDate", isoDob)
        json.put("engineNumber", EngineNumberTxtEditText.text.toString())
        json.put("chassisNumber", ChassisNumberTxtEditText.text.toString())
        json.put("vehicleSegment", "CAR")
        if (monthNumber != null) {
            json.put("manufacturingMonth", monthNumber.toString())
        }

        Log.d("PostcleNomineeDetail", "Request JSON: $json")

        val call: Call<PostVehicleBasicDetailMainData?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java
            ).postvehicleBasicDetail(_id_VehilceOD.toString(),json.toString())
        call?.enqueue(object : Callback<PostVehicleBasicDetailMainData?> {
            override fun onResponse(
                call: Call<PostVehicleBasicDetailMainData?>,
                response: Response<PostVehicleBasicDetailMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostVehicleBasicDetailMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostcleNomineeDetail", "onResponse 1: " + response.raw() + "~" + json.toString())
                            vehiclePropasalForm.visibility = LinearLayout.GONE
                            vehicleTintIV.visibility = LinearLayout.GONE
                            vehicleTintTxtView.visibility = LinearLayout.GONE
                            vehicleTintLL.setBackgroundResource(R.drawable.check_circle)
                            existingPolciy.visibility = LinearLayout.VISIBLE

                            _idNominee=main!!.data!!.id

                            Toast.makeText(this@ProposalForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostcleNomineeDetail", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ProposalForm, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostcleNomineeDetail", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ProposalForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostVehicleBasicDetailMainData?>?, t: Throwable) {
                Log.d("PostcleNomineeDetail", "onResponse: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ProposalForm, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
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

    private fun isValidPanNumber(pan: String): Boolean {
        val panPattern = Regex("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        return pan.matches(panPattern)
    }


    private fun isValidAadharNumber(aadhar: String): Boolean {
        val aadharPattern = Regex("\\d{12}")
        return aadhar.matches(aadharPattern)
    }

    private fun toggleCompanySection() {
        if (isCompanySectionVisible) {

             return
        }

        companyLL.visibility = LinearLayout.VISIBLE
        companyTextView.setTextColor(resources.getColor(android.R.color.white))
        companyTextView.setBackgroundResource(R.drawable.private_company_selected)

        privateLL.visibility = LinearLayout.GONE
        privateTextView.setTextColor(Color.parseColor("#666666"))
        privateTextView.background = null

        nomineeOwnerDetailLL.visibility = LinearLayout.GONE


        isCompanySectionVisible = true
    }

    private fun togglePrivateSection() {
        if (!isCompanySectionVisible) {

            return
        }

        privateLL.visibility = LinearLayout.VISIBLE
        privateTextView.setTextColor(resources.getColor(android.R.color.white))
        privateTextView.setBackgroundResource(R.drawable.private_company_selected)

        companyLL.visibility = LinearLayout.GONE
        companyTextView.setTextColor(Color.parseColor("#666666"))
        companyTextView.background = null

        nomineeOwnerDetailLL.visibility = LinearLayout.VISIBLE


        isCompanySectionVisible = false
    }

    private fun ShowSummaryBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.summary_bottom_sheet, null)

        setupBottomSheetViewNoNocb(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()


    }



    private fun setupBottomSheetViewNoNocb(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {

        val close_icon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        IdvCover = bottomSheetView.findViewById<TextView>(R.id.IdvCover)
        ncbBTM = bottomSheetView.findViewById<TextView>(R.id.ncbBTM)
        planTypeBTM = bottomSheetView.findViewById<TextView>(R.id.planTypeBTM)
        ODPremium = bottomSheetView.findViewById<TextView>(R.id.ODPremium)
        basicODPremium = bottomSheetView.findViewById<TextView>(R.id.basicODPremium)
        ODDiscount = bottomSheetView.findViewById<TextView>(R.id.ODDiscount)
        noClaimBonus = bottomSheetView.findViewById<TextView>(R.id.noClaimBonus)
        totalPremium = bottomSheetView.findViewById<TextView>(R.id.totalPremium)
        gstNumber = bottomSheetView.findViewById<TextView>(R.id.gstNumber)
        gstPecentage = bottomSheetView.findViewById<TextView>(R.id.gstPecentage)
        payableAmount = bottomSheetView.findViewById<TextView>(R.id.payableAmount)



        if (!APIClient.isNetworkAvailable(this@ProposalForm)) {
            Toast.makeText(this@ProposalForm, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("GetSummaryDetails", "Request: $quoteId")
        val call: Call<GetCartDetailsMainData?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java)
                .getSummaryDetails(quoteId.toString())

        call?.enqueue(object : Callback<GetCartDetailsMainData?> {
            override fun onResponse(call: Call<GetCartDetailsMainData?>, response: Response<GetCartDetailsMainData?>) {
                if (response.isSuccessful) {
                    val main: GetCartDetailsMainData? = response.body()
                    main?.let {

                        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
                            maximumFractionDigits = 0
                        }

                        val formattedIdvCover = currencyFormat.format(main.data?.iDVCover)
                        val formattedNcb = currencyFormat.format(main.data?.ncb)
                        val formattedPayableAmount = currencyFormat.format(main.data?.payablePremium)
                        val formattedGstAmount = currencyFormat.format(main.data?.gstAmount)
                        val formattedBasicOdPremium = currencyFormat.format(main.data?.premiumDetail?.odPremium?.basicOdPremium)
                        val formattedOdDiscount = currencyFormat.format(main.data?.premiumDetail?.odPremium?.odDiscount)
                        val formattedNoClaimBonus = currencyFormat.format(main.data?.premiumDetail?.odPremium?.odNCB)
                        val formattedTotalPremium = currencyFormat.format(main.data?.premiumDetail?.tpPremium?.basicTPPremium)

                        IdvCover.text = formattedIdvCover
                        ncbBTM.text = formattedNcb
                        payableAmount.text = formattedPayableAmount
                        planTypeBTM.text = main.data?.insuranceType.toString()
                        gstPecentage.text = formattedGstAmount
                        gstNumber.text = "GST @ ${main.data?.gst.toString()} %"
                        ODPremium.text = formattedBasicOdPremium
                        basicODPremium.text = formattedBasicOdPremium
                        ODDiscount.text = formattedOdDiscount
                        noClaimBonus.text = formattedNoClaimBonus
                        totalPremium.text = formattedTotalPremium



                        IdvCoverp.text = formattedIdvCover
                        ncbP.text = formattedNcb
                        ODPremiumP.text = formattedBasicOdPremium
                        planTypep.text = main.data?.insuranceType.toString()
                        gstPecentage.text = formattedGstAmount
                        gstPecentagep.text = "GST @ ${main.data?.gst.toString()} %"
                        basicODPremiumP.text = formattedBasicOdPremium
                        ODDiscountp.text = formattedOdDiscount
                        noClaimBonusp.text = formattedNoClaimBonus
                        totalPremiump.text = formattedTotalPremium
                        payableAmountp.text = formattedPayableAmount


                        Toast.makeText(this@ProposalForm, response.body()?.message ?: "No message", Toast.LENGTH_SHORT).show()
                        Log.d("GetSummaryDetails", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                    }
                } else {
                    Log.d("GetSummaryDetails", "Server error: ${response.code()} - ${response.message()}~~${response.raw()}")
                    Toast.makeText(this@ProposalForm, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<GetCartDetailsMainData?>?, t: Throwable) {
                Log.e("GetSummaryDetails", "Failed to get user details", t)
                Toast.makeText(this@ProposalForm, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })


        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        close_icon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }



    }

    private fun GetSummaryDetails(quoteId: String?) {
        if (!APIClient.isNetworkAvailable(this@ProposalForm)) {
            Toast.makeText(this@ProposalForm, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("GetSummaryDetails11", "Request: $quoteId")
        val call: Call<GetCartDetailsMainData?>? =
            MyApplicationPort3010.getRetrofitClient(this@ProposalForm).create<ApiInterface>(
                ApiInterface::class.java)
                .getSummaryDetails(quoteId.toString())
        
        call?.enqueue(object : Callback<GetCartDetailsMainData?> {
            override fun onResponse(call: Call<GetCartDetailsMainData?>, response: Response<GetCartDetailsMainData?>) {
                if (response.isSuccessful) {
                    val main: GetCartDetailsMainData? = response.body()
                    main?.let {

                        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
                            maximumFractionDigits = 0
                        }

                        val formattedIdvCover = currencyFormat.format(main.data?.iDVCover)
                        val formattedNcb = currencyFormat.format(main.data?.ncb)
                        val formattedPayableAmount = currencyFormat.format(main.data?.payablePremium)

                        idvValue.text = formattedIdvCover
                        ncbValue.text = formattedNcb
                        totalAmount.text = formattedPayableAmount
                        planType.text = main.data?.insuranceType.toString()





                        Toast.makeText(this@ProposalForm, response.body()?.message ?: "No message", Toast.LENGTH_SHORT).show()
                        Log.d("GetSummaryDetails11", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                    }
                } else {
                    Log.d("GetSummaryDetails11", "Server error: ${response.code()} - ${response.message()}~~${response.raw()}")
                    Toast.makeText(this@ProposalForm, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetCartDetailsMainData?>?, t: Throwable) {
                Log.e("GetSummaryDetails11", "Failed to get user details", t)
                Toast.makeText(this@ProposalForm, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
