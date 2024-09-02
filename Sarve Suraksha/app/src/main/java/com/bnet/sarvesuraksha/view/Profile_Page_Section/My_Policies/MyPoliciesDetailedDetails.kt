package com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Policies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.model_api.*
import com.bnet.sarvesuraksha.view.adapter.CarouselAdapter
import com.bnet.sarvesuraksha.view.adapter.PolicyDetailsAdapter
import com.bnet.sarvesuraksha.view.adapter.PolicyDetailsAdapterTravel
import com.bnet.sarvesuraksha.view.adapter.PolicyDetailsAdapterVehicle
import com.google.gson.Gson

class MyPoliciesDetailedDetails : AppCompatActivity() {

    private lateinit var activePolicyRecylerView: RecyclerView
    private lateinit var expiredPolicyRecylerView: RecyclerView
    private lateinit var activeLL: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var ExpiredLL: LinearLayout
    private lateinit var activeChildLL: LinearLayout
    private lateinit var expiredChildLL: LinearLayout
    private lateinit var expire_no_data: LinearLayout
    private lateinit var recommendLL: LinearLayout
    private lateinit var active_No_data: LinearLayout
    private lateinit var PolicyNameToolBarText: TextView

    private lateinit var activePolicyAdapter: PolicyDetailsAdapter<ActivePolicyRes>
    private lateinit var expiredPolicyAdapter: PolicyDetailsAdapter<ExpiredPolicyeRes>

    private lateinit var activePolicyAdapterTravel: PolicyDetailsAdapterTravel<TravelActivePolicyRes?>
    private lateinit var expiredPolicyAdapterTravel: PolicyDetailsAdapterTravel<TravelExpiredPolicyeRes?>


    private lateinit var activePolicyAdapterVehicle: PolicyDetailsAdapterVehicle<VehicleActivePolicyRes?>
    private lateinit var expiredPolicyAdapterVehicle: PolicyDetailsAdapterVehicle<VehicleExpiredPolicyeRes?>


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
        setContentView(R.layout.activity_my_policies_detailed_details)

        activePolicyRecylerView = findViewById(R.id.activePolicyRecylerView)
        PolicyNameToolBarText = findViewById(R.id.PolicyNameToolBarText)
        expiredPolicyRecylerView = findViewById(R.id.expiredPolicyRecylerView)
        activeLL = findViewById(R.id.activeLL)
        goBack = findViewById(R.id.goBack)
        ExpiredLL = findViewById(R.id.ExpiredLL)
        activeChildLL = findViewById(R.id.activeChildLL)
        expiredChildLL = findViewById(R.id.expiredChildLL)
        active_No_data = findViewById(R.id.active_No_data)
        expire_no_data = findViewById(R.id.expire_no_data)
        recommendLL = findViewById(R.id.recommendLL)

        viewPagerRecommended = findViewById(R.id.viewPagerRecommended)
        indicator1 = findViewById(R.id.indicator1)
        indicator2 = findViewById(R.id.indicator2)
        indicator3 = findViewById(R.id.indicator3)


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


        activeLL.setOnClickListener { selectLayout(activeLL, activeChildLL) }
        ExpiredLL.setOnClickListener { selectLayout(ExpiredLL, expiredChildLL) }

        val policyName = intent.getStringExtra("policyName")
        PolicyNameToolBarText.text = "My $policyName Policy"

        val policyDataJson = intent.getStringExtra("policyData")
        Log.d("MyPoliciesDetDetails", "Policy Name: $policyName")
        Log.d("MyPoliciesDetDetails", "Policy Data JSON: $policyDataJson")

        val gson = Gson()
        when (policyName) {
            "Health" -> {
                val wrapper = gson.fromJson(policyDataJson, PolicyData.Health::class.java)
                val healthInsuranceData = wrapper.data
                Log.d("MyPoliciesDetDetails", "Health Insurance Data: $healthInsuranceData")
                setupAdapters(healthInsuranceData.activePolicy, healthInsuranceData.expiredPolicye)
            }
            "Travel" -> {
                val wrapper = gson.fromJson(policyDataJson, PolicyData.Travel::class.java)
                val travelInsuranceData = wrapper.data
                Log.d("MyPoliciesDetDetails", "Travel Insurance Data: $travelInsuranceData")
                setupAdaptersTravel(travelInsuranceData.activePolicy, travelInsuranceData.expiredPolicye)
            }
            "Vehicle" -> {
                val wrapper = gson.fromJson(policyDataJson, PolicyData.Vehicle::class.java)
                val vehicleInsuranceData = wrapper.data
                Log.d("MyPoliciesDetDetails", "Vehicle Insurance Data: $vehicleInsuranceData")
                setupAdaptersVehicle(vehicleInsuranceData.activePolicy, vehicleInsuranceData.expiredPolicye)
            }
        }

        goBack.setOnClickListener {
            startActivity(Intent(this@MyPoliciesDetailedDetails, MyPolicies::class.java))
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
    private fun setupAdapters(
        activePolicies: List<ActivePolicyRes>?,
        expiredPolicies: List<ExpiredPolicyeRes>?

    ) {
        Log.d("MyPoliciesDetDetails", "Setting up adapters with active policies: $activePolicies and expired policies: $expiredPolicies")

        val activePoliciesList = activePolicies ?: emptyList()
        val expiredPoliciesList = expiredPolicies ?: emptyList()

        activePolicyAdapter = PolicyDetailsAdapter(this, activePoliciesList)
        expiredPolicyAdapter = PolicyDetailsAdapter(this, expiredPoliciesList)

        activePolicyRecylerView.layoutManager = LinearLayoutManager(this)
        activePolicyRecylerView.adapter = activePolicyAdapter

        expiredPolicyRecylerView.layoutManager = LinearLayoutManager(this)
        expiredPolicyRecylerView.adapter = expiredPolicyAdapter

        active_No_data.visibility = if (activePoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
        expire_no_data.visibility = if (expiredPoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
        recommendLL.visibility = if (expiredPoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
    }


    private fun setupAdaptersTravel(
        activePolicies: List<TravelActivePolicyRes>?,
        expiredPolicies: List<TravelExpiredPolicyeRes>?

    ) {
        Log.d("MyPoliciesDetDetails", "Setting up adapters with active policies: $activePolicies and expired policies: $expiredPolicies")

        val activePoliciesList = activePolicies ?: emptyList()
        val expiredPoliciesList = expiredPolicies ?: emptyList()


        activePolicyAdapterTravel = PolicyDetailsAdapterTravel(this, activePoliciesList)
        expiredPolicyAdapterTravel = PolicyDetailsAdapterTravel(this, expiredPoliciesList)

        activePolicyRecylerView.layoutManager = LinearLayoutManager(this)
        activePolicyRecylerView.adapter = activePolicyAdapterTravel

        expiredPolicyRecylerView.layoutManager = LinearLayoutManager(this)
        expiredPolicyRecylerView.adapter = expiredPolicyAdapterTravel

        active_No_data.visibility = if (activePoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
        expire_no_data.visibility = if (expiredPoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
        recommendLL.visibility = if (expiredPoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
    }

    private fun setupAdaptersVehicle(
        activePolicies: List<VehicleActivePolicyRes>?,
        expiredPolicies: List<VehicleExpiredPolicyeRes>?

    ) {
        Log.d("MyPoliciesDetDetails", "Setting up adapters with active policies: $activePolicies and expired policies: $expiredPolicies")

        val activePoliciesList = activePolicies ?: emptyList()
        val expiredPoliciesList = expiredPolicies ?: emptyList()


        activePolicyAdapterVehicle = PolicyDetailsAdapterVehicle(this, activePoliciesList)
        expiredPolicyAdapterVehicle = PolicyDetailsAdapterVehicle(this, expiredPoliciesList)

        activePolicyRecylerView.layoutManager = LinearLayoutManager(this)
        activePolicyRecylerView.adapter = activePolicyAdapterVehicle

        expiredPolicyRecylerView.layoutManager = LinearLayoutManager(this)
        expiredPolicyRecylerView.adapter = expiredPolicyAdapterVehicle

        active_No_data.visibility = if (activePoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
        expire_no_data.visibility = if (expiredPoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
        recommendLL.visibility = if (expiredPoliciesList.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
    }

    private fun selectLayout(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {
        activeLL.background = null
        ExpiredLL.background = null

        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg)



        activeChildLL.visibility = if (detailsLayout == activeChildLL) LinearLayout.VISIBLE else LinearLayout.GONE
        expiredChildLL.visibility = if (detailsLayout == expiredChildLL) LinearLayout.VISIBLE else LinearLayout.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MyPoliciesDetailedDetails, MyPolicies::class.java))
        finish()
    }
}
