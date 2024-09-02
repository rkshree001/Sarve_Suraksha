package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import com.bnet.savresuraksha.R
class FilterArrayAdapter(
    context: Context,
    private val filters: List<String>,
    private val selectedPositions: MutableSet<Int>,
    private var selectedPosition: Int,
    private val categoryName: String,
    private val isMultipleSelectionAllowed: Boolean,
    private val onFilterSelectedListener: OnFilterSelectedListener
) : ArrayAdapter<String>(context, R.layout.filter_list_item_sort_by, filters) {

    interface OnFilterSelectedListener {
        fun onFilterSelected(categoryName: String, filterName: String)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.filter_list_item_sort_by, parent, false).apply {
                tag = ViewHolder(this)
            }

        viewHolder = view.tag as ViewHolder

        val item = getItem(position)
        viewHolder.filterRadioButton.text = item
        viewHolder.filterRadioButton.isChecked =
            if (isMultipleSelectionAllowed) selectedPositions.contains(position)
            else selectedPosition == position

        viewHolder.filterRadioButton.setOnClickListener {
            if (isMultipleSelectionAllowed) {

                if (selectedPositions.contains(position)) {
                    selectedPositions.remove(position)
                } else {
                    selectedPositions.add(position)
                }
            } else {

                if (selectedPosition != position) {
                    selectedPosition = position
                    notifyDataSetChanged()
                }
            }


            onFilterSelectedListener.onFilterSelected(categoryName, item ?: "")
        }

        return view
    }

    private class ViewHolder(view: View) {
        val filterRadioButton: RadioButton = view.findViewById(R.id.filterRadioButton)
    }
}

