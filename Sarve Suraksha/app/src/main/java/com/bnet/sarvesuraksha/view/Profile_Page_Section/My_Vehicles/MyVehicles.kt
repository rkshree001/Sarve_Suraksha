package com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Vehicles

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3009
import com.bnet.sarvesuraksha.model_api.CommonData
import com.bnet.sarvesuraksha.model_api.GetListVehicleMainData
import com.bnet.sarvesuraksha.model_api.GetListVehicleMainRes
import com.bnet.sarvesuraksha.model_api.GetVehicleDetailRcMainData
import com.bnet.sarvesuraksha.model_api.InsuranceDetail
import com.bnet.sarvesuraksha.model_api.OwnerDetail
import com.bnet.sarvesuraksha.model_api.PostVehicleMainData
import com.bnet.sarvesuraksha.model_api.RtoDetail
import com.bnet.sarvesuraksha.model_api.VehicleDetail
import com.bnet.sarvesuraksha.model_api.VehiclePicture
import com.bnet.sarvesuraksha.model_api.VehiclePost
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import com.bnet.sarvesuraksha.view.adapter.MyVehicleListAdapter
import com.bnet.savresuraksha.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import pl.droidsonroids.gif.GifTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyVehicles : AppCompatActivity() {

    private lateinit var addYourVehicle: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var enterVehicleDetails: LinearLayout
    private lateinit var addVehicleParent: LinearLayout
    private lateinit var EnterVehicleNoTIL: TextInputLayout
    private lateinit var EnterVehicleNoTIET: TextInputEditText
    private lateinit var submitVehicleLL: LinearLayout
    private lateinit var finalVehicleParent: LinearLayout
    private lateinit var submitVehicleTextView: TextView
    private lateinit var notYourVehicle: TextView
    private lateinit var vehiclenumberTextView: TextView
    private lateinit var VheicleName: TextView
    private lateinit var cardViewVehicleParent: CardView
    private lateinit var yourVehicleLL: LinearLayout
    private lateinit var delete_ll: LinearLayout
    private lateinit var tickGifTextView: GifTextView
    private lateinit var tickGifTextViewLL: LinearLayout
    private lateinit var yourBikeDocuments: LinearLayout
    private lateinit var overView: TextView
    private lateinit var foundBikeHeaderText: TextView
    private lateinit var MyVehiclesListRecyclerView: RecyclerView
    private lateinit var bgParentAnimationArea: LinearLayout
    private lateinit var AnimatePicsImageView: ImageView


    private var shouldPostVehicleData = false
    private lateinit var vehiclePost: VehiclePost


    private lateinit var storedMobileNumber: String
    private var VehcileidFromPost: String = ""
    private var makerName: String = ""
    private var variantName: String = ""
    private lateinit var sharedPreferences: SharedPreferences


    private val handler = Handler(Looper.getMainLooper())
    private var currentImageIndex = 0
    private val imageResources = listOf(R.drawable.car_vehicle_animation, R.drawable.bike_vehicle_animation)
    val rcPattern = Regex("^[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}$")
    val bhPattern = Regex("^\\d{2}BH\\d{4}[A-Z]{1,2}$")


    val validStateCodes = listOf(
        "AP", "AR", "AS", "BR", "CG", "GA", "GJ", "HR", "HP", "JH", "KA", "KL",
        "MP", "MH", "MN", "ML", "MZ", "NL", "OD", "PB", "RJ", "SK", "TN", "TS",
        "TR", "UP", "UK", "WB", "AN", "CH", "DN", "DD", "DL", "JK", "LA", "LD", "PY"
    )

    private lateinit var shimmer_view_container: ShimmerFrameLayout
    private lateinit var shimmer_view_container1: ShimmerFrameLayout
    private lateinit var shimmer_view_container2: ShimmerFrameLayout
    private lateinit var shimmer_view_container3: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_vehicles)


        sharedPreferences = getSharedPreferences("com.bnet.sarvesuraksha", Context.MODE_PRIVATE)
        storedMobileNumber = sharedPreferences.getString("mobile_number", null).toString()

        MyVehiclesListRecyclerView = findViewById(R.id.MyVehiclesListRecyclerView)
        bgParentAnimationArea = findViewById(R.id.bgParentAnimationArea)
        AnimatePicsImageView = findViewById(R.id.AnimatePicsImageView)
        notYourVehicle = findViewById(R.id.notYourVehicle)
        VheicleName = findViewById(R.id.VheicleName)
        delete_ll = findViewById(R.id.delete_ll)
        goBack = findViewById(R.id.goBack)
        addYourVehicle = findViewById(R.id.addYourVehicle)
        enterVehicleDetails = findViewById(R.id.enterVehicleDeatils)
        EnterVehicleNoTIL = findViewById(R.id.EnterVehicleNoTIL)
        EnterVehicleNoTIET = findViewById(R.id.EnterVehicleNoTIET)
        submitVehicleLL = findViewById(R.id.submitVehicleLL)
        submitVehicleTextView = findViewById(R.id.submitVehicleTextView)
        addVehicleParent = findViewById(R.id.addVehicleParent)
        vehiclenumberTextView = findViewById(R.id.vehiclenumberTextView)
        cardViewVehicleParent = findViewById(R.id.cardViewVehicleParent)
        yourVehicleLL = findViewById(R.id.yourVehicleLL)
        finalVehicleParent = findViewById(R.id.finalVehicleParent)
        tickGifTextView = findViewById(R.id.tickGifTextView)
        tickGifTextViewLL = findViewById(R.id.tickGifTextViewLL)
        yourBikeDocuments = findViewById(R.id.yourBikeDocuments)
        overView = findViewById(R.id.overView)
        foundBikeHeaderText = findViewById(R.id.foundBikeHeaderText)

        shimmer_view_container1 = findViewById(R.id.shimmer_view_container1)
        shimmer_view_container2 = findViewById(R.id.shimmer_view_container2)
        shimmer_view_container3 = findViewById(R.id.shimmer_view_container3)
        shimmer_view_container = findViewById(R.id.shimmer_view_container)


        shimmer_view_container.startShimmer()
        shimmer_view_container1.startShimmer()
        shimmer_view_container2.startShimmer()
        shimmer_view_container3.startShimmer()

        GetUserVehicleData(storedMobileNumber.toString())
        addYourVehicle.setOnClickListener {
            enterVehicleDetails.visibility = View.VISIBLE
            addYourVehicle.visibility = View.GONE
        }
//
//        EnterVehicleNoTIET.addTextChangedListener(object : TextWatcher {
//            private var isFormatting = false
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if (isFormatting) return
//                isFormatting = true
//
//                // Remove spaces and convert to uppercase
//                val cleanedText = s.toString().replace(" ", "").toUpperCase()
//
//                // Format text for display
//                val formattedText = StringBuilder()
//                var i = 0
//
//                while (i < cleanedText.length) {
//                    when (i) {
//                        2, 4, 6 -> formattedText.append(" ")
//                    }
//                    formattedText.append(cleanedText[i])
//                    i++
//                }
//
//                // Set formatted text for display
//                EnterVehicleNoTIET.setText(formattedText.toString())
//                EnterVehicleNoTIET.setSelection(formattedText.length)
//
//                isFormatting = false
//
//                // Validate the cleaned text
//                val isValid = validateVehicleNumber(cleanedText)
//
//                // Update submit button state and appearance
//                if (isValid) {
//                    submitVehicleLL.isClickable = true
//                    submitVehicleLL.isFocusable = true
//                    submitVehicleLL.background.setTint(resources.getColor(R.color.submitVehicleLLTint))
//                    submitVehicleTextView.setTextColor(resources.getColor(R.color.black))
//                    submitVehicleLL.setOnClickListener {
//                        val vehicleNumber = EnterVehicleNoTIET.text.toString().replace(" ", "")
//                        GetVehicleData(vehicleNumber)
//                    }
//
//                } else {
//                    submitVehicleLL.isClickable = false
//                    submitVehicleLL.isFocusable = false
//                    submitVehicleLL.background.setTint(resources.getColor(R.color.submitVehicleLLDisabledTint))
//
//                    submitVehicleTextView.setTextColor(resources.getColor(R.color.disabledTextColor))
//                    submitVehicleLL.setOnClickListener(null)
////                    EnterVehicleNoTIET.error = "Vehicle number should be in the format: XX 00 XX 0000 (e.g., AB 12 CD 3456)"
//
////                    EnterVehicleNoTIET.error = "Vehicle Number Shou"
//                }
//            }
//        })

        EnterVehicleNoTIET.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                // Remove spaces and convert to uppercase
                val cleanedText = s.toString().replace(" ", "").toUpperCase()

                // Format text for display
                val formattedText = StringBuilder()
                var i = 0

                while (i < cleanedText.length) {
                    when (i) {
                        2, 4, 6 -> formattedText.append(" ")
                    }
                    formattedText.append(cleanedText[i])
                    i++
                }

                // Set formatted text for display
                EnterVehicleNoTIET.setText(formattedText.toString())
                EnterVehicleNoTIET.setSelection(formattedText.length)

                isFormatting = false

                // Validate the cleaned text
                if (validateVehicleNumber(cleanedText)) {
                    submitVehicleLL.isClickable = true
                    submitVehicleLL.isFocusable = true
                    submitVehicleLL.setOnClickListener {
                        val vehicleNumber = EnterVehicleNoTIET.text.toString().replace(" ", "")
                        if (validateVehicleNumber(vehicleNumber)) {
                            GetVehicleData(vehicleNumber)
                        } else {
                            EnterVehicleNoTIET.error = "Invalid vehicle number. Please check the format."
                            Toast.makeText(
                                this@MyVehicles,
                                "Invalid vehicle number. Please check the format.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    submitVehicleLL.background.setTint(resources.getColor(R.color.submitVehicleLLTint))
                    submitVehicleTextView.setTextColor(resources.getColor(R.color.black))
                } else {
                    submitVehicleLL.isClickable = false
                    submitVehicleLL.isFocusable = false
                    submitVehicleLL.setOnClickListener(null)
                    addVehicleParent.visibility = View.VISIBLE
                    cardViewVehicleParent.visibility = View.GONE
//                    EnterVehicleNoTIET.error = "Invalid vehicle number. Please check the format."
                }
            }
        })



//        EnterVehicleNoTIET.addTextChangedListener(object : TextWatcher {
//            private var isFormatting = false
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if (isFormatting) return
//                isFormatting = true
//
//                val text = s.toString().replace(" ", "").toUpperCase()
//                val formattedText = StringBuilder()
//                var i = 0
//
//                while (i < text.length) {
//                    when (i) {
//                        2, 4, 6 -> formattedText.append(" ")
//                    }
//                    formattedText.append(text[i])
//                    i++
//                }
//
//                EnterVehicleNoTIET.setText(formattedText.toString())
//                EnterVehicleNoTIET.setSelection(formattedText.length)
//
//                isFormatting = false
//
//                if (formattedText.length == 13) {
//
//                    submitVehicleLL.isClickable = true
//                    submitVehicleLL.isFocusable = true
//                    submitVehicleLL.setOnClickListener {
//
//
//
//                        val vehicleNumber = EnterVehicleNoTIET.text.toString().replace(" ", "")
//                        GetVehicleData(vehicleNumber)
//                    }
//
//
//                    submitVehicleLL.background.setTint(resources.getColor(R.color.submitVehicleLLTint))
//                    submitVehicleTextView.setTextColor(resources.getColor(R.color.black))
//                } else {
//
//                    submitVehicleLL.isClickable = false
//                    submitVehicleLL.isFocusable = false
//                    submitVehicleLL.setOnClickListener(null)
//                    addVehicleParent.visibility = View.VISIBLE
//                    cardViewVehicleParent.visibility = View.GONE
//                }
//            }
//        })

        notYourVehicle.setOnClickListener {
            EnterVehicleNoTIET.setText("")
            addVehicleParent.visibility = View.GONE
            cardViewVehicleParent.visibility = View.GONE
            addVehicleParent.visibility = View.VISIBLE
        }

        yourVehicleLL.setOnClickListener {
            EnterVehicleNoTIET.setText("")


            shouldPostVehicleData = true


            val gson = Gson()
            val vehiclePostJson = gson.toJson(vehiclePost)
            Log.d("vehiclePost", "VehiclePost JSON: $vehiclePostJson")

            if (shouldPostVehicleData) {
                PostVehicleData(vehiclePost)
            }

        }

        overView.setOnClickListener {
            startActivity(Intent(this@MyVehicles, OverViewMyVehicle::class.java).putExtra("PostSucces",VehcileidFromPost))
        }

        goBack.setOnClickListener {
            startActivity(Intent(this@MyVehicles, ProfilePage::class.java))
            finish()
        }

        delete_ll.setOnClickListener {
            showCommonAlertDialog()

        }
        AnimatePicsImageView.setImageResource(imageResources[currentImageIndex])

        startImageAnimation()
    }


    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun validateVehicleNumber(vehicleNumber: String): Boolean {
        if (vehicleNumber.length < 2) {
            return false
        }

        val stateCode = vehicleNumber.substring(0, 2)

        return when {
            !validStateCodes.contains(stateCode) -> {
                false
            }
            rcPattern.matches(vehicleNumber) -> {
                true
            }
            bhPattern.matches(vehicleNumber) -> {
                true
            }
            else -> {
                false
            }
        }
    }
    private fun startImageAnimation() {

        if (!::AnimatePicsImageView.isInitialized) {
            Log.e("Animation", "AnimatePicsImageView is not initialized.")
            return
        }


        val anim1 = ObjectAnimator.ofFloat(AnimatePicsImageView, "translationX", 1000f, 0f)
        anim1.duration = 1000

        val anim2 = ObjectAnimator.ofFloat(AnimatePicsImageView, "translationX", 0f, -1000f)
        anim2.duration = 1000

        anim2.startDelay = 500

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(anim1, anim2)


        animatorSet.interpolator = LinearInterpolator()


        animatorSet.start()


        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)


                currentImageIndex = (currentImageIndex + 1) % imageResources.size


                AnimatePicsImageView.setImageResource(imageResources[currentImageIndex])
                startImageAnimation()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MyVehicles, ProfilePage::class.java))
        finish()
    }


    private fun GetVehicleData(vehicleNumber: String) {
        if (!APIClient.isNetworkAvailable(this@MyVehicles)) {
            Toast.makeText(this@MyVehicles, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("GetVehicleData", "Request : $vehicleNumber")
        val call: Call<GetVehicleDetailRcMainData?>? =
            MyApplicationPort3009.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getVehicleDetail(vehicleNumber)
        call?.enqueue(object : Callback<GetVehicleDetailRcMainData?> {
            override fun onResponse(
                call: Call<GetVehicleDetailRcMainData?>,
                response: retrofit2.Response<GetVehicleDetailRcMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: GetVehicleDetailRcMainData? = response.body()
                    main?.let {
                        EnterVehicleNoTIET.hideKeyboard()
                        Log.d("GetVehicleData", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                        addVehicleParent.visibility = View.GONE
                        MyVehiclesListRecyclerView.visibility = View.GONE
                        cardViewVehicleParent.visibility = View.VISIBLE

                        val regNo = it.data?.rcDetail?.vehicleDetail?.registrationNumber
                        makerName = it.data?.rcDetail?.vehicleDetail?.makerName.toString()
                        val modelName = it.data?.rcDetail?.vehicleDetail?.modelName
                        variantName = it.data?.rcDetail?.vehicleDetail?.variantName.toString()


                        val vehicleDetail = it.data?.rcDetail?.vehicleDetail
                        val ownerDetail = it.data?.rcDetail?.ownerdetail
                        val insuranceDetail = it.data?.rcDetail?.insuranceDetail


                        vehiclePost = VehiclePost(
                            vehicleDetail = VehicleDetail(
                                registrationNumber = vehicleDetail?.registrationNumber ?: "",
                                makerName = vehicleDetail?.makerName ?: "",
                                modelName = vehicleDetail?.modelName ?: "",
                                variantName = vehicleDetail?.variantName ?: "",
                                fuelType = vehicleDetail?.fuelType ?: "",
                                manufacturingDate = vehicleDetail?.manufacturingDate ?: "",
                                registrationDate = vehicleDetail?.registrationDate ?: "",
                                engineNumber = vehicleDetail?.engineNumber ?: "",
                                chassisNumber = vehicleDetail?.chassisNumber ?: "",
                                rtoDetail = RtoDetail(
                                    rtoCode = vehicleDetail?.rtoDetail?.rtoCode ?: "",
                                    rtoName = vehicleDetail?.rtoDetail?.rtoName ?: "",
                                    pincode = vehicleDetail?.rtoDetail?.pincode ?: "",
                                    cityName = vehicleDetail?.rtoDetail?.cityName ?: "",
                                    stateName = vehicleDetail?.rtoDetail?.stateName ?: ""
                                ),
                                vehiclePictures = vehicleDetail?.vehiclePictures?.map { picture ->
                                    VehiclePicture(
                                        picturId = picture.picturId ?: "",
                                        pictureName = picture.pictureName ?: "",
                                        picture = picture.picture ?: ""
                                    )
                                } ?: emptyList()
                            ),
                            ownerdetail = OwnerDetail(
                                ownerName = ownerDetail?.ownerName ?: "",
                                mobileNumber = ownerDetail?.mobileNumber ?: ""
                            ),
                            insuranceDetail = insuranceDetail?.map { insurance ->
                                InsuranceDetail(
                                    insuranceType = insurance.insuranceType ?: "",
                                    insurerName = insurance.insurerName ?: "",
                                    policyNumber = insurance.policyNumber ?: "",
                                    policyExpiryDate = insurance.policyExpiryDate ?: ""
                                )
                            } ?: emptyList(),
                            logInId = "9080322066"
                        )


//                        PostVehicleData(vehiclePost)
                        vehiclenumberTextView.text=regNo
                        VheicleName.text="  "+makerName+" "+variantName
                    }
                } else {
                    Log.d("GetVehicleData", "Server error: ${response.code()} - ${response.message()}"+"~~"+response.raw())
                    Toast.makeText(this@MyVehicles, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetVehicleDetailRcMainData?>?, t: Throwable) {
                Log.e("GetUserDetailAddfam", "Failed to get user details", t)
                Toast.makeText(this@MyVehicles, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun PostVehicleData(vehiclePost: VehiclePost) {
        val gson = Gson()
        val json = gson.toJson(vehiclePost)

        Log.d("PostVehicleData", "PostVehicleData JSON: $json")
        Log.d("vehiclePost", "PostVehicleData: "+vehiclePost.toString())

        val call: Call<PostVehicleMainData> = MyApplicationPort3009.getRetrofitClient(this).create(ApiInterface::class.java)
            .postVehicleData(vehiclePost)

        call?.enqueue(object : Callback<PostVehicleMainData?> {
            override fun onResponse(call: Call<PostVehicleMainData?>, response: Response<PostVehicleMainData?>) {
                if (response.isSuccessful) {
                    val postVehicleMainData = response.body()
                    postVehicleMainData?.let {
                        val postVehicleMainRes = it.data?.vehicleDetail
                        val id = postVehicleMainRes?.id // Accessing the _id field

                        VehcileidFromPost = postVehicleMainRes?.id.toString()

                        Log.d("PostVehicleData", "Posted Vehicle ID: $id"+"~~"+VehcileidFromPost)

                        finalVehicleParent.visibility = View.VISIBLE
                        yourBikeDocuments.visibility = View.VISIBLE
                        tickGifTextViewLL.visibility = View.VISIBLE
                        tickGifTextView.visibility = View.VISIBLE
                        overView.visibility = View.VISIBLE
                        notYourVehicle.visibility = View.GONE
                        cardViewVehicleParent.visibility = View.VISIBLE
                        cardViewVehicleParent.layoutParams.height = resources.getDimensionPixelSize(R.dimen.card_view_height)
                        addVehicleParent.visibility = View.GONE
                        yourVehicleLL.visibility = View.GONE
                        delete_ll.visibility = View.VISIBLE
                        foundBikeHeaderText.visibility = View.GONE

                        Handler().postDelayed(
                            {
                                tickGifTextViewLL.visibility = View.GONE
                                finalVehicleParent.visibility = View.GONE
                            }, 1000
                        )



                        Log.d("PostVehicleData", "onResponse: ${response.raw()}~~${postVehicleMainData.message}")
                        Toast.makeText(this@MyVehicles, postVehicleMainData.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("PostVehicleData", "onResponse Error: ${response.errorBody()?.string()}"+"~~"+response.raw()+"~~"+vehiclePost.toString())
//                    Toast.makeText(this@MyVehicles, "Additional details Not posted Successfully", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostVehicleMainData?>, t: Throwable) {
                Log.d("PostAdditionalDetail", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@MyVehicles, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun GetUserVehicleData(mobileNumberString: String) {
        if (!APIClient.isNetworkAvailable(this@MyVehicles)) {
            Toast.makeText(this@MyVehicles, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetUserDetailMain", "Request : $mobileNumberString")
        val call: Call<GetListVehicleMainData?>? =
            MyApplicationPort3009.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getAllVehicleDetailList(mobileNumberString)
        call?.enqueue(object : Callback<GetListVehicleMainData?> {
            override fun onResponse(
                call: Call<GetListVehicleMainData?>,
                response: retrofit2.Response<GetListVehicleMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: GetListVehicleMainData? = response.body()
                    main?.let {
                        Log.d("GetUserDetailMain", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                        main.data?.vehicleList?.let { VehicleList ->

                            shimmer_view_container.stopShimmer()
                            shimmer_view_container1.stopShimmer()
                            shimmer_view_container2.stopShimmer()
                            shimmer_view_container3.stopShimmer()

                            shimmer_view_container.visibility = View.GONE
                            shimmer_view_container1.visibility = View.GONE
                            shimmer_view_container2.visibility = View.GONE
                            shimmer_view_container3.visibility = View.GONE

                            bindDataToRecyclerView(VehicleList)
                        }
                    }
                } else {
                    Log.d("GetUserDetailMain", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@MyVehicles, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<GetListVehicleMainData?>?, t: Throwable) {
                Log.e("GetUserDetailMain", "Failed to get user details", t)
                Toast.makeText(this@MyVehicles, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showCommonAlertDialog() {
        val inflater = LayoutInflater.from(this)
        val alertDialogView = inflater.inflate(R.layout.common_alert_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val removeLL = alertDialogView.findViewById<LinearLayout>(R.id.removeLL)
        val cancelLL = alertDialogView.findViewById<LinearLayout>(R.id.cancel_ll)
        val signInText = alertDialogView.findViewById<TextView>(R.id.signInText)

        signInText.setText("Remove  "+makerName+" "+variantName+" Details?")

        closeIcon.setOnClickListener {
            alertDialog.dismiss()
        }

        removeLL.setOnClickListener {

            DeleteMemberdetail(VehcileidFromPost,alertDialog)

        }

        cancelLL.setOnClickListener {

            alertDialog.dismiss()
        }

        alertDialog.show()
    }


    private fun bindDataToRecyclerView(VehicleList: List<GetListVehicleMainRes>) {

        val adapter = MyVehicleListAdapter(VehicleList){ familyMember ->

            VehcileidFromPost=familyMember.id.toString()

//            finalVehicleParent.visibility = View.VISIBLE
            yourBikeDocuments.visibility = View.VISIBLE
            tickGifTextViewLL.visibility = View.VISIBLE
            tickGifTextView.visibility = View.VISIBLE
            overView.visibility = View.VISIBLE
            notYourVehicle.visibility = View.GONE
            cardViewVehicleParent.visibility = View.VISIBLE
            cardViewVehicleParent.layoutParams.height = resources.getDimensionPixelSize(R.dimen.card_view_height)
            addVehicleParent.visibility = View.GONE
            yourVehicleLL.visibility = View.GONE
            MyVehiclesListRecyclerView.visibility = View.GONE
            delete_ll.visibility = View.VISIBLE
            foundBikeHeaderText.visibility = View.GONE

            overView.setOnClickListener {
                val intent = Intent(this@MyVehicles, OverViewMyVehicle::class.java).apply {

                    putExtra("PostSucces", familyMember.id.toString())

                }

                startActivity(intent)

            }




        }
        MyVehiclesListRecyclerView.layoutManager = LinearLayoutManager(this)
        MyVehiclesListRecyclerView.adapter = adapter
    }


    private fun DeleteMemberdetail(id: String, alertDialog: AlertDialog) {

        Log.d("LOGPostBrfTRan", "Resp : "+id.toString())
        val call: Call<CommonData?>? =
            MyApplicationPort3009.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).deleteVehicleDetail(id.toString())
        call?.enqueue(object : Callback<CommonData?> {
            override fun onResponse(
                call: Call<CommonData?>,
                response: retrofit2.Response<CommonData?>
            ) {
                if (response.isSuccessful()) {
                    val main: CommonData? = response.body()
                    try {

                        if (response.isSuccessful){

                            alertDialog.dismiss()


                            startActivity(Intent(this@MyVehicles, MyVehicles::class.java))
                            finish()

                            Log.d("ResponseRawa", "onResponse 1: "+response.raw()+"~"+id.toString())

                            Toast.makeText(this@MyVehicles, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("ResponseRawa", "onResponse 2: "+response.raw()+"~"+id.toString())
                        Toast.makeText(this@MyVehicles, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("ResponseRawa", "onResponse 3: "+response.raw()+"~"+id.toString())
                    Toast.makeText(this@MyVehicles, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            public override fun onFailure(call: Call<CommonData?>?, t: Throwable) {
                Log.d("ResponseRawa", "onResponse: "+t.localizedMessage+"~"+id.toString())
                Toast.makeText(this@MyVehicles, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

}
