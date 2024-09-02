package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance

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
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance.CarInsuranceQoutePlanForm
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance.Adapters.FilterArrayAdapterTermLife
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance.Adapters.FilterArrayAdapterTermLifeCheckBox
import com.bnet.sarvesuraksha.view.adapter.SelectedFiltersAdapter
import com.bnet.savresuraksha.R

class TermLifeAllFilters : AppCompatActivity(),
    FilterArrayAdapterTermLife.OnFilterSelectedListener,
    FilterArrayAdapterTermLifeCheckBox.OnFilterSelectedListenerCheckBox {

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
        setContentView(R.layout.activity_term_life_insurance_quote_all_filters)

        quoteId = intent.getStringExtra("TermID")
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
            val intent = Intent(this@TermLifeAllFilters, TermLifeInsuranceGetQuoteFormList::class.java)
            intent.putExtra("TermID", quoteId)
            startActivity(intent)
        }

        val filters = listOf(
            "Recommended",
            "Premium low to high",
            "Premium high to low"
        )
        Log.d("TermLifeAllfiltersasd", "Sort By filters: $filters")

        val paymentTenure = listOf(
            "Pay for 5 Years",
            "Pay for 7 Years",
            "Pay for 10 Years",
            "Pay for 12 Years",
            "Pay for 15 Years",
            "Pay for 23 Years"
        )
        Log.d("TermLifeAllfiltersasd", "paymentTenure: $paymentTenure")

        val planTenure = listOf(
            "Return of premium",
            "Whole life (Cover upto 100 years)",
            "Zero cost plan"
        )
        Log.d("TermLifeAllfiltersasd", "planTenure: $planTenure")

        val insurerName = listOf(
            "ICICI Lombard",
            "Iffco Tokio",
            "Manipal Cigna",
            "Raheja QBE",
            "SBI",
            "Star Health",
            "Tata AIG"
        )

        Log.d("TermLifeAllfiltersasd", "insurerName: $insurerName")

        val sortByAdapter = FilterArrayAdapterTermLife(
            this,
            filters,
            mutableSetOf(),
            selectedSortByPosition,
            "Sort By",
            false,
            this
        )
        sortByListView.adapter = sortByAdapter

        val paymentTenureAdapter = FilterArrayAdapterTermLife(
            this,
            paymentTenure,
            mutableSetOf(),
            selectedAddOnsPosition,
            "Payment tenure",
            false,
            this
        )
        addOnsListView.adapter = paymentTenureAdapter

        val planTypeAdapter = FilterArrayAdapterTermLife(
            this,
            planTenure,
            mutableSetOf(),
            selectedDeductiblesPosition,
            "Plan type",
            false,
            this
        )
        deductiblesListView.adapter = planTypeAdapter

        val InsurerAdapter = FilterArrayAdapterTermLifeCheckBox(
            this,
            insurerName,
            mutableSetOf(),
            "Insurer",
            this
        )
        accessoriesCoversListView.adapter = InsurerAdapter


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
            val intent = Intent(this@TermLifeAllFilters, TermLifeInsuranceGetQuoteFormList::class.java)
            intent.putExtra("TermID", quoteId)
            intent.putExtra("Type", "Filter")

            val selectedSortBy = selectedFilters.filter { it.category == "Sort By" }.map { it.filterName }
            val SelectedPlanTenure = selectedFilters.filter { it.category == "Payment tenure" }.map { it.filterName }
            val SelectedPlanType = selectedFilters.filter { it.category == "Plan type" }.map { it.filterName }
            val SelectedInsurer = selectedFilters.filter { it.category == "Insurer" }.map { it.filterName }

            intent.putStringArrayListExtra("SelectedSortBy", ArrayList(selectedSortBy))
            intent.putStringArrayListExtra("SelectedPlanTenure", ArrayList(SelectedPlanTenure))
            intent.putStringArrayListExtra("SelectedPlanType", ArrayList(SelectedPlanType))
            intent.putStringArrayListExtra("SelectedInsurer", ArrayList(SelectedInsurer))

            startActivity(intent)
        }



    }
    private fun updateFilters(filterNames: List<String>, category: String) {
        filterNames.forEach { filterName ->
            val existingFilter = selectedFilters.find { it.category == category && it.filterName == filterName }
            if (existingFilter == null) {
                selectedFilters.add(FilterItem(category, filterName))
            }
        }
        selectedFiltersAdapter.notifyDataSetChanged()
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
        Log.d("TermLifeAllfiltersasd", "Filter selected: $categoryName - $filterName")


        if (categoryName == "Sort By" || categoryName == "Payment tenure"|| categoryName == "Plan type") {

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


    override fun onFilterSelectedCheckBox(
        categoryName: String,
        filterName: String,
        isSelected: Boolean
    ) {
        val existingFilter = selectedFilters.find { it.category == categoryName && it.filterName == filterName }
        if (existingFilter == null) {
            selectedFilters.add(FilterItem(categoryName, filterName))
        } else {
            selectedFilters.remove(existingFilter)
        }
        selectedFiltersAdapter.notifyDataSetChanged()
    }

}
