package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import com.bnet.savresuraksha.R

class CustomSpinnerAdapter(
    context: Context,
    private val items: List<String>
) : ArrayAdapter<String>(context, R.layout.filter_list_item_sort_by, items) {

    private var selectedPosition: Int = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.filter_list_item_sort_by, parent, false)
        val itemRadioButton = view.findViewById<RadioButton>(R.id.filterRadioButton)
        itemRadioButton.text = items[position]


        itemRadioButton.isChecked = position == selectedPosition


        itemRadioButton.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val viewGroup = view as ViewGroup

        if (viewGroup.childCount > 1) {
            viewGroup.removeViewAt(viewGroup.childCount - 1)
        }

        val divider = View(context)
        divider.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            1
        )
        divider.setBackgroundColor(Color.GRAY)
        viewGroup.addView(divider)

        return view
    }

    fun getSelectedItemText(): String {
        return if (selectedPosition != -1) items[selectedPosition] else ""
    }
}
