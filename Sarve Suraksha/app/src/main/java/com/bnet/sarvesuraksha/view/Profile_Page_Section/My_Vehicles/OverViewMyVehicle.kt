package com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Vehicles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3009
import com.bnet.sarvesuraksha.model_api.GetIndividualVehicleMainData
import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class OverViewMyVehicle : AppCompatActivity() {

    private lateinit var VehicleLL: LinearLayout
    private lateinit var OwnerLL: LinearLayout
    private lateinit var InsuranceLL: LinearLayout
    private lateinit var VehicleParent: LinearLayout
    private lateinit var OwnerParent: LinearLayout
    private lateinit var InsuranceParent: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var RegnNumTxtEditText: TextInputEditText
    private lateinit var MakeAndModelTxtEditText: TextInputEditText
    private lateinit var FuelTypeTxtEditText: TextInputEditText
    private lateinit var ManufactureTxtEditText: TextInputEditText
    private lateinit var RegistrationDateTxtEditText: TextInputEditText
    private lateinit var EngineNumberTxtEditText: TextInputEditText
    private lateinit var ChassisNumberTxtEditText: TextInputEditText
    private lateinit var rtoCity: TextInputEditText
    private lateinit var rtoPinCode: TextInputEditText
    private lateinit var OwnerNameTxtEditText: TextInputEditText
    private lateinit var PhoneNumberTxtEditText: TextInputEditText
    private lateinit var ThirdPartyInsTxtEditText: TextInputEditText
    private lateinit var ThirdPolicyNumTxtEditText: TextInputEditText
    private lateinit var ThirdPolicyExpDateTxtEditText: TextInputEditText
    private lateinit var OwnDmagePolicyInsTxtEditText: TextInputEditText
    private lateinit var OwnDamageNumTxtEditText: TextInputEditText
    private lateinit var OwnDamageExpDateTxtEditText: TextInputEditText



    companion object {
        var PostSucces: String? = null

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_over_view_my_vehicle)


        val intent = intent
        PostSucces = intent.getStringExtra("PostSucces")


        VehicleLL = findViewById(R.id.VehicleLL)
        OwnerLL = findViewById(R.id.OwnerLL)
        InsuranceLL = findViewById(R.id.InsuranceLL)
        VehicleParent = findViewById(R.id.VehicleParent)
        OwnerParent = findViewById(R.id.OwnerParent)
        InsuranceParent = findViewById(R.id.InsuranceParent)
        goBack = findViewById(R.id.goBack)
        RegnNumTxtEditText = findViewById(R.id.RegnNumTxtEditText)
        MakeAndModelTxtEditText = findViewById(R.id.MakeAndModelTxtEditText)
        FuelTypeTxtEditText = findViewById(R.id.FuelTypeTxtEditText)
        ManufactureTxtEditText = findViewById(R.id.ManufactureTxtEditText)
        RegistrationDateTxtEditText = findViewById(R.id.RegistrationDateTxtEditText)
        EngineNumberTxtEditText = findViewById(R.id.EngineNumberTxtEditText)
        ChassisNumberTxtEditText = findViewById(R.id.ChassisNumberTxtEditText)
        rtoCity = findViewById(R.id.rtoCity)
        rtoPinCode = findViewById(R.id.rtoPinCode)
        OwnerNameTxtEditText = findViewById(R.id.OwnerNameTxtEditText)
        PhoneNumberTxtEditText = findViewById(R.id.PhoneNumberTxtEditText)
        ThirdPartyInsTxtEditText = findViewById(R.id.ThirdPartyInsTxtEditText)
        ThirdPolicyNumTxtEditText = findViewById(R.id.ThirdPolicyNumTxtEditText)
        ThirdPolicyExpDateTxtEditText = findViewById(R.id.ThirdPolicyExpDateTxtEditText)
        OwnDmagePolicyInsTxtEditText = findViewById(R.id.OwnDmagePolicyInsTxtEditText)
        OwnDamageNumTxtEditText = findViewById(R.id.OwnDamageNumTxtEditText)
        OwnDamageExpDateTxtEditText = findViewById(R.id.OwnDamageExpDateTxtEditText)

        VehicleLL.setOnClickListener { onLayoutClicked(VehicleParent, VehicleLL) }
        OwnerLL.setOnClickListener { onLayoutClicked(OwnerParent, OwnerLL) }
        InsuranceLL.setOnClickListener { onLayoutClicked(InsuranceParent, InsuranceLL) }

        setFieldsNonEditable()

        goBack.setOnClickListener {
            startActivity(Intent(this@OverViewMyVehicle, MyVehicles::class.java))
            finish()
        }

        val vehicleId = PostSucces
        if (vehicleId != null) {
            GetUserVehicleData(vehicleId)
        } else {

            Toast.makeText(this, "Vehicle ID is missing", Toast.LENGTH_SHORT).show()
        }


    }

    private fun setFieldsNonEditable() {
        RegnNumTxtEditText.isFocusable = false
        RegnNumTxtEditText.isClickable = false
        MakeAndModelTxtEditText.isFocusable = false
        MakeAndModelTxtEditText.isClickable = false
        FuelTypeTxtEditText.isFocusable = false
        FuelTypeTxtEditText.isClickable = false
        ManufactureTxtEditText.isFocusable = false
        ManufactureTxtEditText.isClickable = false
        RegistrationDateTxtEditText.isFocusable = false
        RegistrationDateTxtEditText.isClickable = false
        EngineNumberTxtEditText.isFocusable = false
        EngineNumberTxtEditText.isClickable = false
        ChassisNumberTxtEditText.isFocusable = false
        ChassisNumberTxtEditText.isClickable = false
        rtoCity.isFocusable = false
        rtoCity.isClickable = false
        rtoPinCode.isFocusable = false
        rtoPinCode.isClickable = false
        OwnerNameTxtEditText.isFocusable = false
        OwnerNameTxtEditText.isClickable = false
        PhoneNumberTxtEditText.isFocusable = false
        PhoneNumberTxtEditText.isClickable = false
        ThirdPartyInsTxtEditText.isFocusable = false
        ThirdPartyInsTxtEditText.isClickable = false
        ThirdPolicyNumTxtEditText.isFocusable = false
        ThirdPolicyNumTxtEditText.isClickable = false
        ThirdPolicyExpDateTxtEditText.isFocusable = false
        ThirdPolicyExpDateTxtEditText.isClickable = false
        OwnDmagePolicyInsTxtEditText.isFocusable = false
        OwnDmagePolicyInsTxtEditText.isClickable = false
        OwnDamageNumTxtEditText.isFocusable = false
        OwnDamageNumTxtEditText.isClickable = false
        OwnDamageExpDateTxtEditText.isFocusable = false
        OwnDamageExpDateTxtEditText.isClickable = false
    }
    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
    private fun onLayoutClicked(visibleLayout: LinearLayout, selectedLayout: LinearLayout) {

        VehicleParent.visibility = View.GONE
        OwnerParent.visibility = View.GONE
        InsuranceParent.visibility = View.GONE


        visibleLayout.visibility = View.VISIBLE


        resetBackgrounds()
        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg)
    }

    private fun resetBackgrounds() {
        VehicleLL.background = null
        OwnerLL.background = null
        InsuranceLL.background = null

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@OverViewMyVehicle, MyVehicles::class.java))
        finish()
    }

    private fun GetUserVehicleData(vehicleId: String) {
        if (!APIClient.isNetworkAvailable(this@OverViewMyVehicle)) {
            Toast.makeText(this@OverViewMyVehicle, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetUserDetailMain", "Request : $vehicleId")
        val call: Call<GetIndividualVehicleMainData?>? =
            MyApplicationPort3009.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getIndividualVehicleDetail(vehicleId)
        call?.enqueue(object : Callback<GetIndividualVehicleMainData?> {
            override fun onResponse(
                call: Call<GetIndividualVehicleMainData?>,
                response: retrofit2.Response<GetIndividualVehicleMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: GetIndividualVehicleMainData? = response.body()
                    main?.let {
                        Log.d("GetUserDetailMain", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                        main?.let {
                            Log.d(
                                "GetUserDetailMain",
                                "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}"
                            )


                            it.data?.let { data ->
                                data.vehicleDetail?.let { vehicleDetail ->


                                    RegnNumTxtEditText.setText(vehicleDetail.vehicleDetail?.registrationNumber)
                                    MakeAndModelTxtEditText.setText("${vehicleDetail.vehicleDetail?.makerName} ${vehicleDetail.vehicleDetail?.modelName}")
                                    FuelTypeTxtEditText.setText(vehicleDetail.vehicleDetail?.fuelType)

                                    ManufactureTxtEditText.setText(formatDate(vehicleDetail.vehicleDetail?.manufacturingDate.toString()))
                                    RegistrationDateTxtEditText.setText(formatDate(vehicleDetail.vehicleDetail?.registrationDate.toString()))
//                                    ManufactureTxtEditText.setText(vehicleDetail.vehicleDetail?.manufacturingDate)
//                                    RegistrationDateTxtEditText.setText(vehicleDetail.vehicleDetail?.registrationDate)
//                                    EngineNumberTxtEditText.setText(vehicleDetail.vehicleDetail?.engineNumber)
//                                    ChassisNumberTxtEditText.setText(vehicleDetail.vehicleDetail?.chassisNumber)

                                    EngineNumberTxtEditText.setText(maskSensitiveData(vehicleDetail.vehicleDetail?.engineNumber ?: ""))
                                    ChassisNumberTxtEditText.setText(maskSensitiveData(vehicleDetail.vehicleDetail?.chassisNumber ?: ""))
                                    rtoCity.setText(vehicleDetail.vehicleDetail?.rtoDetail?.cityName)
                                    rtoPinCode.setText(vehicleDetail.vehicleDetail?.rtoDetail?.pincode)
                                }

                                it.data?.vehicleDetail?.ownerdetail?.let { ownerDetail ->
                                    OwnerNameTxtEditText.setText(maskSensitiveData(ownerDetail.ownerName ?: ""))
                                    PhoneNumberTxtEditText.setText(maskSensitiveData(ownerDetail.mobileNumber ?: "", isPhone = true))
                                }


//                                it.data?.vehicleDetail?.ownerdetail?.let { ownerDetail ->
//                                    OwnerNameTxtEditText.setText(ownerDetail.ownerName)
//                                    PhoneNumberTxtEditText.setText(ownerDetail.mobileNumber)
//                                }


                                it.data?.vehicleDetail?.insuranceDetail?.let { insuranceList ->
                                    for (insurance in insuranceList) {
                                        when (insurance.insuranceType) {
                                            "thirdParty" -> {
                                                ThirdPartyInsTxtEditText.setText(insurance.insurerName)
//                                                ThirdPolicyNumTxtEditText.setText(insurance.policyNumber)
//                                                ThirdPolicyExpDateTxtEditText.setText(insurance.policyExpiryDate)
                                                ThirdPolicyNumTxtEditText.setText(maskSensitiveData(insurance.policyNumber.toString()))

                                                ThirdPolicyExpDateTxtEditText.setText(formatDate(insurance.policyExpiryDate.toString()))

                                            }

                                            "ownerDamage" -> {
                                                OwnDmagePolicyInsTxtEditText.setText(insurance.insurerName)
//                                                OwnDamageNumTxtEditText.setText(insurance.policyNumber)
                                                OwnDamageNumTxtEditText.setText(maskSensitiveData(insurance.policyNumber.toString()))

                                                OwnDamageExpDateTxtEditText.setText(formatDate(insurance.policyExpiryDate.toString()))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.d("GetUserDetailMain", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@OverViewMyVehicle, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<GetIndividualVehicleMainData?>?, t: Throwable) {
                Log.e("GetUserDetailMain", "Failed to get user details", t)
                Toast.makeText(this@OverViewMyVehicle, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun maskSensitiveData(data: String, isPhone: Boolean = false): String {
        return if (isPhone) {
            if (data.length > 4) {
                val firstThree = data.substring(0, 3)
                val lastOne = data.substring(data.length - 1)
                "$firstThree*****$lastOne"
            } else {
                data // Return as is if length is less than or equal to 4
            }
        } else {
            if (data.length > 4) {
                val firstTwo = data.substring(0, 2)
                val lastTwo = data.substring(data.length - 2)
                "$firstTwo****$lastTwo"
            } else if (data.length == 4) {
                val firstTwo = data.substring(0, 2)
                "$firstTwo**"
            } else {
                data // Return as is if length is less than 4
            }
        }
    }

//    private fun maskSensitiveData(data: String): String {
//        return if (data.length > 8) {
//            val firstFour = data.substring(0, 4)
//            val lastFour = data.substring(data.length - 4)
//            "$firstFour****$lastFour"
//        } else {
//            data
//        }
//    }

}
