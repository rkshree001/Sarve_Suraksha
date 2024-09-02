package com.bnet.sarvesuraksha.view.Landing_Page_Section.Bike_Insurance

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3010
import com.bnet.sarvesuraksha.model_api.GetQuoteFormMainData
import com.bnet.sarvesuraksha.model_api.GetQouteFormListDataMain
import com.bnet.sarvesuraksha.model_api.GetQouteFormListInsuranceDetail
import com.bnet.sarvesuraksha.model_api.GetQuoteFormAddOn
import com.bnet.sarvesuraksha.model_api.GetQuoteFormMainRes
import com.bnet.sarvesuraksha.model_api.GetVehicleRcDetailMain
import com.bnet.sarvesuraksha.model_api.PostCartDataMain
import com.bnet.sarvesuraksha.model_api.PostVehcileIndMainData
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance.AddVehicleData
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance.CarInsuranceQuoteAllFilters
import com.bnet.sarvesuraksha.view.adapter.CarInsuranceQuoteAdapter
import com.bnet.sarvesuraksha.view.adapter.FilterAdapterSortBy
import com.bnet.savresuraksha.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.skydoves.powerspinner.PowerSpinnerView
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class BikeInsuranceQoutePlanForm : AppCompatActivity(),
    CarInsuranceQuoteAdapter.OnConfirmAndProceedClickListener {

    private lateinit var sortByFilters: LinearLayout
    private lateinit var CarName: TextView
    private lateinit var modelNameAndType: TextView

    private lateinit var allFilters: TextView
    private lateinit var totalPlansAvl: TextView
    private lateinit var addOnFilters: LinearLayout
    private lateinit var filterOption_LL: LinearLayout
    private lateinit var adapterSortBy: FilterAdapterSortBy
    private lateinit var adapterAddOn: FilterAdapterSortBy
    private lateinit var popupWindowSortBy: PopupWindow
    private lateinit var popupWindowAddOn: PopupWindow
    private lateinit var GetAllQuoteList: RecyclerView
    private lateinit var GetAllQuoteListTP: RecyclerView
    private lateinit var OwndamageParentLL: LinearLayout
    private lateinit var ThirdpartyparentLL: LinearLayout
    private lateinit var ncbBottomSheet: LinearLayout
    private lateinit var thirdPartyLL: LinearLayout
    private lateinit var OwndamageLL: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var existingPolicyLL: LinearLayout
    private lateinit var IdvCoverLL: LinearLayout
    private lateinit var editCardDetails: LinearLayout
    private var quoteId: String? = ""
    private var vehicleIdIn: String? = null
    private var VehicleId: String? = null
    private var vehicleIdINNPP: String? = null
    private var VehicleNumberS: String? = null

    private lateinit var OwndamageParentLLs: LinearLayout
    private lateinit var ThirdpartyparentLLs: LinearLayout
    private lateinit var thirdPartyLLs: LinearLayout
    private lateinit var OwndamageLLs: LinearLayout

    private lateinit var thirdPartyFilter: LinearLayout
    private lateinit var planTenure: LinearLayout
    private lateinit var planSelected: TextView
    private lateinit var comprehensiveFilter: LinearLayout

    var globalInsuranceDetail: List<GetQouteFormListInsuranceDetail>? = null

    val emptyAddOnList = arrayListOf<String>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_insurance_qoute_plan_form)


        sortByFilters = findViewById(R.id.sortByFilters)
        addOnFilters = findViewById(R.id.filteroption_addOn)
        filterOption_LL = findViewById(R.id.filterOption_LL)
        CarName = findViewById(R.id.CarName)
        modelNameAndType = findViewById(R.id.modelNameAndType)
        GetAllQuoteList = findViewById(R.id.GetAllQuoteList)
        totalPlansAvl = findViewById(R.id.totalPlansAvl)
        allFilters = findViewById(R.id.allFilters)
        ncbBottomSheet = findViewById(R.id.ncbBottomSheet)
        IdvCoverLL = findViewById(R.id.IdvCoverLL)
        editCardDetails = findViewById(R.id.editCardDetails)
        thirdPartyFilter = findViewById(R.id.thirdPartyFilter)
        comprehensiveFilter = findViewById(R.id.comprehensiveFilter)
        planTenure = findViewById(R.id.planTenure)
        planSelected = findViewById(R.id.planSelected)



        OwndamageParentLL = findViewById(R.id.OwndamageParentLL)
        ThirdpartyparentLL = findViewById(R.id.ThirdpartyparentLL)
        thirdPartyLL = findViewById(R.id.thirdPartyLL)
        OwndamageLL = findViewById(R.id.OwndamageLL)
        GetAllQuoteListTP = findViewById(R.id.GetAllQuoteListTP)
        goBack = findViewById(R.id.goBack)
        existingPolicyLL = findViewById(R.id.existingPolicyLL)

        quoteId = intent.getStringExtra("QuoteId")
        Log.d("IKUGAD", "onCreate: "+quoteId)
        vehicleIdIn = intent.getStringExtra("vehicleId")
        getAllQouteListTemp(quoteId)

        val intentType = intent.getStringExtra("Type")
        if (intentType == "Filter") {

            val selectedSortBys = intent.getStringArrayListExtra("SelectedSortBy")
            val selectedAddOnss = intent.getStringArrayListExtra("SelectedAddOns")
            val selectedDeductibles = intent.getStringArrayListExtra("SelectedDeductibles")
            val selectedAccessoriesCover = intent.getStringArrayListExtra("SelectedAccessoriesCover")


            Log.d("CarInsuPlanForm", "Selected Sort By: $selectedSortBys")
            Log.d("CarInsuPlanForm", "Selected Add Ons: $selectedAddOnss")
            Log.d("CarInsuPlanForm", "Selected Deductibles: $selectedDeductibles")
            Log.d("CarInsuPlanForm", "Selected Accessories Cover: $selectedAccessoriesCover")


            val sortMapping = mapOf(
                "Premium low to high" to "premium_low_to_high",
                "Premium high to low" to "premium_high_to_low",
                "IDV low to high" to "IDV_low_to_high",
                "IDV high to low" to "IDV_high_to_low"
            )

            val selectedSortByList = intent.getStringArrayListExtra("SelectedSortBy") ?: arrayListOf()

            val mappedSortByList = selectedSortByList.mapNotNull { sortMapping[it] }

            val selectedSortBy = mappedSortByList.firstOrNull().toString()

            val selectedAddOns = intent.getStringArrayListExtra("SelectedAddOns") ?: arrayListOf()

            Log.d("selectedSortBy", "onCreate: $selectedSortBy")

            GetQouteListComprehensive(VehicleId, "comprehensive", 110000, selectedSortBy, selectedAddOns)

            GetQouteListThirdParty(VehicleId, "third_party", 110000, 1, selectedSortBy, selectedAddOns)


            /*            val selectedSortBy = intent.getStringArrayListExtra("SelectedSortBy").toString()
                        val selectedAddOns = intent.getStringArrayListExtra("SelectedAddOns") ?: arrayListOf()

                        Log.d("selectedSortBy", "onCreate: "+selectedSortBy)

                        GetQouteListComprehensive(VehicleId, "comprehensive", 110000, selectedSortBy, selectedAddOns)

                        GetQouteListThirdParty(VehicleId, "third_party", 110000, 1, selectedSortBy, selectedAddOns)*/

//            GetQouteListComprehensive(VehicleId, "comprehensive", 110000)
//            GetQouteListThirdParty(VehicleId,"third_party",110000,1)


        }else{
            GetQouteListComprehensive(
                VehicleId,
                "comprehensive",
                110000,
                "premium_low_to_high",
                emptyAddOnList
            )

        }






        editCardDetails.setOnClickListener {

            val intent = Intent(this@BikeInsuranceQoutePlanForm, AddVehicleData::class.java)
            Log.d("vehicleIdIn", "QuoteId: $quoteId"+"vehicleIdIn``~"+vehicleIdIn)
            intent.putExtra("vehicleId",quoteId)
            intent.putExtra("Type","Edit")
            startActivity(intent)

        }

        goBack.setOnClickListener {
            val intent = Intent(this@BikeInsuranceQoutePlanForm, BikeInsurance::class.java)
            startActivity(intent)
        }

        ncbBottomSheet.setOnClickListener {
            ShowNoNcbBonusBottomSheet()
         //   ShowNcbBonusBottomSheet()
        }

        IdvCoverLL.setOnClickListener {
            ShowIDVCoverBottomSheet()
        }

        existingPolicyLL.setOnClickListener {
            ShowExistingPolicyPopup()
        }
        planTenure.setOnClickListener {
            ShowPlanTenure()
        }

        initializeAdapters()

        val dropdownViewSortBy = LayoutInflater.from(this).inflate(R.layout.dropdown_list, filterOption_LL, false)
        val dropdownListViewSortBy = dropdownViewSortBy.findViewById<ListView>(R.id.dropdownListView)
        dropdownListViewSortBy.adapter = adapterSortBy

        val dropdownViewAddOn = LayoutInflater.from(this).inflate(R.layout.dropdown_list_add_ons, filterOption_LL, false)
        val dropdownListViewAddOn = dropdownViewAddOn.findViewById<ListView>(R.id.dropdownListView)
        dropdownListViewAddOn.adapter = adapterAddOn


        popupWindowSortBy = PopupWindow(dropdownViewSortBy, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindowAddOn = PopupWindow(dropdownViewAddOn, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)

        val marginX = 16.dpToPx()
        val marginY = 8.dpToPx()

        sortByFilters.setOnClickListener {
            if (popupWindowSortBy.isShowing) {
                popupWindowSortBy.dismiss()
            } else {
                popupWindowSortBy.showAsDropDown(sortByFilters, marginX, marginY)
            }
        }

        addOnFilters.setOnClickListener {
            if (popupWindowAddOn.isShowing) {
                popupWindowAddOn.dismiss()
            } else {
                popupWindowAddOn.showAsDropDown(addOnFilters, marginX, marginY)
            }
        }


        OwndamageParentLL.setOnClickListener {


//            if (globalInsuranceDetail!!.size>1){
                GetQouteListComprehensive(
                    VehicleId,
                    "comprehensive",
                    110000,
                    "premium_low_to_high",
                    emptyAddOnList
                )
//            }
            selectLayout1(OwndamageParentLL, OwndamageLL)

        }
        ThirdpartyparentLL.setOnClickListener {


//            if (globalInsuranceDetail!!.size>1){
                GetQouteListThirdParty(
                    VehicleId,
                    "third_party",
                    110000,
                    1,
                    "premium_low_to_high",
                    emptyAddOnList
                )
//            }
            selectLayout1(ThirdpartyparentLL, thirdPartyLL)
        }


        allFilters.setOnClickListener {
            val intent = Intent(this@BikeInsuranceQoutePlanForm, CarInsuranceQuoteAllFilters::class.java)
            intent.putExtra("QuoteId", quoteId)
            startActivity(intent)
        }


    }


    private fun getSortByValue(displayValue: String): String {
        return when(displayValue) {
            "Premium low to high" -> "premium_low_to_high"
            "Premium high to low" -> "premium_high_to_low"
            "IDV low to high" -> "IDV_low_to_high"
            "IDV high to low" -> "IDV_high_to_low"
            else -> ""
        }
    }


    private fun ShowExistingPolicyPopup() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.existingt_policy_bottomsheet, null)

        setupBottomSheetViewEP(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()


    }

    private fun PostVehicleInsuranceDetail(
        vehicleId: String?,
        insuranceDetails: List<JSONObject>,
        bottomSheetDialog: BottomSheetDialog
    ) {
        val jsonArray = JSONArray(insuranceDetails).toString()

        Log.d("LOGPostBrfTRan", "Resp : $jsonArray")
        val call: Call<PostVehcileIndMainData?> =
            MyApplicationPort3010.getRetrofitClient(this).create(ApiInterface::class.java)
                .postVehicleInsDataBike(vehicleId.toString(), jsonArray)
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
                            bottomSheetDialog.dismiss()
                            GetQouteListComprehensive(
                                VehicleId,
                                "comprehensive",
                                110000,
                                "premium_low_to_high",
                                emptyAddOnList
                            )
                            GetQouteListThirdParty(
                                VehicleId,
                                "third_party",
                                110000,
                                1,
                                "premium_low_to_high",
                                emptyAddOnList
                            )

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
    fun convertDateToDDMMYYYY(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return try {
            val date = inputFormat.parse(dateStr)
            date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: ParseException) {
            "Invalid Date"
        }
    }



    private fun setupBottomSheetViewEP(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {

        val close_icon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        OwndamageParentLLs = bottomSheetView.findViewById<LinearLayout>(R.id.OwndamageParentLL)
        ThirdpartyparentLLs = bottomSheetView.findViewById<LinearLayout>(R.id.ThirdpartyparentLL)
        OwndamageLLs = bottomSheetView.findViewById<LinearLayout>(R.id.OwndamageLL)
        thirdPartyLLs = bottomSheetView.findViewById<LinearLayout>(R.id.thirdPartyLL)
        val ProceedOwnDamage = bottomSheetView.findViewById<LinearLayout>(R.id.ProceedOwnDamage)
        val InsuresNameSpinnerOD = bottomSheetView.findViewById<PowerSpinnerView>(R.id.InsuresNameSpinnerOD)
        val thirdPartySpinner = bottomSheetView.findViewById<PowerSpinnerView>(R.id.thirdPartySpinner)
        val expiryDateOD = bottomSheetView.findViewById<TextInputEditText>(R.id.expiryDateOD)
        val expirydateThirdParty = bottomSheetView.findViewById<TextInputEditText>(R.id.expirydateThirdParty)
        val confirmAndProceed = bottomSheetView.findViewById<LinearLayout>(R.id.confirmAndProceed)
        val alertTextViewOD = bottomSheetView.findViewById<TextView>(R.id.alertTextViewOD)
        val alertODLL = bottomSheetView.findViewById<LinearLayout>(R.id.alertODLL)
        val alertTPLL = bottomSheetView.findViewById<LinearLayout>(R.id.alertTPLL)
        val alertTextViewTP = bottomSheetView.findViewById<TextView>(R.id.alertTextViewTP)
        alertTPLL.visibility = View.GONE
        alertODLL.visibility = View.GONE
        var policyNumberOd:String=""
        var policyNumberTp:String=""
        val InsurerList = listOf("Tata Aig", "Bajaj Aig", "Digit", "Reliance")
        InsuresNameSpinnerOD.setItems(InsurerList)
        thirdPartySpinner.setItems(InsurerList)
        expiryDateOD.isFocusable = false
        expirydateThirdParty.isFocusable = false

        expiryDateOD.setOnClickListener { showDatePickerDialog(expiryDateOD) }
        expirydateThirdParty.setOnClickListener { showDatePickerDialog(expirydateThirdParty) }

        OwndamageParentLLs.setOnClickListener {

            selectLayoutBottomSheet(OwndamageParentLLs, OwndamageLLs)

        }
        ThirdpartyparentLLs.setOnClickListener {

            selectLayoutBottomSheet(ThirdpartyparentLLs, thirdPartyLLs)
        }



        ProceedOwnDamage.setOnClickListener {
            selectLayoutBottomSheet(ThirdpartyparentLL, thirdPartyLLs)
        }


        globalInsuranceDetail?.forEach { insuranceDetail ->

            when (insuranceDetail.insuranceType) {
                "own_damage" -> {
                    InsuresNameSpinnerOD.setText(insuranceDetail.insurerName)
                    val formattedDateStr = convertDateToDDMMYYYY(insuranceDetail.policyExpiryDate.toString())

                    expiryDateOD.setText(formattedDateStr)

                }
                "third_party" -> {

                    thirdPartySpinner.setText(insuranceDetail.insurerName)
                    val formattedDateStr = convertDateToDDMMYYYY(insuranceDetail.policyExpiryDate.toString())

                    expirydateThirdParty.setText(formattedDateStr)


                }
            }
        }



        val json: JSONObject = JSONObject()
        json.put("vehicleNo", VehicleNumberS)
        json.put("vehicleType", "vehicle_with_number")
        json.put("vehicleSegment", "BIKE")

        Log.d("postRcDetailAndGetRc", "Resp : "+json.toString())
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
                            Log.d("ResponseRawa", "postRcDetailAndGetRc 1: "+response.raw()+"~"+json.toString())

                            val vehicleInsuranceDetails = main?.data?.vehicleDetail?.insuranceDetail
                            vehicleIdINNPP = main?.data?.vehicleDetail?.id
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                            val currentDate = Date()

                            vehicleInsuranceDetails?.forEach { insuranceDetail ->
                                val expiryDate = dateFormat.parse(insuranceDetail.policyExpiryDate)
                                val daysDifference = getDateDifference(currentDate, expiryDate)

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


                        }
                    } catch (e: Exception) {

                    }
                } else {
                    Log.d("ResponseRawa", "postRcDetailAndGetRc 3: "+response.raw()+"~"+json.toString())
                }
            }

            override fun onFailure(call: Call<GetVehicleRcDetailMain?>, t: Throwable) {
                Log.d("ResponseRawa", "postRcDetailAndGetRc: "+t.localizedMessage+"~"+json.toString())
            }


        })



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

            PostVehicleInsuranceDetail(vehicleIdINNPP, insuranceDetails,bottomSheetDialog)
        }



        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        close_icon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }



    }


    private fun getDateDifference(date1: Date, date2: Date?): Long? {
        return date2?.let {
            val diff = it.time - date1.time
            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        }
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


        datePickerDialog.setTitle("Select Date")
        datePickerDialog.show()
    }


    private fun ShowNcbBonusBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.confirm_your_ncb_bottomsheet, null)

        setupBottomSheetView(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()


    }



    private fun setupBottomSheetView(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {

        val close_icon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)


        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        close_icon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }



    }

    private fun ShowNoNcbBonusBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.not_eligibile_ncb, null)

        setupBottomSheetViewNoNocb(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()


    }



    private fun setupBottomSheetViewNoNocb(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {

        val close_icon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val okay_ll = bottomSheetView.findViewById<LinearLayout>(R.id.okay_ll)


        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        close_icon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        okay_ll.setOnClickListener {
            bottomSheetDialog.dismiss()
        }



    }


    private fun ShowPlanTenure() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.plan_tenure, null)

        setUpPlanTenure(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()


    }



    private fun setUpPlanTenure(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        val closeIcon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val save_LL = bottomSheetView.findViewById<LinearLayout>(R.id.save_LL)
        val radioGroupTenure: RadioGroup = bottomSheetView.findViewById(R.id.radioGroupTenure)

        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        closeIcon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        val planText = planSelected.text.toString()
        val numericPart = planText.split(" ")[0].toIntOrNull() ?: 0


        when (numericPart) {
            1 -> radioGroupTenure.check(R.id.year_1)
            2 -> radioGroupTenure.check(R.id.year_2)
            3 -> radioGroupTenure.check(R.id.year_3)
            else -> radioGroupTenure.clearCheck()
        }

        radioGroupTenure.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.year_1 -> {
                    planSelected.setText("1 Year")
                    GetQouteListThirdParty(
                        VehicleId,
                        "third_party",
                        110000,
                        1,
                        "premium_low_to_high",
                        emptyAddOnList
                    )

                }
                R.id.year_2 -> {
                    planSelected.setText("2 Year")
                    GetQouteListThirdParty(
                        VehicleId,
                        "third_party",
                        110000,
                        2,
                        "premium_low_to_high",
                        emptyAddOnList
                    )

                }
                else -> {
                    planSelected.setText("3 Year")
                    GetQouteListThirdParty(
                        VehicleId,
                        "third_party",
                        110000,
                        3,
                        "premium_low_to_high",
                        emptyAddOnList
                    )

                }
            }
        }

        save_LL.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    private fun ShowIDVCoverBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.car_value_bottomsheet, null)

        setupBottomSheetViewIDV(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()
    }

    private fun setupBottomSheetViewIDV(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        val closeIcon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val secureYourCar = bottomSheetView.findViewById<LinearLayout>(R.id.secureYourCar)
        val rangeTextLL = bottomSheetView.findViewById<LinearLayout>(R.id.rangeTextLL)
        val rangeSeekBar = bottomSheetView.findViewById<RangeSeekBar>(R.id.RangeSeekBar)
        val headerTxt = bottomSheetView.findViewById<TextView>(R.id.headerTxt)
        val radioGroupCNG: RadioGroup = bottomSheetView.findViewById(R.id.radioGroupCNG)

        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        closeIcon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        headerTxt.setText("Bike Value")
        val preferences = getSharedPreferences("IDVPreferences", MODE_PRIVATE)
        val savedRadioId = preferences.getInt("selectedRadioId", R.id.bestValueRadio)
        val savedRangeValue = preferences.getFloat("rangeSeekBarValue", 5.0f)

        // Restore saved RadioButton and RangeSeekBar values
        radioGroupCNG.check(savedRadioId)
        rangeSeekBar.setRange(5.0f, 10.0f)
        rangeSeekBar.setProgress(savedRangeValue, 10.0f)
        rangeSeekBar.leftSeekBar.setIndicatorText("₹${String.format("%.2f", savedRangeValue)}L")

        rangeSeekBar.visibility = if (savedRadioId == R.id.customRadio) View.VISIBLE else View.INVISIBLE
        rangeTextLL.visibility = if (savedRadioId == R.id.customRadio) View.VISIBLE else View.INVISIBLE

        radioGroupCNG.setOnCheckedChangeListener { _, checkedId ->
            val editor = preferences.edit()
            editor.putInt("selectedRadioId", checkedId).apply()

            if (checkedId == R.id.customRadio) {
                rangeSeekBar.visibility = View.VISIBLE
                rangeTextLL.visibility = View.VISIBLE
            } else {
                rangeSeekBar.visibility = View.INVISIBLE
                rangeTextLL.visibility = View.INVISIBLE
            }
        }

        rangeSeekBar.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(rangeSeekBar: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                rangeSeekBar.setIndicatorText("₹${String.format("%.2f", leftValue)}L")
                val editor = preferences.edit()
                editor.putFloat("rangeSeekBarValue", leftValue).apply()
            }

            override fun onStartTrackingTouch(rangeSeekBar: RangeSeekBar, isLeft: Boolean) {}
            override fun onStopTrackingTouch(rangeSeekBar: RangeSeekBar, isLeft: Boolean) {}
        })

        secureYourCar.setOnClickListener {
            val selectedIDV = convertIDV(rangeSeekBar.leftSeekBar.progress)
            Log.d("setupBottomSheetViewIDV", "Converted IDV: $selectedIDV")

            GetQouteListComprehensive(
                VehicleId,
                "comprehensive",
                selectedIDV,
                "premium_low_to_high",
                emptyAddOnList
            )
            GetQouteListThirdParty(
                VehicleId,
                "third_party",
                selectedIDV,
                1,
                "premium_low_to_high",
                emptyAddOnList
            )
            bottomSheetDialog.dismiss()
        }
    }

    private fun convertIDV(value: Float): Int {

        val scaledValue = value * 100000

        val roundedValue = scaledValue.toInt()

        val finalValue = (roundedValue + 500) / 1000 * 1000

        return finalValue
    }



    /*    private fun setupBottomSheetViewIDV(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
            val closeIcon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
            val rangeSeekBar = bottomSheetView.findViewById<RangeSeekBar>(R.id.RangeSeekBar)

            bottomSheetDialog.setCanceledOnTouchOutside(false)
            bottomSheetDialog.setCancelable(false)

            closeIcon.setOnClickListener {
                bottomSheetDialog.dismiss()
            }


            rangeSeekBar.setRange(5.0f, 10.0f)
            rangeSeekBar.setProgress(5.0f, 10.0f)


            rangeSeekBar.setOnRangeChangedListener(object : OnRangeChangedListener {
                override fun onRangeChanged(rangeSeekBar: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {

                    rangeSeekBar.leftSeekBar.setIndicatorText("₹${String.format("%.2f", leftValue)}L")


                    rangeSeekBar.rightSeekBar.setIndicatorText("₹${String.format("%.2f", rightValue)}L")

                    if (rangeSeekBar.rangeSeekBarState[0].isMin) {
                        rangeSeekBar.leftSeekBar.setIndicatorText("₹${String.format("%.2f", leftValue)}L (Min)")
                    } else if (rangeSeekBar.rangeSeekBarState[0].isMax) {
                        rangeSeekBar.leftSeekBar.setIndicatorText("₹${String.format("%.2f", leftValue)}L (Max)")
                    }
                }

                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                    // Handle start tracking touch event if needed
                }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                    // Handle stop tracking touch event if needed
                }
            })
        }*/


    private fun selectLayout(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {

        OwndamageParentLL.background = null
        ThirdpartyparentLL.background = null


        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg_cream)


        thirdPartyLL.visibility = if (detailsLayout == thirdPartyLL) LinearLayout.VISIBLE else LinearLayout.GONE
        OwndamageLL.visibility = if (detailsLayout == OwndamageLL) LinearLayout.VISIBLE else LinearLayout.GONE

    }
    private fun selectLayout1(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {

        OwndamageParentLL.background = null
        ThirdpartyparentLL.background = null


        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg_cream)


        thirdPartyLL.visibility = if (detailsLayout == thirdPartyLL) LinearLayout.VISIBLE else LinearLayout.GONE
        OwndamageLL.visibility = if (detailsLayout == OwndamageLL) LinearLayout.VISIBLE else LinearLayout.GONE


        comprehensiveFilter.visibility = if (detailsLayout == OwndamageLL) LinearLayout.VISIBLE else LinearLayout.GONE

        val thirdPartyVisibility = if (detailsLayout == thirdPartyLL) LinearLayout.VISIBLE else LinearLayout.GONE
        val owndamageVisibility = if (detailsLayout == OwndamageLL) LinearLayout.VISIBLE else LinearLayout.GONE

        val comprehensiveVisibility = if (detailsLayout == OwndamageLL) LinearLayout.VISIBLE else LinearLayout.GONE
        val thirdPartyFilterVisibility = if (detailsLayout == thirdPartyLL) LinearLayout.VISIBLE else LinearLayout.GONE

        comprehensiveFilter.visibility = comprehensiveVisibility
        thirdPartyFilter.visibility = thirdPartyFilterVisibility


        Log.d("VisibilityDebug", "thirdPartyLL visibility: $thirdPartyVisibility")
        Log.d("VisibilityDebug", "OwndamageLL visibility: $owndamageVisibility")
        Log.d("VisibilityDebug", "comprehensiveFilter visibility: $comprehensiveVisibility")
        Log.d("VisibilityDebug", "thirdPartyFilter visibility: $thirdPartyFilterVisibility")
    }
    private fun selectLayoutBottomSheet(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {

        OwndamageParentLLs.background = null
        ThirdpartyparentLLs.background = null


        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg)


        thirdPartyLLs.visibility = if (detailsLayout == thirdPartyLLs) LinearLayout.VISIBLE else LinearLayout.GONE
        OwndamageLLs.visibility = if (detailsLayout == OwndamageLLs) LinearLayout.VISIBLE else LinearLayout.GONE
    }

    private fun getAllQouteListTemp(quoteId: String?) {

        if (!APIClient.isNetworkAvailable(this@BikeInsuranceQoutePlanForm)) {
            Toast.makeText(this@BikeInsuranceQoutePlanForm, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("getempBike", "Request : $quoteId")
        val call: Call<GetQouteFormListDataMain?>? =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getAllQouteListTempBike(quoteId.toString())
        call?.enqueue(object : Callback<GetQouteFormListDataMain?> {
            override fun onResponse(
                call: Call<GetQouteFormListDataMain?>,
                response: retrofit2.Response<GetQouteFormListDataMain?>
            ) {
                if (response.isSuccessful) {
                    val main: GetQouteFormListDataMain? = response.body()
                    main?.let {
                        Log.d("getempBike", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")

                        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        val dateFormatOutput = SimpleDateFormat("yyyy")
                        val registrationDateStr= main?.data!!.vehicleBasicDetail!!.registrationDate

                        Log.d("registrationDateStr", "onResponse: "+registrationDateStr)

                        val year = try {
                            val date = dateFormatInput.parse(registrationDateStr)
                            date?.let { dateFormatOutput.format(it) } ?: "Unknown Year"
                        } catch (e: ParseException) {
                            "Unknown Year"
                        }
                        val vehcileType=main?.data!!.vehicleDetail!!.makerName+""+main?.data!!.vehicleDetail!!.modelName
                        val vehicleregNo=main?.data!!.vehicleBasicDetail!!.registrationNumber
                        VehicleNumberS=main?.data!!.vehicleBasicDetail!!.registrationNumber

                        val vehcileTypeVal=main?.data!!.vehicleDetail!!.variantName+"-"+main?.data!!.vehicleDetail!!.fuelType+"-"+year
                        VehicleId =main?.data!!.vehicleDetail!!.id

//                        Log.d(TAG, "onResponse: "+vehicleregNo+"~~~"+main?.data!!.vehicleDetail!!.registrationNumber)
                        val makerName = main?.data?.vehicleDetail?.makerName ?: ""
                        val modelName = main?.data?.vehicleDetail?.modelName ?: ""
                        val truncatedMakerName = if (makerName.length > 5) {
                            makerName.split(" ")[0]
                        } else {
                            makerName
                        }

//                        globalInsuranceDetail = main?.data?.insuranceDetail

//                        Log.d("PostAndGetInsList", "onResponse: "+globalInsuranceDetail!!.size)


//                        globalInsuranceDetail?.forEach { insuranceDetail ->
//
//                            when (insuranceDetail.insuranceType) {
//                                "own_damage" -> {
//
//                                }
//                                "third_party" -> {
//
//
//                                }
//                            }
//                        }

                        CarName.text=vehicleregNo
                        modelNameAndType.text=truncatedMakerName+" "+modelName

                        Toast.makeText(this@BikeInsuranceQoutePlanForm, response.body()!!.message, Toast.LENGTH_SHORT).show()


                    }
                } else {
                    Log.d("getempBike", "Server error: ${response.code()} - ${response.message()}"+"~~"+response.raw())
                    Toast.makeText(this@BikeInsuranceQoutePlanForm, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetQouteFormListDataMain?>?, t: Throwable) {
                Log.e("getempBike", "Failed to get user details", t)
                Toast.makeText(this@BikeInsuranceQoutePlanForm, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
        
    }


    private fun initializeAdapters() {
        val filterOptions = listOf("Premium low to high", "Premium high to low", "IDV high to low", "IDV low to high")
        adapterSortBy = FilterAdapterSortBy(this, filterOptions)

        val addOnOptions = listOf("Zero depreciation", "24x7 roadside assistance", "Engine protection cover", "Consumables")
        adapterAddOn = FilterAdapterSortBy(this, addOnOptions)
    }

    fun updateFilterOptions(newFilterOptions: List<String>) {
        adapterSortBy.clear()
        adapterSortBy.addAll(newFilterOptions)
        adapterSortBy.notifyDataSetChanged()
    }

    fun updateAddOnOptions(newAddOnOptions: List<String>) {
        adapterAddOn.clear()
        adapterAddOn.addAll(newAddOnOptions)
        adapterAddOn.notifyDataSetChanged()
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

/*
    private fun GetQouteListComprehensive(
        VehicleId: String?,
        insuranceType: String,
        s: Int,
        selectedSortBy: String,
        selectedAddOns: ArrayList<String>
    ) {
        val json: JSONObject = JSONObject()
        val addOnArray = JSONArray()
        json.put("sortBy", "premium_low_to_high")
        json.put("addOn", addOnArray)
        json.put("insuranceType",insuranceType)
        json.put("IDVCover",s)
        json.put("tenure",1)
        json.put("quoteId",quoteId)
        json.put("vehicleSegment", "BIKE")

        Log.d("PostAndGeComprehe", "Resp : "+json.toString())
        val call: Call<GetQuoteFormMainData>? =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).PostAndGetInsuranceList(json.toString())
        call?.enqueue(object : Callback<GetQuoteFormMainData?> {
            override fun onResponse(
                call: Call<GetQuoteFormMainData?>,
                response: retrofit2.Response<GetQuoteFormMainData?>
            ) {
                if (response.isSuccessful()) {
                    val main: GetQuoteFormMainData? = response.body()
                    val mainRes: List<GetQuoteFormMainRes>? = response.body()!!.data!!.quoteList
                    try {

                        if (response.isSuccessful){
                            totalPlansAvl.text="Showing "+ mainRes!!.size +" available plan"
                            BindDataToRecyclerView(mainRes)

                            Log.d("PostAndGeComprehe", "onResponse1: "+response.raw()+"~"+json.toString())

//                            Toast.makeText(this@CarInsuranceQoutePlanForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostAndGeComprehe", "onResponse2: "+response.raw()+"~"+json.toString())
//                        Toast.makeText(this@CarInsuranceQoutePlanForm, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostAndGeComprehe", "onResponse3: "+response.raw()+"~"+json.toString())
//                    Toast.makeText(this@CarInsuranceQoutePlanForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            public override fun onFailure(call: Call<GetQuoteFormMainData?>?, t: Throwable) {
                Log.d("PostAndGeComprehe", "onResponse: "+t.localizedMessage+"~"+json.toString())
//                Toast.makeText(this@CarInsuranceQoutePlanForm, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }
*/


    private fun GetQouteListComprehensive(
        VehicleId: String?,
        insuranceType: String,
        s: Int,
        sortBy: String,
        addOn: List<String>
    ) {
        val json = JSONObject()
        val addOnArray = JSONArray()

        addOn.forEach {
            addOnArray.put(it)
        }

        json.put("sortBy", sortBy)
        json.put("addOn", addOnArray)
        json.put("insuranceType", insuranceType)
        json.put("IDVCover", s)
        json.put("tenure", 1)
        json.put("quoteId", quoteId)
        json.put("vehicleSegment", "BIKE")

        Log.d("PostAndGeComprehe", "Resp : $json")

        val call: Call<GetQuoteFormMainData>? = MyApplicationPort3010.getRetrofitClient(this)
            .create<ApiInterface>(ApiInterface::class.java)
            .PostAndGetInsuranceList(json.toString())

        call?.enqueue(object : Callback<GetQuoteFormMainData?> {
            override fun onResponse(
                call: Call<GetQuoteFormMainData?>,
                response: retrofit2.Response<GetQuoteFormMainData?>
            ) {
                if (response.isSuccessful) {
                    val mainRes: List<GetQuoteFormMainRes>? = response.body()?.data?.quoteList
                    try {
                        if (mainRes != null) {
                            totalPlansAvl.text = "Showing ${mainRes.size} available plan"
                            BindDataToRecyclerView(mainRes)
                            Log.d("PostAndGeComprehe", "onResponse1: ${response.raw()}~$json")
                        }
                    } catch (e: Exception) {
                        Log.d("PostAndGeComprehe", "onResponse2: ${response.raw()}~$json")
                    }
                } else {
                    Log.d("PostAndGeComprehe", "onResponse3: ${response.raw()}~$json")
                }
            }

            override fun onFailure(call: Call<GetQuoteFormMainData?>?, t: Throwable) {
                Log.d("PostAndGeComprehe", "onResponse: ${t.localizedMessage}~$json")
            }
        })
    }



    private fun BindDataToRecyclerView(mainRes: List<GetQuoteFormMainRes>?) {
        val adapter = CarInsuranceQuoteAdapter(mainRes,this)
        GetAllQuoteList.layoutManager = LinearLayoutManager(this)
        GetAllQuoteList.adapter = adapter
    }


    private fun GetQouteListThirdParty(
        VehicleId: String?,
        insuranceType: String,
        s: Int,
        i: Int,
        selectedSortBy: String,
        selectedAddOns: ArrayList<String>
    ) {
        val json = JSONObject()
        val addOnArray = JSONArray()

        selectedAddOns.forEach {
            addOnArray.put(it)
        }

        json.put("sortBy", selectedSortBy)
        json.put("addOn", addOnArray)
        json.put("insuranceType", insuranceType)
        json.put("IDVCover", s)
        json.put("quoteId", quoteId)
        json.put("vehicleSegment", "BIKE")

        val planText = planSelected.text.toString()
        val numericPart = planText.split(" ")[0].toIntOrNull() ?: 0
        json.put("tenure", numericPart)

        Log.d("PostAndGethirdparty", "GetQouteListThirdParty : $json")

        Log.d("PostAndGethirdparty", "GetQouteListThirdParty : "+json.toString())
        val call: Call<GetQuoteFormMainData>? =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).PostAndGetInsuranceList(json.toString())
        call?.enqueue(object : Callback<GetQuoteFormMainData?> {
            override fun onResponse(
                call: Call<GetQuoteFormMainData?>,
                response: retrofit2.Response<GetQuoteFormMainData?>
            ) {
                if (response.isSuccessful()) {
                    val main: GetQuoteFormMainData? = response.body()
                    val mainRes: List<GetQuoteFormMainRes>? = response.body()!!.data!!.quoteList
                    try {

                        if (response.isSuccessful){
                            BindDataToRecyclerViewTP(mainRes)

                            totalPlansAvl.text="Showing "+ mainRes!!.size +" available plan"
                            Log.d("PostAndGethirdparty", "onResponse 1: "+response.raw()+"~"+json.toString())

//                            Toast.makeText(this@CarInsuranceQoutePlanForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("PostAndGethirdparty", "onResponse 2: "+response.raw()+"~"+json.toString())
//                        Toast.makeText(this@CarInsuranceQoutePlanForm, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostAndGethirdparty", "onResponse 3: "+response.raw()+"~"+json.toString())
//                    Toast.makeText(this@CarInsuranceQoutePlanForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            public override fun onFailure(call: Call<GetQuoteFormMainData?>?, t: Throwable) {
                Log.d("PostAndGethirdparty", "onResponse: "+t.localizedMessage+"~"+json.toString())
//                Toast.makeText(this@CarInsuranceQoutePlanForm, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }


    private fun BindDataToRecyclerViewTP(mainRes: List<GetQuoteFormMainRes>?) {
        val adapter = CarInsuranceQuoteAdapter(mainRes,this)
        GetAllQuoteListTP.layoutManager = LinearLayoutManager(this)
        GetAllQuoteListTP.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@BikeInsuranceQoutePlanForm, BikeInsurance::class.java)
        startActivity(intent)

    }


    override fun onConfirmAndProceed(quote: GetQuoteFormMainRes, position: Int, checkedAddOns: List<GetQuoteFormAddOn>) {
        PostCartDetails(quote, checkedAddOns)


        Log.d("CarInsuranceQoutePlanForm", "Confirmed and proceeded with quote at position $position: $quote")
    }


    private fun PostCartDetails(quote: GetQuoteFormMainRes, checkedAddOns: List<GetQuoteFormAddOn>) {
        val json = JSONObject()
        json.put("policyId", quote.id)
        json.put("premium", quote.premium)
        json.put("gst", 18)
        json.put("quoteId", quoteId)
        json.put("IDVCover", quote.iDVCover)
        json.put("NCB", 0)
        json.put("tenure", (planSelected.text.toString().split(" ")[0].toIntOrNull() ?: 0))
        json.put("vehicleSegment", "BIKE")

        val addOnArray = JSONArray()
        checkedAddOns.forEach { addOn ->
            val addOnObject = JSONObject()
            addOnObject.put("addOnName", addOn.addOnName)
            addOnObject.put("addOnPremium", addOn.addOnPremium)
            addOnObject.put("declaration", addOn.declaration)
            addOnObject.put("insId", addOn.insId)
            addOnArray.put(addOnObject)
        }
        json.put("addOn", addOnArray)

        Log.d("PostCartDetails", "Resp : "+json.toString())
        val call: Call<PostCartDataMain?> =
            MyApplicationPort3010.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).postCartDataMain(json.toString())
        call?.enqueue(object : Callback<PostCartDataMain?> {
            override fun onResponse(
                call: Call<PostCartDataMain?>,
                response: retrofit2.Response<PostCartDataMain?>
            ) {
                if (response.isSuccessful()) {
                    val main: PostCartDataMain? = response.body()
                    try {
                        if (response.isSuccessful){

                            val intent = Intent(this@BikeInsuranceQoutePlanForm, ProposalFormBikeInsurance::class.java)
                            intent.putExtra("QuoteId", main?.data?.quoteId.toString())
                            intent.putExtra("vehicleNumber", VehicleNumberS)
                            intent.putExtra("vehicleId", vehicleIdIn)

                            startActivity(intent)

                            Log.d("PostCartDetails", "postRcDetailAndGetRc 1: "+response.raw()+"~"+json.toString()+VehicleNumberS)

                        }
                    } catch (e: Exception) {

                    }
                } else {
                    Log.d("PostCartDetails", "postRcDetailAndGetRc 3: "+response.raw()+"~"+json.toString())
                }
            }

            override fun onFailure(call: Call<PostCartDataMain?>, t: Throwable) {
                Log.d("PostCartDetails", "postRcDetailAndGetRc: "+t.localizedMessage+"~"+json.toString())
            }


        })

    }


}

