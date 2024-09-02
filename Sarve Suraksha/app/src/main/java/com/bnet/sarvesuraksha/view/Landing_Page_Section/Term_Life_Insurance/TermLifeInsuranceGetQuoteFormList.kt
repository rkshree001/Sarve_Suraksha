package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3006
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListAddOndetail
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListMainData
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListMainGet
import com.bnet.sarvesuraksha.model_api.PostSummaryDetailsTLMainData
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Landing_Page.LandingPage
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance.Adapters.TermLifeInsuranceQuoteAdapter
import com.bnet.savresuraksha.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.skydoves.powerspinner.PowerSpinnerView
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermLifeInsuranceGetQuoteFormList : AppCompatActivity(),
    TermLifeInsuranceQuoteAdapter.OnConfirmAndProceedClickListener {
    private lateinit var GetAllQuoteList:RecyclerView
    private lateinit var lifeCoveLL:LinearLayout
    private lateinit var allFilters:LinearLayout
    private lateinit var noDataFoundLL:LinearLayout
    private lateinit var coverageTilLL:LinearLayout
    private lateinit var paymentType:LinearLayout
    private lateinit var selectedLifeCoverTV:TextView
    private lateinit var coverageTilTV:TextView
    private lateinit var paymentTypeTV:TextView
//    private var TermID: String? = "66a4f54985ecb6115a1110e3"
    private var TermID: String? = ""

    private var sortBy: String? = "premium_high_to_low"
    private var paymentFrequency: String? = "monthly"
    private var planType: String? = "base_plan"
    private var sumAssured: Int? = 6000000
    private var coverUpto: Int? = 60
    private var paymentTenure: Int? = 43
    private var addOnDetail: JSONArray = JSONArray()
    private var insurerName: JSONArray = JSONArray()
    private var selectedItem = ""
    private var sumAssuredValue: Int = 0
    private lateinit var goBack: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_life_insurance_get_quote_form_list)
        TermID = intent.getStringExtra("TermID")
        GetAllQuoteList=findViewById(R.id.GetAllQuoteList)
        lifeCoveLL=findViewById(R.id.lifeCoveLL)
        noDataFoundLL=findViewById(R.id.noDataFoundLL)
        selectedLifeCoverTV=findViewById(R.id.selectedLifeCoverTV)
        coverageTilTV=findViewById(R.id.coverageTilTV)
        coverageTilLL=findViewById(R.id.coverageTilLL)
        paymentType=findViewById(R.id.paymentType)
        paymentTypeTV=findViewById(R.id.paymentTypeTV)
        allFilters=findViewById(R.id.allFilters)
        goBack = findViewById(R.id.goBack)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        goBack.setOnClickListener {
            val intent = Intent(this@TermLifeInsuranceGetQuoteFormList, TermLifeInsurance::class.java)
            startActivity(intent)
        }


        lifeCoveLL.setOnClickListener {
            ShowLifeCoverBTS()
        }

        coverageTilLL.setOnClickListener {
            ShowCoverageTillBTS()
        }
        paymentType.setOnClickListener {
            ShowpaymentTypeBTS()
        }
        allFilters.setOnClickListener {
            val intent = Intent(this@TermLifeInsuranceGetQuoteFormList, TermLifeAllFilters::class.java)
            intent.putExtra("TermID", TermID)


            startActivity(intent)


        }

        val intentType = intent.getStringExtra("Type")
        if (intentType == "Filter") {


            val selectedSortBys = intent.getStringArrayListExtra("SelectedSortBy")
            val selectedPlanTenures = intent.getStringArrayListExtra("SelectedPlanTenure")
            val selectedPaymentTenures = intent.getStringArrayListExtra("SelectedPlanType")
            val selectedInsurers = intent.getStringArrayListExtra("SelectedInsurer")

            Log.d("TLInsuPlanForm", "Selected Sort By: $selectedSortBys")
            Log.d("TLInsuPlanForm", "Selected Plan Tenure: $selectedPlanTenures")
            Log.d("TLInsuPlanForm", "Selected Plan Type: $selectedPaymentTenures")
            Log.d("TLInsuPlanForm", "Selected Insurer: $selectedInsurers")

            val sortMapping = mapOf(
                "Premium low to high" to "premium_low_to_high",
                "Premium high to low" to "premium_high_to_low",
                "Recommended" to "recommended"
            )

            val planTenureMapping = mapOf(
                "Return of premium" to "return_premium",
                "Whole life (Cover upto 100 years)" to "whole_life",
                "Zero cost plan" to "zero_cost_plan"
            )

            val paymentTenureMapping = mapOf(
                "Pay for 5 Years" to 5,
                "Pay for 7 Years" to 7,
                "Pay for 10 Years" to 10,
                "Pay for 12 Years" to 12,
                "Pay for 15 Years" to 15,
                "Pay for 23 Years" to 23
            )


            val mappedSortBy = selectedSortBys?.mapNotNull { sortMapping[it] }?.firstOrNull().toString()
            var mappedPlanTenure = selectedPlanTenures?.mapNotNull { paymentTenureMapping[it] }?.firstOrNull() ?: 43
            var mappedPlanType = selectedPaymentTenures?.mapNotNull { planTenureMapping[it] }?.firstOrNull().toString()
            val mappedInsurerNames = selectedInsurers ?: arrayListOf()
            if (mappedPlanType.isNullOrEmpty() || mappedPlanType == "null") {
                mappedPlanType = "base_plan"
                planType=mappedPlanType
            }


            if (mappedPlanTenure.equals("null") || mappedPlanTenure == null) {
                mappedPlanTenure = 43
            }

            paymentTenure=mappedPlanTenure
            Log.d("TLInsuPlanForm", "Mapped Sort By: $mappedSortBy")
            Log.d("TLInsuPlanForm", "Mapped Plan Tenure: $mappedPlanTenure")
            Log.d("TLInsuPlanForm", "Mapped Plan Type: $mappedPlanType")
            Log.d("TLInsuPlanForm", "Mapped Insurers: $mappedInsurerNames")



            val mappedInsurerJSONArray = JSONArray(mappedInsurerNames)

            GetQouteListTermLife(
                TermID,
                mappedSortBy,
                sumAssured,
                coverUpto,
                mappedPlanTenure,
                paymentFrequency,
                mappedPlanType,
                addOnDetail,
                mappedInsurerJSONArray
            )
//            mappedInsurerNames.joinToString(",")
        } else {
            GetQouteListTermLife(
                TermID,
                sortBy,
                sumAssured,
                coverUpto,
                paymentTenure,
                paymentFrequency,
                planType,
                addOnDetail,
                insurerName
            )
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@TermLifeInsuranceGetQuoteFormList, TermLifeInsurance::class.java)
        startActivity(intent)
        finish()
    }
    private fun GetQouteListTermLife(
        TermID: String?,
        sortBy: String?,
        sumAssured: Int?,
        coverUpto: Int?,
        paymentTenure: Int?,
        paymentFrequency: String?,
        planType: String?,
        addOnDetail: JSONArray,
        insurerName: JSONArray
    ) {
        val json = JSONObject()

        json.put("sortBy", sortBy)
        json.put("sumAssured", sumAssured)
        json.put("coverUpto", coverUpto)
        json.put("paymentTenure", paymentTenure)
        json.put("paymentFrequency", paymentFrequency)
        json.put("planType", planType)
        json.put("insurerName", insurerName)
        json.put("addOnDetail", addOnDetail)

        Log.d("GetQouteListTermLife", "Resp : $json")

        val call: Call<GetTermLifeQuoteListMainData?> = MyApplicationPort3006.getRetrofitClient(this)
            .create<ApiInterface>(ApiInterface::class.java)
            .postAndGetTLQuote(TermID.toString(),json.toString())

        call?.enqueue(object : Callback<GetTermLifeQuoteListMainData?> {
            override fun onResponse(
                call: Call<GetTermLifeQuoteListMainData?>,
                response: retrofit2.Response<GetTermLifeQuoteListMainData?>
            ) {
                if (response.isSuccessful) {
                    val mainRes: List<GetTermLifeQuoteListMainGet>? = response.body()?.data
                    try {
                        if (mainRes != null) {
                            if (mainRes.size>1){
                                GetAllQuoteList.visibility=View.VISIBLE
                                noDataFoundLL.visibility=View.GONE
                                BindDataToRecyclerView(mainRes)
                            }else{
                                GetAllQuoteList.visibility=View.GONE
                                noDataFoundLL.visibility=View.VISIBLE
                            }

                            Log.d("GetQouteListTermLife", "onResponse1: ${response.raw()}~$json"+mainRes.size)
                        }
                    } catch (e: Exception) {
                        Log.d("GetQouteListTermLife", "onResponse2: ${response.raw()}~$json")
                    }
                } else {
                    Log.d("GetQouteListTermLife", "onResponse3: ${response.raw()}~$json")
                }
            }

            override fun onFailure(call: Call<GetTermLifeQuoteListMainData?>?, t: Throwable) {
                Log.d("GetQouteListTermLife", "onResponse: ${t.localizedMessage}~$json")
            }
        })
    }

//    private fun ShowLifeCoverBTS() {
//        val bottomSheetFragment = LifeCoverBottomSheetFragment()
//        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
//    }


    private fun ShowLifeCoverBTS() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.life_cover_bottomsheet, null)

        setupBottomSheetViewIDV(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        bottomSheetDialog.show()
    }
    private fun setupBottomSheetViewIDV(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        val closeIcon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val lifeCoverACTV = bottomSheetView.findViewById<AutoCompleteTextView>(R.id.lifeCoverACTV)
        val dropdownArrow = bottomSheetView.findViewById<ImageView>(R.id.dropdownArrow)
        val lifeCoverSpinner = bottomSheetView.findViewById<PowerSpinnerView>(R.id.lifeCoverSpinner)
        val updateLifeCover = bottomSheetView.findViewById<LinearLayout>(R.id.updateLifeCover)

        val lifeCoverList = listOf("20 Lakh", "25 Lakh", "30 Lakh", "35 Lakh", "40 Lakh", "45 Lakh", "50 Lakh",
            "55 Lakh", "60 Lakh", "65 Lakh", "70 Lakh", "75 Lakh", "80 Lakh", "85 Lakh", "90 Lakh", "95 Lakh", "1 CR")
        val simpleAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lifeCoverList)
        lifeCoverACTV.setAdapter(simpleAdapter)
        lifeCoverSpinner.setItems(lifeCoverList)

        lifeCoverACTV.setOnClickListener {
            lifeCoverACTV.showDropDown()
            toggleArrow(true, dropdownArrow)
        }

        lifeCoverACTV.setOnDismissListener {
            toggleArrow(false, dropdownArrow)
        }

        lifeCoverACTV.setOnItemClickListener { parent, view, position, id ->
            Log.d("AutoCompleteTextView", "Item clicked: position=$position, id=$id")
            selectedItem = parent.getItemAtPosition(position) as String
            Log.d("SelectedItem", "Item selected: $selectedItem")
            lifeCoverACTV.setText(selectedItem, false)
            lifeCoverACTV.clearFocus()
            lifeCoverACTV.dismissDropDown()
            toggleArrow(false, dropdownArrow)

        }

        updateLifeCover.setOnClickListener {
            val selectedSumAssured = lifeCoverSpinner.text.toString()
            sumAssured = convertToNumericValue(selectedSumAssured)
            GetQouteListTermLife(TermID, sortBy, sumAssured, coverUpto, paymentTenure, paymentFrequency, planType, addOnDetail, insurerName)
            selectedLifeCoverTV.text="₹ "+selectedSumAssured
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        closeIcon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    private fun ShowCoverageTillBTS() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.coverage_till_bottomsheet, null)

        setupBottomSheetViewCT(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        bottomSheetDialog.show()
    }


    private fun setupBottomSheetViewCT(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        val closeIcon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val lifeCoverSpinner = bottomSheetView.findViewById<PowerSpinnerView>(R.id.coverageTillSpinner)
        val updateLifeCover = bottomSheetView.findViewById<LinearLayout>(R.id.updateLifeCover)

        val coverageTill = (32..100).map { "$it years" }

        lifeCoverSpinner.setItems(coverageTill)


        updateLifeCover.setOnClickListener {
            val selectedYears = lifeCoverSpinner.text.toString()
            coverUpto = selectedYears.split(" ")[0].toInt()

            GetQouteListTermLife(TermID, sortBy, sumAssured, coverUpto, paymentTenure, paymentFrequency, planType, addOnDetail, insurerName)

            coverageTilTV.text=""+selectedYears
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        closeIcon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }


    private fun ShowpaymentTypeBTS() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.payment_type_bottomsheet, null)

        setupBottomSheetViewPT(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        bottomSheetDialog.show()
    }


    private fun setupBottomSheetViewPT(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        val closeIcon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val updateLifeCover = bottomSheetView.findViewById<LinearLayout>(R.id.updateLifeCover)
        val paymentTypeRadioGroup = bottomSheetView.findViewById<RadioGroup>(R.id.paymentTypeRadioGroup)

        paymentTypeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            paymentFrequency = when (checkedId) {
                R.id.monthlyRadioBtn -> "monthly"
                R.id.quartelyRadioBtn -> "quarterly"
                R.id.halfYearlyRadioBtn -> "halfYearly"
                R.id.yearlyRadioBtn -> "yearly"
                else -> ""
            }

            paymentTypeTV.text = when (paymentFrequency) {
                "monthly" -> "Monthly"
                "quarterly" -> "Quarterly"
                "halfYearly" -> "Half Yearly"
                "yearly" -> "Yearly"
                else -> ""
            }
        }

        updateLifeCover.setOnClickListener {
            GetQouteListTermLife(TermID, sortBy, sumAssured, coverUpto, paymentTenure, paymentFrequency, planType, addOnDetail, insurerName)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        closeIcon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }




    private fun toggleArrow(isDropdownOpen: Boolean, dropdownArrow: ImageView) {
        val arrowDrawable = if (isDropdownOpen) {
            R.drawable.arrow_up_dd
        } else {
            R.drawable.arrow_down_dd
        }
        dropdownArrow.setImageResource(arrowDrawable)
    }


    private fun convertToNumericValue(value: String): Int {
        val parts = value.split(" ")
        if (parts.size == 2) {
            val amount = parts[0].toIntOrNull()
            if (amount != null) {
                return when (parts[1]) {
                    "Lakh" -> amount * 100000
                    "CR" -> amount * 10000000
                    else -> throw IllegalArgumentException("Invalid sum assured unit: ${parts[1]}")
                }
            } else {
                throw IllegalArgumentException("Invalid sum assured amount: ${parts[0]}")
            }
        }
        throw IllegalArgumentException("Invalid sum assured format: $value")
    }
    private fun BindDataToRecyclerView(mainRes: List<GetTermLifeQuoteListMainGet>?) {
        val adapter = TermLifeInsuranceQuoteAdapter(mainRes,paymentFrequency.toString(),this)
        GetAllQuoteList.layoutManager = LinearLayoutManager(this)
        GetAllQuoteList.adapter = adapter
    }

    override fun onConfirmAndProceed(
        quote: GetTermLifeQuoteListMainGet,
        position: Int,
        checkedAddOns: MutableList<GetTermLifeQuoteListAddOndetail>,
        claimSettled: String?,
        insurerImg: String?
    ) {
        PostSummaryDetails(quote,checkedAddOns,claimSettled,insurerImg)
    }

    private fun PostSummaryDetails(
        quote: GetTermLifeQuoteListMainGet?,
        checkedAddOns: MutableList<GetTermLifeQuoteListAddOndetail>,
        claimSettled: String?,
        insurerImg: String?
    ) {
        val json = JSONObject()
        json.put("quoteId", TermID)
        json.put("insuranceId", quote!!.id)
        json.put("sumAssured", quote!!.sumAssured)
        json.put("age", 20)
        json.put("coverUpto", coverUpto)
        json.put("paymentTenure", paymentTenure)
        json.put("paymentFrequency", paymentFrequency)
        json.put("planType", planType)
        json.put("addOnDetail", addOnDetail)
        json.put("gst", 18)
        json.put("premium", quote!!.premium)
        json.put("discount", quote!!.discount)

        Log.d("PostSummaryDetails", "Request JSON: $json")

        val call: Call<PostSummaryDetailsTLMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@TermLifeInsuranceGetQuoteFormList).create<ApiInterface>(
                ApiInterface::class.java
            ).postSummaryDetailsTL(json.toString())

        call?.enqueue(object : Callback<PostSummaryDetailsTLMainData?> {
            override fun onResponse(
                call: Call<PostSummaryDetailsTLMainData?>,
                response: Response<PostSummaryDetailsTLMainData?>
            ) {
                if (response.isSuccessful) {
                    val mains: PostSummaryDetailsTLMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostSummaryDetails", "onResponse 1: " + response.raw() + "~" + json.toString())
                            Toast.makeText(this@TermLifeInsuranceGetQuoteFormList, response.body()!!.message, Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@TermLifeInsuranceGetQuoteFormList, ViewPlanDetails::class.java)
                            intent.putExtra("MainData", mains)
                            intent.putExtra("claimSettled", claimSettled)
                            intent.putExtra("insurerImg", insurerImg)
                            intent.putExtra("planType", planType)
                            intent.putExtra("TermID", TermID)
                            startActivity(intent)

                            startActivity(intent)


                        }
                    } catch (e: Exception) {
                        Log.d("PostSummaryDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@TermLifeInsuranceGetQuoteFormList, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("PostSummaryDetails", "onResponse: "+e.localizedMessage.toString())
                    }
                } else {
                    Log.d("PostSummaryDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@TermLifeInsuranceGetQuoteFormList, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostSummaryDetailsTLMainData?>?, t: Throwable) {
                Log.d("PostSummaryDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@TermLifeInsuranceGetQuoteFormList, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}