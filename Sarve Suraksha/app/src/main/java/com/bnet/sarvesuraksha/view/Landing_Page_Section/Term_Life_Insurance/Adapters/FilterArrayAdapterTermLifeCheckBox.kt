package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.bnet.savresuraksha.R

class FilterArrayAdapterTermLifeCheckBox(
    context: Context,
    private val filters: List<String>,
    private val selectedPositions: MutableSet<Int>,
    private val categoryName: String,
    private val onFilterSelectedListener: OnFilterSelectedListenerCheckBox
) : ArrayAdapter<String>(context, R.layout.filter_list_item_sort_by, filters) {

    interface OnFilterSelectedListenerCheckBox {
        fun onFilterSelectedCheckBox(categoryName: String, filterName: String, isSelected: Boolean)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.filter_list_item_sort_by_check_box, parent, false).apply {
                tag = ViewHolder(this)
            }

        viewHolder = view.tag as ViewHolder

        val item = getItem(position)
        viewHolder.filterCheckBox.text = item
        viewHolder.filterCheckBox.isChecked = selectedPositions.contains(position)

        viewHolder.filterCheckBox.setOnClickListener {
            val isSelected = viewHolder.filterCheckBox.isChecked
            if (isSelected) {
                selectedPositions.add(position)
            } else {
                selectedPositions.remove(position)
            }

            onFilterSelectedListener.onFilterSelectedCheckBox(categoryName, item ?: "", isSelected)
        }

        return view
    }

    private class ViewHolder(view: View) {
        val filterCheckBox: CheckBox = view.findViewById(R.id.filterCheckBox)
    }
}
