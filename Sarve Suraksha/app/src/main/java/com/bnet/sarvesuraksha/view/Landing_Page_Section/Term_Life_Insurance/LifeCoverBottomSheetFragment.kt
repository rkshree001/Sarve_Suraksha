package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import com.bnet.savresuraksha.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LifeCoverBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.life_cover_bottomsheet, container, false)
        setupBottomSheetViewIDV(view)
        return view
    }

    private fun setupBottomSheetViewIDV(bottomSheetView: View) {
        val closeIcon = bottomSheetView.findViewById<LinearLayout>(R.id.close_icon)
        val lifeCoverACTV = bottomSheetView.findViewById<AutoCompleteTextView>(R.id.lifeCoverACTV)
        val dropdownArrow = bottomSheetView.findViewById<ImageView>(R.id.dropdownArrow)

        val lifeCoverList = listOf("20 Lakh",
            "25 Lakh", "30 Lakh", "35 Lakh", "40 Lakh",
            "45 Lakh", "50 Lakh","55 Lakh","60 Lakh","65 Lakh","70 Lakh","75 Lakh","80 Lakh","85 Lakh","90 Lakh","95 Lakh","1 CR")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, lifeCoverList)
        lifeCoverACTV.setAdapter(adapter)

        lifeCoverACTV.setOnClickListener {
            lifeCoverACTV.showDropDown()
            toggleArrow(true, dropdownArrow)
        }

        lifeCoverACTV.setOnDismissListener {
            toggleArrow(false, dropdownArrow)
        }

        lifeCoverACTV.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            lifeCoverACTV.setText(selectedItem, false)
            lifeCoverACTV.dismissDropDown()
            toggleArrow(false, dropdownArrow)
        }

        closeIcon.setOnClickListener {
            dismiss()
        }
    }

    private fun toggleArrow(isDropdownOpen: Boolean, dropdownArrow: ImageView) {
        dropdownArrow.setImageResource(
            if (isDropdownOpen) R.drawable.arrow_up_dd else R.drawable.arrow_down_dd
        )
    }
}
