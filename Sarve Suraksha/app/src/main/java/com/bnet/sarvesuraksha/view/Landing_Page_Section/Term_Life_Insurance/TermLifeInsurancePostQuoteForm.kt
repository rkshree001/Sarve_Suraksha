package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3006
import com.bnet.sarvesuraksha.model_api.PostQuoteAdditionalDetailMainData
import com.bnet.sarvesuraksha.view.adapter.AnnualIncomeAdapter
import com.bnet.savresuraksha.R
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermLifeInsurancePostQuoteForm : AppCompatActivity(), AnnualIncomeAdapter.OnIncomeSelectedListener {

    private lateinit var listViewAnnualIncome: ListView
    private lateinit var annualIncomeTV: TextView
    private lateinit var educationTV: TextView
    private lateinit var occupationLL: LinearLayout
    private lateinit var annualIncomeLL: LinearLayout
    private lateinit var salaried_ll: LinearLayout
    private lateinit var graduate_and_above_ll: LinearLayout
    private lateinit var self_employed_ll: LinearLayout
    private lateinit var house_wife_ll: LinearLayout
    private lateinit var pass12_ll: LinearLayout
    private lateinit var pass10_ll: LinearLayout
    private lateinit var tick_10pass: LinearLayout
    private lateinit var tick_12pass: LinearLayout
    private lateinit var tick_salaried: LinearLayout
    private lateinit var tick_self_employed: LinearLayout
    private lateinit var tick_house_wife: LinearLayout
    private lateinit var occupation_ind: LinearLayout
    private lateinit var annual_income_LL: LinearLayout
    private lateinit var educationLL: LinearLayout
    private lateinit var tick_graduate_and_above: LinearLayout
    private lateinit var occupationIV: ImageView
    private lateinit var annual_income_ind_TV: TextView
    private lateinit var annual_income_ind_IV: ImageView
    private lateinit var eduLL: LinearLayout
    private lateinit var eduIV: ImageView
    private lateinit var eduTxtView: TextView
    private lateinit var smokeTV: TextView
    private lateinit var smokeLL: LinearLayout
    private lateinit var yes_smoked: LinearLayout
    private lateinit var no_smoked: LinearLayout
    private lateinit var smoke_LL: LinearLayout
    private lateinit var smoke_IV: ImageView
    private lateinit var smoke_TV: TextView

    private var Occupation: String= ""
    private var MemberType: String= ""
    private var Qualification: String= ""
    private var Tobacco: Boolean = false
    private var TermID: String? = ""


    val annualIncomeList = listOf(
        "25 Lac+",
        "15 Lac to 24.9 Lac",
        "10 Lac to 14.9 Lac",
        "8 Lac to 9.9 Lac",
        "5 Lac to 7.9 Lac",
        "3 Lac to 4.9 Lac",
        "2 Lac to 2.9 Lac",
        "Less than 2 Lac"
    )

    private var minIncomeValue: Int = 0
    private var maxIncomeValue: Int = 0
    private lateinit var goBack: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_life_insurance_quote_form)

        listViewAnnualIncome = findViewById(R.id.listViewAnnualIncome)
        annualIncomeTV = findViewById(R.id.annualIncomeTV)
        occupationLL = findViewById(R.id.occupationLL)
        annualIncomeLL = findViewById(R.id.annualIncomeLL)
        salaried_ll = findViewById(R.id.salaried_ll)
        graduate_and_above_ll = findViewById(R.id.graduate_and_above_ll)
        self_employed_ll = findViewById(R.id.self_employed_ll)
        house_wife_ll = findViewById(R.id.house_wife_ll)
        tick_house_wife = findViewById(R.id.tick_house_wife)
        tick_salaried = findViewById(R.id.tick_salaried)
        tick_self_employed = findViewById(R.id.tick_self_employed)
        occupation_ind = findViewById(R.id.occupation_ind)
        occupationIV = findViewById(R.id.occupationIV)
        annual_income_ind_TV = findViewById(R.id.annual_income_ind_TV)
        annual_income_ind_IV = findViewById(R.id.annual_income_ind_IV)
        educationLL = findViewById(R.id.educationLL)
        tick_graduate_and_above = findViewById(R.id.tick_graduate_and_above)
        pass12_ll = findViewById(R.id.pass12_ll)
        tick_12pass = findViewById(R.id.tick_12pass)
        pass10_ll = findViewById(R.id.pass10_ll)
        tick_10pass = findViewById(R.id.tick_10pass)
        educationTV = findViewById(R.id.educationTV)
        annual_income_LL = findViewById(R.id.annual_income_LL)
        eduLL = findViewById(R.id.eduLL)
        eduIV = findViewById(R.id.eduIV)
        eduTxtView = findViewById(R.id.eduTxtView)
        smokeTV = findViewById(R.id.smokeTV)
        smokeLL = findViewById(R.id.smokeLL)
        yes_smoked = findViewById(R.id.yes_smoked)
        no_smoked = findViewById(R.id.no_smoked)
        goBack = findViewById(R.id.goBack)
        smoke_LL = findViewById(R.id.smoke_LL)
        smoke_IV = findViewById(R.id.smoke_IV)
        smoke_TV = findViewById(R.id.smoke_TV)


        TermID = intent.getStringExtra("TermID")

        val adapter = AnnualIncomeAdapter(this, annualIncomeList, this)
        listViewAnnualIncome.adapter = adapter

        salaried_ll.setOnClickListener {
            Occupation="salaried"
            MemberType="self"
            onOccupationClicked(salaried_ll, tick_salaried)
        }

        no_smoked.setOnClickListener {
            Tobacco=false

            PostTermLifeQuote(Tobacco)
        }

        yes_smoked.setOnClickListener {
            Tobacco=true

            PostTermLifeQuote(Tobacco)
        }
        self_employed_ll.setOnClickListener {
            Occupation="self_employed"
            MemberType="self"
            onOccupationClicked(self_employed_ll, tick_self_employed)
        }
        house_wife_ll.setOnClickListener {
            Occupation="house_wife"
            MemberType="husband"
            onOccupationClicked(house_wife_ll, tick_house_wife)
        }

        pass12_ll.setOnClickListener {
            Qualification="12th_pass"
            onEducationClicked(pass12_ll, tick_12pass)
        }
        pass10_ll.setOnClickListener {
            Qualification="10th_pass"
            onEducationClicked(pass10_ll, tick_10pass)
        }
        graduate_and_above_ll.setOnClickListener {
            Qualification="graduate_and_above"
            onEducationClicked(graduate_and_above_ll, tick_graduate_and_above)
        }

        goBack.setOnClickListener {

            if (annualIncomeLL.isVisible){
                occupationLL.visibility = LinearLayout.VISIBLE
                annualIncomeLL.visibility = LinearLayout.GONE

                occupationIV.visibility = View.VISIBLE
                occupation_ind.setBackgroundResource(R.drawable.small_round_icon)

                annual_income_ind_TV.visibility = LinearLayout.VISIBLE
                annual_income_ind_IV.visibility = LinearLayout.GONE

            }else if (occupationLL.isVisible){
                val intent = Intent(this@TermLifeInsurancePostQuoteForm, TermLifeInsurance::class.java)
                startActivity(intent)
            }else if (educationLL.isVisible){
                educationLL.visibility = LinearLayout.GONE
                annualIncomeLL.visibility = LinearLayout.VISIBLE

                annual_income_LL.setBackgroundResource(R.drawable.small_round_icon)
                eduLL.setBackgroundResource(R.drawable.small_round_icon)

                annual_income_ind_IV.visibility = LinearLayout.VISIBLE
                annual_income_ind_TV.visibility = LinearLayout.GONE

                eduIV.visibility = LinearLayout.GONE
                eduTxtView.visibility = LinearLayout.VISIBLE
            }else if (smokeLL.isVisible){
                smokeLL.visibility = LinearLayout.GONE
                educationLL.visibility = LinearLayout.VISIBLE
                eduLL.setBackgroundResource(R.drawable.small_round_icon)
                eduIV.visibility = LinearLayout.VISIBLE
                eduTxtView.visibility = LinearLayout.GONE
                smoke_IV.visibility = LinearLayout.GONE
                smoke_TV.visibility = LinearLayout.VISIBLE


            }
        }
    }

    private fun onOccupationClicked(selectedLayout: LinearLayout, tickLayout: LinearLayout) {
        resetBackgrounds()
        hideAllOccupationTicks()

        selectedLayout.setBackgroundResource(R.drawable.bg_curve_3dp_clicked)
        tickLayout.visibility = LinearLayout.VISIBLE

        if (selectedLayout == house_wife_ll) {
            annualIncomeTV.text = "Husband’s annual income"
            educationTV.text = "Husband’s education qualification"
            smokeTV.text = "Does wife smoked or consumed tobacco in the last 12 months?"
        } else {
            annualIncomeTV.text = "Annual income"
            educationTV.text = "Education qualification"
            smokeTV.text = "Have you smoked or consumed tobacco in the last 12 months?"
        }

        occupationIV.visibility = View.GONE
        occupation_ind.setBackgroundResource(R.drawable.check_circle)

        occupationLL.visibility = LinearLayout.GONE
        annualIncomeLL.visibility = LinearLayout.VISIBLE

        annual_income_ind_TV.visibility = LinearLayout.GONE
        annual_income_ind_IV.visibility = LinearLayout.VISIBLE
    }

    private fun onEducationClicked(selectedLayout: LinearLayout, tickLayout: LinearLayout) {
        resetEducationBackgrounds()
        hideAllEducationTicks()

        selectedLayout.setBackgroundResource(R.drawable.bg_curve_3dp_clicked)
        tickLayout.visibility = LinearLayout.VISIBLE

        educationLL.visibility = LinearLayout.GONE
        smokeLL.visibility=LinearLayout.VISIBLE
        smoke_IV.visibility=LinearLayout.VISIBLE
        smoke_TV.visibility=LinearLayout.GONE

    }

    private fun resetBackgrounds() {
        salaried_ll.setBackgroundResource(R.drawable.bg_curve_3dp_plain_stroke)
        self_employed_ll.setBackgroundResource(R.drawable.bg_curve_3dp_plain_stroke)
        house_wife_ll.setBackgroundResource(R.drawable.bg_curve_3dp_plain_stroke)
    }

    private fun hideAllOccupationTicks() {
        tick_salaried.visibility = LinearLayout.GONE
        tick_self_employed.visibility = LinearLayout.GONE
        tick_house_wife.visibility = LinearLayout.GONE
    }

    private fun resetEducationBackgrounds() {
        pass12_ll.setBackgroundResource(R.drawable.bg_curve_3dp_plain_stroke)
        pass10_ll.setBackgroundResource(R.drawable.bg_curve_3dp_plain_stroke)
        graduate_and_above_ll.setBackgroundResource(R.drawable.bg_curve_3dp_plain_stroke)
    }

    private fun hideAllEducationTicks() {
        tick_12pass.visibility = LinearLayout.GONE
        tick_10pass.visibility = LinearLayout.GONE
        tick_graduate_and_above.visibility = LinearLayout.GONE
        eduIV.visibility = View.GONE
        eduLL.setBackgroundResource(R.drawable.check_circle)
    }

    override fun onIncomeSelected(selectedIncome: String) {
        if (selectedIncome.contains(" to ")) {
            val incomeRange = selectedIncome.split(" to ")
            val minIncome = incomeRange[0].replace(" Lac", "").replace(" ", "").toDoubleOrNull() ?: 0.0
            val maxIncome = incomeRange[1].replace(" Lac", "").replace(" ", "").toDoubleOrNull() ?: 0.0

            minIncomeValue = (minIncome * 100000).toInt()
            maxIncomeValue = (maxIncome * 100000).toInt()
        } else {
            val income = selectedIncome.replace("Less than ", "").replace(" Lac", "").replace("+", "").replace(" ", "").toDoubleOrNull() ?: 0.0
            minIncomeValue = (income * 100000).toInt()
            maxIncomeValue = minIncomeValue
        }

        occupationLL.visibility = View.GONE
        annualIncomeLL.visibility = View.GONE
        educationLL.visibility = View.VISIBLE

        annual_income_LL.setBackgroundResource(R.drawable.check_circle)
        annual_income_ind_TV.visibility = LinearLayout.GONE
        annual_income_ind_IV.visibility = LinearLayout.GONE
        eduTxtView.visibility = LinearLayout.GONE
        eduIV.visibility = LinearLayout.VISIBLE
    }


    private fun PostTermLifeQuote(Tobacco: Boolean) {

        val json = JSONObject().apply {
            put("occupation", Occupation)
            put("annuallIncome", JSONObject().apply {
                put("memberType", MemberType)
                put("max", maxIncomeValue.toString())
                put("min", minIncomeValue.toString())
            })
            put("qualification", Qualification)
            put("tobacco", Tobacco)
        }

        Log.d("PostTermLifeQuote", "Post TermLif eQuote: "+json.toString())
        val call: Call<PostQuoteAdditionalDetailMainData?>? =
            MyApplicationPort3006.getRetrofitClient(this@TermLifeInsurancePostQuoteForm).create<ApiInterface>(
                ApiInterface::class.java
            ).postQuoteAdditionalDetail(TermID.toString(),json.toString())

        call?.enqueue(object : Callback<PostQuoteAdditionalDetailMainData?> {
            override fun onResponse(
                call: Call<PostQuoteAdditionalDetailMainData?>,
                response: Response<PostQuoteAdditionalDetailMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: PostQuoteAdditionalDetailMainData? = response.body()
                    try {
                        if (response.isSuccessful) {
                            Log.d("PostTermLifeQuote", "onResponse 1: " + response.raw() + "~" + json.toString())
                            TermID=main!!.data!!.id

                            Toast.makeText(this@TermLifeInsurancePostQuoteForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@TermLifeInsurancePostQuoteForm, TermLifeInsuranceGetQuoteFormList::class.java)
                            intent.putExtra("TermID", TermID)

                            startActivity(intent)


                            Toast.makeText(this@TermLifeInsurancePostQuoteForm, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            
                        }
                    } catch (e: Exception) {
                        Log.d("PostTermLifeQuote", "onResponse 2: " + response.raw() + "~" + json.toString())
                        Toast.makeText(this@TermLifeInsurancePostQuoteForm, e.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostTermLifeQuote", "onResponse 3: " + response.raw() + "~" + json.toString())
                    Toast.makeText(this@TermLifeInsurancePostQuoteForm, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostQuoteAdditionalDetailMainData?>?, t: Throwable) {
                Log.d("PostTermLifeQuote", "onFailure: " + t.localizedMessage + "~" + json.toString())
                Toast.makeText(this@TermLifeInsurancePostQuoteForm, "ServerError " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
}
