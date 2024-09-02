package com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.model.FilterItem
import com.bnet.sarvesuraksha.view.adapter.FilterArrayAdapter
import com.bnet.sarvesuraksha.view.adapter.SelectedFiltersAdapter
import com.bnet.savresuraksha.R

class CarInsuranceQuoteAllFilters : AppCompatActivity(),
    FilterArrayAdapter.OnFilterSelectedListener {

    private lateinit var sortByListView: ListView
    private lateinit var addOnsListView: ListView
    private lateinit var accessoriesCoversListView: ListView
    private lateinit var deductiblesListView: ListView
    private lateinit var sortByFiltersParent: LinearLayout
    private lateinit var deductiblesFiltersParent: LinearLayout
    private lateinit var addOnsFiltersParent: LinearLayout
    private lateinit var accessoriesCoverParent: LinearLayout
    private lateinit var sortByArrow: ImageView
    private lateinit var addOnsByArrow: ImageView
    private lateinit var deductiblesOnsByArrow: ImageView
    private lateinit var accessoriesOnsByArrow: ImageView
    private lateinit var filterAddedList: RecyclerView
    private var selectedSortByPosition: Int = -1
    private var selectedAddOnsPosition: Int = -1
    private var selectedDeductiblesPosition: Int = -1
    private var selectedAccessoriesPosition: Int = -1

    private var quoteId: String? = null
    private lateinit var goBack: LinearLayout
    private lateinit var applyFilters: LinearLayout

    private lateinit var selectedFiltersAdapter: SelectedFiltersAdapter
    private val selectedFilters = mutableListOf<FilterItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_insurance_quote_all_filters)

        quoteId = intent.getStringExtra("QuoteId")
        Log.d("CarInsuranceQoutePlanForm", "QuoteId: $quoteId")


        sortByListView = findViewById(R.id.sortByListView)
        sortByFiltersParent = findViewById(R.id.sortByFiltersParent)
        addOnsFiltersParent = findViewById(R.id.addOnsFiltersParent)
        sortByArrow = findViewById(R.id.sortByArrow)
        accessoriesOnsByArrow = findViewById(R.id.accessoriesOnsByArrow)
        addOnsListView = findViewById(R.id.addOnsListView)
        addOnsByArrow = findViewById(R.id.addOnsByArrow)
        deductiblesFiltersParent = findViewById(R.id.deductiblesFiltersParent)
        deductiblesListView = findViewById(R.id.deductiblesListView)
        deductiblesOnsByArrow = findViewById(R.id.deductiblesOnsByArrow)
        accessoriesCoverParent = findViewById(R.id.accessoriesCoverParent)
        accessoriesCoversListView = findViewById(R.id.accessoriesCoversListView)
        goBack = findViewById(R.id.goBack)
        filterAddedList = findViewById(R.id.filter_added_list)
        applyFilters = findViewById(R.id.applyFilters)

        selectedFiltersAdapter = SelectedFiltersAdapter(this, selectedFilters) { removedFilter ->
            Log.d("CaQuoteAFilters", "Removing filter: ${removedFilter.filterName}")
            selectedFilters.remove(removedFilter)
            selectedFiltersAdapter.notifyDataSetChanged()
        }

        filterAddedList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filterAddedList.adapter = selectedFiltersAdapter

        goBack.setOnClickListener {
            Log.d("CaQuoteAFilters", "Go back clicked")
            val intent = Intent(this@CarInsuranceQuoteAllFilters, CarInsuranceQoutePlanForm::class.java)
            intent.putExtra("QuoteId", quoteId)
            startActivity(intent)
        }

        val filters = listOf(
            "Premium low to high",
            "Premium high to low",
            "IDV low to high",
            "IDV high to low"
        )
        Log.d("CaQuoteAFilters", "Sort By filters: $filters")

        val addOnOptions = listOf(
            "Zero depreciation",
            "24x7 roadside assistance",
            "Engine protection cover",
            "Consumables",
            "Key & lock replacement"
        )
        Log.d("CaQuoteAFilters", "Add Ons options: $addOnOptions")

        val deductiblesOnOptions = listOf(
            "₹2,500 voluntary deductibles",
            "₹5,000 voluntary deductibles",
            "₹7,500 voluntary deductibles",
            "₹15,000 voluntary deductibles"
        )
        Log.d("CaQuoteAFilters", "Deductibles options: $deductiblesOnOptions")

        val accessoriesCover = listOf(
            "Bi-fuel kit cover",
            "Electrical accessories",
            "Non-electrical accessories"
        )
        Log.d("CarInsuranceQuoteAllFilters", "Accessories Cover options: $accessoriesCover")

        val sortByAdapter = FilterArrayAdapter(
            this,
            filters,
            mutableSetOf(),
            selectedSortByPosition,
            "Sort By",
            false,
            this
        )
        sortByListView.adapter = sortByAdapter

        val addOnsAdapter = FilterArrayAdapter(
            this,
            addOnOptions,
            mutableSetOf(),
            -1,
            "Add Ons",
            true,
            this
        )
        addOnsListView.adapter = addOnsAdapter

        val deductiblesAdapter = FilterArrayAdapter(
            this,
            deductiblesOnOptions,
            mutableSetOf(),
            selectedDeductiblesPosition,
            "Deductibles",
            false,
            this
        )
        deductiblesListView.adapter = deductiblesAdapter

        val accessoriesAdapter = FilterArrayAdapter(
            this,
            accessoriesCover,
            mutableSetOf(),
            -1,
            "Accessories Cover",
            true,
            this
        )
        accessoriesCoversListView.adapter = accessoriesAdapter


        sortByFiltersParent.setOnClickListener {
            Log.d("CaQuoteAFilters", "Sort By clicked")
            toggleListViewVisibility(sortByListView, sortByArrow)
        }

        addOnsFiltersParent.setOnClickListener {
            Log.d("CaQuoteAFilters", "Add Ons clicked")
            toggleListViewVisibility(addOnsListView, addOnsByArrow)
        }

        deductiblesFiltersParent.setOnClickListener {
            Log.d("CaQuoteAFilters", "Deductibles clicked")
            toggleListViewVisibility(deductiblesListView, deductiblesOnsByArrow)
        }

        accessoriesCoverParent.setOnClickListener {
            Log.d("CaQuoteAFilters", "Accessories Cover clicked")
            toggleListViewVisibility(accessoriesCoversListView, accessoriesOnsByArrow)
        }


        applyFilters.setOnClickListener {
            val intent = Intent(this@CarInsuranceQuoteAllFilters, CarInsuranceQoutePlanForm::class.java)
            intent.putExtra("QuoteId", quoteId)
            intent.putExtra("Type", "Filter")

            val selectedSortBy = selectedFilters.filter { it.category == "Sort By" }.map { it.filterName }
            val selectedAddOns = selectedFilters.filter { it.category == "Add Ons" }.map { it.filterName }
            val selectedDeductibles = selectedFilters.filter { it.category == "Deductibles" }.map { it.filterName }
            val selectedAccessoriesCover = selectedFilters.filter { it.category == "Accessories Cover" }.map { it.filterName }


            intent.putStringArrayListExtra("SelectedSortBy", ArrayList(selectedSortBy))
            intent.putStringArrayListExtra("SelectedAddOns", ArrayList(selectedAddOns))
            intent.putStringArrayListExtra("SelectedDeductibles", ArrayList(selectedDeductibles))
            intent.putStringArrayListExtra("SelectedAccessoriesCover", ArrayList(selectedAccessoriesCover))

            startActivity(intent)
        }

    }

    private fun toggleListViewVisibility(listView: ListView, arrowImageView: ImageView) {
        val isVisible = listView.visibility == View.VISIBLE
        val targetVisibility = if (isVisible) View.GONE else View.VISIBLE
        val rotationAngle = if (isVisible) 0f else 180f
        ObjectAnimator.ofFloat(arrowImageView, "rotation", rotationAngle).apply {
            duration = 300
            start()
        }
        listView.visibility = targetVisibility
    }

    override fun onFilterSelected(categoryName: String, filterName: String) {
        Log.d("CarInsuranceQuoteAllFilters", "Filter selected: $categoryName - $filterName")


        if (categoryName == "Sort By" || categoryName == "Deductibles") {

            val existingFilter = selectedFilters.find { it.category == categoryName }
            if (existingFilter != null) {
                existingFilter.filterName = filterName
            } else {
                selectedFilters.add(FilterItem(categoryName, filterName))
            }
        } else {

            val existingFilter = selectedFilters.find { it.category == categoryName && it.filterName == filterName }
            if (existingFilter == null) {
                selectedFilters.add(FilterItem(categoryName, filterName))
            } else {
                selectedFilters.remove(existingFilter)
            }
        }

        selectedFiltersAdapter.notifyDataSetChanged()
    }

}
