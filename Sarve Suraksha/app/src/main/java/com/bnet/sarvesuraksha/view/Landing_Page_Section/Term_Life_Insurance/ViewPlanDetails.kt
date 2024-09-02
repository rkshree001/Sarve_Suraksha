package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3006
import com.bnet.sarvesuraksha.model_api.PostCartDataMainTL
import com.bnet.sarvesuraksha.model_api.PostSummaryDetailsTLMainData

import com.bnet.savresuraksha.R
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class ViewPlanDetails : AppCompatActivity() {
    private lateinit var priceBreakUp: TextView
    private lateinit var confirmAndProceed: LinearLayout
    private lateinit var paidBenetilsLL: LinearLayout
    private lateinit var paidBenetilsInfoLL: LinearLayout
    private lateinit var freeBenetilsLL: LinearLayout
    private lateinit var freeBenetilsInfoLL: LinearLayout
    private lateinit var otherFeaturesLL: LinearLayout
    private lateinit var otherFeaturesInfoLL: LinearLayout
    private lateinit var aboutInsuresLL: LinearLayout
    private lateinit var aboutInsurerInfoLL: LinearLayout
    private lateinit var PlanbrochureLL: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var PlanbrochureInfoLL: LinearLayout
    private lateinit var freeArrowImageLL: ImageView
    private lateinit var paidArrowImageLL: ImageView
    private lateinit var othfeaturesArrowImageLL: ImageView
    private lateinit var aboutInsurerArrowImageLL: ImageView
    private lateinit var PlanbrochureArrowImageLL: ImageView
    private lateinit var life_cover: TextView
    private lateinit var totalAmount: TextView
    private lateinit var policy_image: ImageView
    private lateinit var cover_upto: TextView
    private lateinit var claim_settled: TextView
    private var TermID: String? = ""
    private val rotationStates = mutableMapOf<ImageView, Float>()
    private var addOnDetail: JSONArray = JSONArray()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_plan_details)
        TermID = intent.getStringExtra("TermID")
        priceBreakUp = findViewById(R.id.priceBreakUp)
        paidBenetilsLL = findViewById(R.id.paidBenetilsLL)
        paidBenetilsInfoLL = findViewById(R.id.paidBenetilsInfoLL)
        freeBenetilsLL = findViewById(R.id.freeBenetilsLL)
        freeBenetilsInfoLL = findViewById(R.id.freeBenetilsInfoLL)
        freeArrowImageLL = findViewById(R.id.freeArrowImageLL)
        paidArrowImageLL = findViewById(R.id.paidArrowImageLL)
        otherFeaturesLL = findViewById(R.id.otherFeaturesLL)
        othfeaturesArrowImageLL = findViewById(R.id.othfeaturesArrowImageLL)
        otherFeaturesInfoLL = findViewById(R.id.otherFeaturesInfoLL)
        aboutInsuresLL = findViewById(R.id.aboutInsuresLL)
        aboutInsurerInfoLL = findViewById(R.id.aboutInsurerInfoLL)
        aboutInsurerArrowImageLL = findViewById(R.id.aboutInsurerArrowImageLL)
        PlanbrochureLL = findViewById(R.id.PlanbrochureLL)
        PlanbrochureArrowImageLL = findViewById(R.id.PlanbrochureArrowImageLL)
        PlanbrochureInfoLL = findViewById(R.id.PlanbrochureInfoLL)
        life_cover = findViewById(R.id.life_cover)
        cover_upto = findViewById(R.id.cover_upto)
        claim_settled = findViewById(R.id.claim_settled)
        totalAmount = findViewById(R.id.claim_settled)
        policy_image = findViewById(R.id.policy_image)
        goBack = findViewById(R.id.goBack)
        confirmAndProceed = findViewById(R.id.confirmAndProceed)

        goBack.setOnClickListener {
            val intent = Intent(this@ViewPlanDetails, TermLifeInsuranceGetQuoteFormList::class.java)
            intent.putExtra("TermID", TermID)
            startActivity(intent)

        }

        val mSpannableString = SpannableString("Price Breakup")
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        priceBreakUp.text = mSpannableString

        rotationStates[paidArrowImageLL] = 0f
        rotationStates[freeArrowImageLL] = 0f
        rotationStates[othfeaturesArrowImageLL] = 0f
        rotationStates[aboutInsurerArrowImageLL] = 0f
        rotationStates[PlanbrochureArrowImageLL] = 0f

        fun toggleVisibilityAndRotation(targetLL: LinearLayout, arrowImage: ImageView) {
            val isVisible = targetLL.visibility == View.VISIBLE
            targetLL.visibility = if (isVisible) View.GONE else View.VISIBLE
            val newRotation = if (isVisible) 0f else 180f
            rotateArrow(arrowImage, newRotation)
        }

        paidBenetilsLL.setOnClickListener {
            toggleVisibilityAndRotation(paidBenetilsInfoLL, paidArrowImageLL)
        }

        freeBenetilsLL.setOnClickListener {
            toggleVisibilityAndRotation(freeBenetilsInfoLL, freeArrowImageLL)
        }

        otherFeaturesLL.setOnClickListener {
            toggleVisibilityAndRotation(otherFeaturesInfoLL, othfeaturesArrowImageLL)
        }

        aboutInsuresLL.setOnClickListener {
            toggleVisibilityAndRotation(aboutInsurerInfoLL, aboutInsurerArrowImageLL)
        }

        PlanbrochureLL.setOnClickListener {
            toggleVisibilityAndRotation(PlanbrochureInfoLL, PlanbrochureArrowImageLL)
        }
        priceBreakUp.setOnClickListener {
            ShowSummaryBottomSheet()
        }

        val mainData = intent.getParcelableExtra<PostSummaryDetailsTLMainData>("MainData")
        val claimSettled = intent.getStringExtra("claimSettled")
        val insurerImg = intent.getStringExtra("insurerImg")
        val planType = intent.getStringExtra("planType")

        if (mainData != null) {
            life_cover.text = formatToIndianCurrency(mainData.data?.summaryDetail?.sumAssured)
            val totalPremium = mainData.data?.summaryDetail?.totalPremium ?: 0.0

            val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
            val formattedTotalPremium = currencyFormat.format(totalPremium)
            totalAmount.text = formattedTotalPremium
            Glide.with(policy_image).load(insurerImg).into(policy_image)

            cover_upto.text = mainData.data?.summaryDetail?.coverUpto?.toString()+" years" ?: "N/A"
            claim_settled.text = claimSettled+"%" ?: "N/A"
        } else {

        }
        confirmAndProceed.setOnClickListener {
            PostSummaryCartDetails(mainData,planType)

        }
    }
    private fun ShowSummaryBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.term_life_bottom_sheet, null)

        setupBottomSheetViewNoNocb(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()


    }


    private fun formatToIndianCurrency(amount: Int?): String {
        return if (amount != null) {
            when {
                amount >= 1_00_00_000 -> {
                    val crorePart = amount / 1_00_00_000
                    "₹ $crorePart Crore"
                }
                amount >= 1_00_000 -> {
                    val lakhPart = amount / 1_00_000
                    "₹ $lakhPart Lakh"
                }
                else -> {
                    "₹ ${DecimalFormat("#,##,###").format(amount)}"
                }
            }
        } else {
            "₹0"
        }
    }
    private fun setupBottomSheetViewNoNocb(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {


        val close_icon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val cover = bottomSheetView.findViewById<TextView>(R.id.cover)
        val coverUpTo = bottomSheetView.findViewById<TextView>(R.id.coverUpTo)
        val paymentType = bottomSheetView.findViewById<TextView>(R.id.paymentType)
        val paymentTenure = bottomSheetView.findViewById<TextView>(R.id.paymentTenure)
        val payableAmount = bottomSheetView.findViewById<TextView>(R.id.payableAmount)
        val basePremium = bottomSheetView.findViewById<TextView>(R.id.basePremium)
        val gstPercentage = bottomSheetView.findViewById<TextView>(R.id.gstpercentage)
        val addOnsLL = bottomSheetView.findViewById<LinearLayout>(R.id.addOnsLL)
        val policyName = bottomSheetView.findViewById<TextView>(R.id.policyName)
        val policy_image = bottomSheetView.findViewById<ImageView>(R.id.policy_image)

        val mainData = intent.getParcelableExtra<PostSummaryDetailsTLMainData>("MainData")
        val claimSettled = intent.getStringExtra("claimSettled")
        val insurerImg = intent.getStringExtra("insurerImg")
        val planType = intent.getStringExtra("planType")



        if (mainData != null) {

            cover.text = formatToIndianCurrency(mainData.data?.summaryDetail?.sumAssured?.toDouble())

            val totalPremium = mainData.data?.summaryDetail?.totalPremium ?: 0.0
            val gstAmount = mainData.data?.summaryDetail?.gstAmount ?: 0.0

            payableAmount.text = formatToIndianCurrency(totalPremium)
            basePremium.text = formatToIndianCurrency(totalPremium)
            gstPercentage.text = formatToIndianCurrency(gstAmount)

            coverUpTo.text = "${mainData.data?.summaryDetail?.coverUpto} years" ?: "N/A"
            paymentType.text = mainData.data?.summaryDetail?.paymentType ?: "N/A"
            paymentTenure.text = "${mainData.data?.summaryDetail?.paymentTenure} years" ?: "N/A"

            policyName.text=mainData.data?.insuranceDetail?.insurerName.toString()+" "+mainData.data?.insuranceDetail?.planName.toString()

            if (mainData.data?.summaryDetail?.addOnDetail?.size ?: 0 >= 1) {
                addOnsLL.visibility = View.VISIBLE
            } else {
                addOnsLL.visibility = View.GONE
            }
            Glide.with(policy_image).load(insurerImg).into(policy_image)



        } else {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show()
        }

        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.setCancelable(false)

        close_icon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    private fun formatToIndianCurrency(amount: Double?): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN")) as DecimalFormat
        currencyFormat.applyPattern("₹#,##0.00") // Indian currency format with two decimal places
        return currencyFormat.format(amount ?: 0.0)
    }

    private fun rotateArrow(imageView: ImageView, degrees: Float) {
        imageView.animate()
            .rotation(degrees)
            .setDuration(300)
            .start()
        rotationStates[imageView] = degrees
    }

    private fun PostSummaryCartDetails(
        quote: PostSummaryDetailsTLMainData?,
        planType: String?
    ) {
        val json = JSONObject()
        json.put("quoteId", TermID)
        json.put("insuranceId", quote!!.data!!.insuranceDetail!!.id)
        json.put("planType", planType)
        json.put("sumAssured", quote!!.data!!.summaryDetail!!.sumAssured)
        json.put("coverUpto", quote!!.data!!.summaryDetail!!.coverUpto)
        json.put("paymentType", quote!!.data!!.summaryDetail!!.paymentType)
        json.put("addOnDetail", addOnDetail)
        json.put("GST", quote!!.data!!.summaryDetail!!.gst)
        json.put("gstAmount", quote!!.data!!.summaryDetail!!.gstAmount)
        json.put("payableAmount", quote!!.data!!.summaryDetail!!.payableAmount)
        json.put("paymentFrequency", quote!!.data!!.summaryDetail!!.paymentType)
        json.put("premium", quote!!.data!!.summaryDetail!!.totalPremium)
        json.put("totalPremium", quote!!.data!!.summaryDetail!!.totalPremium)
        json.put("gst", quote!!.data!!.summaryDetail!!.gst)
        json.put("paymentTenure", quote!!.data!!.summaryDetail!!.paymentTenure)
        json.put("discount",0)



        Log.d("PostSummaryCartDetails", "Request JSON: $json")

        val call: Call<PostCartDataMainTL?>? =
            MyApplicationPort3006.getRetrofitClient(this@ViewPlanDetails).create<ApiInterface>(
                ApiInterface::class.java
            ).postCartDataMainTL(json.toString())

        call?.enqueue(object : Callback<PostCartDataMainTL?> {
            override fun onResponse(
                call: Call<PostCartDataMainTL?>,
                response: Response<PostCartDataMainTL?>
            ) {
                if (response.isSuccessful) {
                    val mains: PostCartDataMainTL? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostSummaryCartDetails", "onResponse 1: " + response.raw() + "~" + json.toString()+"~~~"+mains!!.data!!.quoteId)
                            Toast.makeText(this@ViewPlanDetails, response.body()!!.message, Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@ViewPlanDetails, ProposalDetailsTermLife::class.java)
                            intent.putExtra("TermID", TermID)
                            intent.putExtra("QuoteId", mains!!.data!!.quoteId)
                            startActivity(intent)



                        }
                    } catch (e: Exception) {
                        Log.d("PostSummaryCartDetails", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@ViewPlanDetails, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("PostSummaryCartDetails", "onResponse: "+e.localizedMessage.toString())
                    }
                } else {
                    Log.d("PostSummaryCartDetails", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@ViewPlanDetails, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostCartDataMainTL?>?, t: Throwable) {
                Log.d("PostSummaryCartDetails", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@ViewPlanDetails, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ViewPlanDetails, TermLifeInsuranceGetQuoteFormList::class.java)
        intent.putExtra("TermID", TermID)
        startActivity(intent)

        finish()
    }
}
