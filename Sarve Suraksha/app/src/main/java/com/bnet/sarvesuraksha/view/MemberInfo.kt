package com.bnet.sarvesuraksha.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Family.MyFamilyProfilePage
import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import com.skydoves.powerspinner.PowerSpinnerView

class MemberInfo : AppCompatActivity() {

    private lateinit var goBack: LinearLayout
    private lateinit var removeDetails: LinearLayout
    private lateinit var edit_LL: LinearLayout
    private lateinit var save_LL: LinearLayout
    private lateinit var ToolBarText: TextView
    private lateinit var saveTextView: TextView
    private lateinit var FullNameTxtEditText: TextInputEditText
    private lateinit var male_LL: LinearLayout
    private lateinit var female_LL: LinearLayout
    private lateinit var other_LL: LinearLayout
    private lateinit var personalLL: LinearLayout
    private lateinit var addressLL: LinearLayout
    private lateinit var personalDetailsChild: LinearLayout
    private lateinit var addressDetailsChild: LinearLayout
    private lateinit var RetalionSpinner: PowerSpinnerView
    private var popupWindow: PopupWindow? = null


    val RelationList = listOf("Father", "Mother","Father in Law","Mother in Law","Spouse","Brother","Sister")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_info)


        goBack = findViewById(R.id.goBack)
        removeDetails = findViewById(R.id.removeDetails)
        FullNameTxtEditText = findViewById(R.id.FullNameTxtEditText)
        male_LL = findViewById(R.id.male_LL)
        female_LL = findViewById(R.id.female_LL)
        other_LL = findViewById(R.id.other_LL)
        RetalionSpinner = findViewById(R.id.RetalionSpinner)
        personalLL = findViewById(R.id.personalLL)
        addressLL = findViewById(R.id.addressLL)
        personalDetailsChild = findViewById(R.id.personalDetailsChild)
        addressDetailsChild = findViewById(R.id.addressDetailsChild)
        ToolBarText = findViewById(R.id.ToolBarText)
        edit_LL = findViewById(R.id.edit_LL)
        save_LL = findViewById(R.id.save_LL)
        saveTextView = findViewById(R.id.saveTextView)


        edit_LL.setOnClickListener {
            edit_LL.visibility=View.GONE
            removeDetails.visibility=View.GONE
            save_LL.visibility=View.VISIBLE
            ToolBarText.text = "Edit Member Details"
        }

        personalLL.setOnClickListener { selectLayout(personalLL, personalDetailsChild) }
        addressLL.setOnClickListener { selectLayout(addressLL, addressDetailsChild) }


        male_LL.setOnClickListener { selectGenderLayout(male_LL) }
        female_LL.setOnClickListener { selectGenderLayout(female_LL) }
        other_LL.setOnClickListener { selectGenderLayout(other_LL) }
        RetalionSpinner.setItems(RelationList)


        RetalionSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
            showToast("Selected Marital Status: $item")
        }

        goBack.setOnClickListener {
            startActivity(Intent(this@MemberInfo, MyFamilyProfilePage::class.java))
        }

        removeDetails.setOnClickListener {
            showRemoveDetailsPopup(removeDetails)
        }

//
//        save_LL.setOnClickListener {
//
//            startActivity(Intent(this@MemberInfo, FamilyMemberAdded::class.java))
//        }


    }

    private fun showRemoveDetailsPopup(anchorView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.remove_alert_dialog, null)

        val removeDetailsCardView = view.findViewById<CardView>(R.id.removeDetailsCardView)



        removeDetailsCardView.setOnClickListener {
            showCommonAlertDialog(anchorView, inflater)
            popupWindow?.dismiss()


        }


        popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow?.elevation = 10.0f


        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1] + anchorView.height


        popupWindow?.showAtLocation(anchorView, Gravity.START or Gravity.TOP, x, y)
    }

    private fun showCommonAlertDialog(anchorView: View, inflater: LayoutInflater) {
        val alertDialogView = inflater.inflate(R.layout.common_alert_dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(alertDialogView)
        val alertDialog = builder.create()

        val closeIcon = alertDialogView.findViewById<ImageView>(R.id.closeIcon)
        val removeLL = alertDialogView.findViewById<LinearLayout>(R.id.removeLL)
        val cancelLL = alertDialogView.findViewById<LinearLayout>(R.id.cancel_ll)

        closeIcon.setOnClickListener {
            alertDialog.dismiss()
        }

        removeLL.setOnClickListener {

            alertDialog.dismiss()
        }

        cancelLL.setOnClickListener {

            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun selectLayout(selectedLayout: LinearLayout, detailsLayout: LinearLayout) {

        personalLL.background = null
        addressLL.background = null


        selectedLayout.setBackgroundResource(R.drawable.user_selected_bg)


        personalDetailsChild.visibility = if (detailsLayout == personalDetailsChild) LinearLayout.VISIBLE else LinearLayout.GONE
        addressDetailsChild.visibility = if (detailsLayout == addressDetailsChild) LinearLayout.VISIBLE else LinearLayout.GONE
    }

    private fun selectGenderLayout(selectedLayout: LinearLayout) {

        male_LL.setBackgroundResource(R.drawable.gender_default)
        female_LL.setBackgroundResource(R.drawable.gender_default)
        other_LL.setBackgroundResource(R.drawable.gender_default)


        selectedLayout.setBackgroundResource(R.drawable.gender_selected)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MemberInfo, MyFamilyProfilePage::class.java))
        finish()
    }

}
