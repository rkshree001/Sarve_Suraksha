package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import java.util.Locale

class CountryAdapter(private val context: Context, private val originalCountries: ArrayList<String>) :
    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, originalCountries) {

    private val filteredCountries = ArrayList(originalCountries)

    override fun getCount(): Int {
        return filteredCountries.size
    }

    override fun getItem(position: Int): String? {
        return filteredCountries[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.simple_list_item_1, null)
        }

        val countryName = filteredCountries[position]
        val textView = view?.findViewById<TextView>(android.R.id.text1)
        textView?.text = countryName

        return view!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filteredList = ArrayList<String>()

                if (constraint == null || constraint.isEmpty()) {
                    filterResults.values = originalCountries
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                    for (item in originalCountries) {
                        if (item.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }

                    filterResults.values = filteredList
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCountries.clear()
                filteredCountries.addAll(results?.values as List<String>)
                notifyDataSetChanged()
            }
        }
    }
}
