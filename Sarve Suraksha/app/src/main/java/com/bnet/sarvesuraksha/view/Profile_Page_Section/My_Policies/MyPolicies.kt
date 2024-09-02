package com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Policies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3009
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.model_api.MyPoliciesMainData
import com.bnet.sarvesuraksha.model_api.PolicyData
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import com.bnet.sarvesuraksha.view.adapter.CarouselAdapter
import com.bnet.sarvesuraksha.view.adapter.PoliciesAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

class MyPolicies : AppCompatActivity(), PoliciesAdapter.OnPolicyClickListener {

    private lateinit var managePolicyRecyclerView: RecyclerView
    private lateinit var goBack: LinearLayout
    private lateinit var policiesAdapter: PoliciesAdapter
    private lateinit var viewPagerRecommended: ViewPager2
    private lateinit var indicator1: LinearLayout
    private lateinit var indicator2: LinearLayout
    private lateinit var indicator3: LinearLayout
    private val handler = Handler(Looper.getMainLooper())

    private val update = object : Runnable {
        override fun run() {
            val nextItem = (viewPagerRecommended.currentItem + 1) % (viewPagerRecommended.adapter?.itemCount ?: 1)
            viewPagerRecommended.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_policies)

        managePolicyRecyclerView = findViewById(R.id.managePolicyRecyclerView)
        goBack = findViewById(R.id.goBack)
        viewPagerRecommended = findViewById(R.id.viewPagerRecommended)
        indicator1 = findViewById(R.id.indicator1)
        indicator2 = findViewById(R.id.indicator2)
        indicator3 = findViewById(R.id.indicator3)

        managePolicyRecyclerView.layoutManager = LinearLayoutManager(this)
        policiesAdapter = PoliciesAdapter(this, emptyList(), this)
        managePolicyRecyclerView.adapter = policiesAdapter

        val images = listOf(
            R.drawable.recommand1,
            R.drawable.recommand2,
            R.drawable.recommand3
        )

        val adapter = CarouselAdapter(images)
        viewPagerRecommended.adapter = adapter

        viewPagerRecommended.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
            }
        })


        handler.postDelayed(update, 2000)

        GetAllMyPurchasePolicyData("7873332700")

        goBack.setOnClickListener {
            startActivity(Intent(this, ProfilePage::class.java))
            finish()
        }
    }

    private fun updateIndicators(position: Int) {

        indicator1.visibility = View.INVISIBLE
        indicator2.visibility = View.INVISIBLE
        indicator3.visibility = View.INVISIBLE


        when (position % 3) {
            0 -> indicator1.visibility = View.VISIBLE
            1 -> indicator2.visibility = View.VISIBLE
            2 -> indicator3.visibility = View.VISIBLE
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(update)
    }

    private fun GetAllMyPurchasePolicyData(mobileNumberString: String) {
        if (!APIClient.isNetworkAvailable(this@MyPolicies)) {
            Toast.makeText(this@MyPolicies, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetPolicyPurchaseData", "Request : $mobileNumberString")
        val call: Call<MyPoliciesMainData?>? =
            MyApplicationPort3009.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getAllMyPurchasePolicyData(mobileNumberString)
        call?.enqueue(object : Callback<MyPoliciesMainData?> {
            override fun onResponse(
                call: Call<MyPoliciesMainData?>,
                response: retrofit2.Response<MyPoliciesMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: MyPoliciesMainData? = response.body()
                    main?.let {
                        Log.d("GetPolicyPurchaseData", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")

                        val policiesWithCount = mutableListOf<PolicyItem>()

                        main.data?.healthInsurance?.let {
                            policiesWithCount.add(PolicyItem("Health", it.active ?: 0, it.expired ?: 0, PolicyData.Health(it)))
                        }

                        main.data?.travelInsurance?.let {
                            policiesWithCount.add(PolicyItem("Travel", it.active ?: 0, it.expired ?: 0, PolicyData.Travel(it)))
                        }

                        main.data?.vehicleInsurance?.let {
                            policiesWithCount.add(PolicyItem("Vehicle", it.active ?: 0, it.expired ?: 0, PolicyData.Vehicle(it)))
                        }

                        policiesAdapter.updatePolicies(policiesWithCount)
                    }
                } else {
                    Log.d("GetPolicyPurchaseData", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@MyPolicies, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MyPoliciesMainData?>?, t: Throwable) {
                Log.e("GetPolicyPurchaseData", "Failed to get user details", t)
                Toast.makeText(this@MyPolicies, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ProfilePage::class.java))
        finish()
    }

    data class PolicyItem(
        val policyType: String,
        val activeCount: Int,
        val expiredCount: Int,
        val data: PolicyData
    )

    override fun onPolicyClick(policyType: String, policy: PolicyItem) {
        val intent = Intent(this, MyPoliciesDetailedDetails::class.java)
        val gson = Gson()
        val policyDataJson = gson.toJson(policy.data)

        Log.d("MyPolicies", "Policy Type: $policyType")
        Log.d("MyPolicies", "Policy Data JSON: $policyDataJson")

        intent.putExtra("policyData", policyDataJson)
        intent.putExtra("policyName", policyType)
        startActivity(intent)
    }
}
