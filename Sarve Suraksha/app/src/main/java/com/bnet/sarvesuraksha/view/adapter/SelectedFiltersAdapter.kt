package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.model.FilterItem
import com.bnet.savresuraksha.R

class SelectedFiltersAdapter(
    private val context: Context,
    private val filters: MutableList<FilterItem>,
    private val onFilterRemoved: (FilterItem) -> Unit
) : RecyclerView.Adapter<SelectedFiltersAdapter.FilterViewHolder>() {

    inner class FilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceName: TextView = view.findViewById(R.id.serviceName)
        val filtersName: TextView = view.findViewById(R.id.filtersName)
        val removeFilter: ImageView = view.findViewById(R.id.removeFilter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.selected_filters_rec_card, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        if (position >= filters.size) return

        val filterItem = filters[position]
        holder.serviceName.text = filterItem.category
        holder.filtersName.text = filterItem.filterName

        holder.removeFilter.setOnClickListener {

            val itemToRemove = filters.getOrNull(position) ?: return@setOnClickListener
            onFilterRemoved(itemToRemove)

            filters.remove(itemToRemove)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, filters.size)
        }
    }

    override fun getItemCount(): Int {
        return filters.size
    }
}
