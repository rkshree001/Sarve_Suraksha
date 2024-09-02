package com.bnet.sarvesuraksha.view

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bnet.savresuraksha.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import jp.wasabeef.blurry.Blurry

class SummaryActivity : AppCompatActivity() {
    private lateinit var mainLayoutParent: LinearLayout
    private lateinit var proceedAndPayLL: LinearLayout
    private lateinit var closeIcon: LinearLayout
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox

    private lateinit var behaviour_review: View
    private lateinit var sheetBehavior: BottomSheetBehavior<*>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        mainLayoutParent = findViewById(R.id.main)
        proceedAndPayLL = findViewById(R.id.proceedAndPayLL)

        proceedAndPayLL.setOnClickListener {
            showDeclarationBottomSheet()
        }
    }

    private fun showDeclarationBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.declaration_bottom_sheet, null)

        setupBottomSheetView(bottomSheetView, bottomSheetDialog)

        bottomSheetDialog.setContentView(bottomSheetView)


        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(false)

        bottomSheetDialog.show()


    }

    private fun setupBottomSheetView(bottomSheetView: View, bottomSheetDialog: BottomSheetDialog) {
        checkBox1 = bottomSheetView.findViewById(R.id.checkBox1)
        checkBox2 = bottomSheetView.findViewById(R.id.checkBox2)
        closeIcon = bottomSheetView.findViewById(R.id.closeIcon)

        closeIcon.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }
}
