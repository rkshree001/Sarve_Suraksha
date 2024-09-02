package com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Family

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3009
import com.bnet.sarvesuraksha.model_api.GetUserDetailMainData
import com.bnet.sarvesuraksha.model_api.GetUserDetailMainRes
import com.bnet.sarvesuraksha.view.Profile_Page_Section.Profile_Page.ProfilePage
import com.bnet.sarvesuraksha.view.adapter.FamilyMemberAdapter
import com.bnet.savresuraksha.R
import com.facebook.shimmer.ShimmerFrameLayout
import pl.droidsonroids.gif.GifTextView
import retrofit2.Call
import retrofit2.Callback

class MyFamilyProfilePage : AppCompatActivity() {


    private lateinit var addFamilyMember: LinearLayout
    private lateinit var goBack: LinearLayout
    private lateinit var videoView: VideoView
    private lateinit var familyMembersAddedRecyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var storedMobileNumber: String
    private lateinit var finalVehicleParent: LinearLayout
    private lateinit var addAreaMain: LinearLayout
    private lateinit var tickGifTextView: GifTextView
    private lateinit var tickGifTextViewLL: LinearLayout
    private lateinit var addFamMemPost: LinearLayout

    private lateinit var shimmer_view_container: ShimmerFrameLayout
    private lateinit var shimmer_view_container1: ShimmerFrameLayout
    private lateinit var shimmer_view_container2: ShimmerFrameLayout
    private lateinit var shimmer_view_container3: ShimmerFrameLayout

    companion object {
        var PostSucces: String? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_family_profile_page)

        val intent = intent
        PostSucces = intent.getStringExtra("PostSucces")


        sharedPreferences = getSharedPreferences("com.bnet.sarvesuraksha", Context.MODE_PRIVATE)
        storedMobileNumber = sharedPreferences.getString("mobile_number", null).toString()


        addFamilyMember = findViewById(R.id.addFamilyMember)
        familyMembersAddedRecyclerView = findViewById(R.id.familyMembersAddedRecyclerView)
        videoView = findViewById(R.id.videoView)
        goBack = findViewById(R.id.goBack)


        finalVehicleParent = findViewById(R.id.finalVehicleParent)
        tickGifTextView = findViewById(R.id.tickGifTextView)
        tickGifTextViewLL = findViewById(R.id.tickGifTextViewLL)
        addAreaMain = findViewById(R.id.addAreaMain)
        addFamMemPost = findViewById(R.id.addFamMemPost)

        shimmer_view_container1 = findViewById(R.id.shimmer_view_container1)
        shimmer_view_container2 = findViewById(R.id.shimmer_view_container2)
        shimmer_view_container3 = findViewById(R.id.shimmer_view_container3)
        shimmer_view_container = findViewById(R.id.shimmer_view_container)


        shimmer_view_container.startShimmer()
        shimmer_view_container1.startShimmer()
        shimmer_view_container2.startShimmer()
        shimmer_view_container3.startShimmer()


//        familyMembersAddedRecyclerView.isNestedScrollingEnabled = false
//        familyMembersAddedRecyclerView.layoutManager = null


        Log.d("storedMobileNumber", "onCreate: "+storedMobileNumber)
        if (storedMobileNumber != null) {
            GetUserMemberDetail(storedMobileNumber)
        }






        Log.d("PostSucces", "onCreate: "+ PostSucces)
        if (PostSucces != null && PostSucces == "Yes") {
            finalVehicleParent.visibility = View.VISIBLE
            tickGifTextViewLL.visibility = View.VISIBLE
            tickGifTextView.visibility = View.VISIBLE
            addAreaMain.visibility = View.GONE
            addFamMemPost.visibility = View.VISIBLE

            Handler().postDelayed(
                {
                    tickGifTextViewLL.visibility = View.GONE
                    finalVehicleParent.visibility = View.GONE
                }, 2000
            )

            addFamMemPost.setOnClickListener {
                val intent = Intent(this@MyFamilyProfilePage, AddFamilyMember::class.java)
                startActivity(intent)
                finish()
            }

        }

        if (PostSucces != null && PostSucces == "Delete") {
            finalVehicleParent.visibility = View.GONE
            addAreaMain.visibility = View.VISIBLE
            addFamMemPost.visibility = View.GONE
        }




        addFamilyMember.setOnClickListener {
            val intent = Intent(this@MyFamilyProfilePage, AddFamilyMember::class.java)
            startActivity(intent)
            finish()
        }

        goBack.setOnClickListener {
            startActivity(Intent(this@MyFamilyProfilePage, ProfilePage::class.java))
            finish()
        }

        playVideo()
    }

    private fun playVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.family_gif}")
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            videoView.start()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MyFamilyProfilePage, ProfilePage::class.java))
        finish()
    }

    private fun GetUserMemberDetail(mobileNumberString: String) {
        if (!APIClient.isNetworkAvailable(this@MyFamilyProfilePage)) {
            Toast.makeText(this@MyFamilyProfilePage, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetUserDetailMain", "Request : $mobileNumberString")
        val call: Call<GetUserDetailMainData?>? =
            MyApplicationPort3009.getRetrofitClient(this).create<ApiInterface>(
                ApiInterface::class.java
            ).getUserMemberDetail(mobileNumberString)
        call?.enqueue(object : Callback<GetUserDetailMainData?> {
            override fun onResponse(
                call: Call<GetUserDetailMainData?>,
                response: retrofit2.Response<GetUserDetailMainData?>
            ) {
                if (response.isSuccessful) {
                    val main: GetUserDetailMainData? = response.body()
                    main?.let {

                        Log.d("GetUserDetailMain", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                        main.data?.memberList?.let { memberList ->
                            shimmer_view_container.stopShimmer()
                            shimmer_view_container1.stopShimmer()
                            shimmer_view_container2.stopShimmer()
                            shimmer_view_container3.stopShimmer()

                            shimmer_view_container.visibility = View.GONE
                            shimmer_view_container1.visibility = View.GONE
                            shimmer_view_container2.visibility = View.GONE
                            shimmer_view_container3.visibility = View.GONE

                            bindDataToRecyclerView(memberList)
                        }
                    }
                } else {
                    Log.d("GetUserDetailMain", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@MyFamilyProfilePage, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<GetUserDetailMainData?>?, t: Throwable) {
                Log.e("GetUserDetailMain", "Failed to get user details", t)
                Toast.makeText(this@MyFamilyProfilePage, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun bindDataToRecyclerView(memberList: List<GetUserDetailMainRes>) {

        val adapter = FamilyMemberAdapter(memberList){ familyMember ->
            val intent = Intent(this@MyFamilyProfilePage, AddFamilyMember::class.java).apply {

                putExtra("send", "edit")
                putExtra("fullName", familyMember.fullName)
                putExtra("profilePic", familyMember.profilePicture)
                putExtra("UserPersonalId", familyMember.id)
                putExtra("relation", familyMember.memberType)

                Log.d("familyMemberid", "bindDataToRecyclerView: "+familyMember.id)
            }
            startActivity(intent)

        }
        familyMembersAddedRecyclerView.layoutManager = LinearLayoutManager(this)
        familyMembersAddedRecyclerView.adapter = adapter
    }

}
