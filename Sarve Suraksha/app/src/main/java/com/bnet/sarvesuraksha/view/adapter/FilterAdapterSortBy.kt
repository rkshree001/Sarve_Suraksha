package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import com.bnet.savresuraksha.R

class FilterAdapterSortBy(context: Context, private val filters: List<String>) : ArrayAdapter<String>(context, 0, filters) {
    private var selectedPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.filter_list_item_sort_by, parent, false)

        val radioButton: RadioButton = view.findViewById(R.id.filterRadioButton)

        radioButton.text = filters[position]

        radioButton.isChecked = position == selectedPosition

        radioButton.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }

        return view
    }

    fun getSelectedPosition(): Int {
        return selectedPosition
    }
}
