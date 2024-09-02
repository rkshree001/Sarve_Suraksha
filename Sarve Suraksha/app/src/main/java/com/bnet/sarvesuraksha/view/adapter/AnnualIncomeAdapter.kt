package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import com.bnet.savresuraksha.R

class AnnualIncomeAdapter(
    private val context: Context,
    private val annualIncomeList: List<String>,
    private val listener: OnIncomeSelectedListener
) : BaseAdapter()
{

    private var selectedPosition = -1

    override fun getCount(): Int {
        return annualIncomeList.size
    }

    override fun getItem(position: Int): Any {
        return annualIncomeList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_view_annual_income, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val annualIncome = getItem(position) as String
        viewHolder.radioButton.text = annualIncome


        viewHolder.radioButton.isChecked = position == selectedPosition
        viewHolder.radioButton.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()


            listener.onIncomeSelected(annualIncome)
        }

        return view
    }

    private class ViewHolder(view: View) {
        val radioButton: RadioButton = view.findViewById(R.id.radioButtonAnnualIncome)
    }

    interface OnIncomeSelectedListener {
        fun onIncomeSelected(selectedIncome: String)
    }
}
