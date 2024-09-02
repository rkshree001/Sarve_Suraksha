package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.model.Department
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Bike_Insurance.BikeInsurance
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance.CarInsurance
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Health_Insurance.HealthInsurance
import com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance.TermLifeInsurance

class CategoryAdapter(private val context: Context, private val categories: List<Department>,private val listener: OnCategoryClickListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    interface OnCategoryClickListener {
        fun onCategoryClick(categoryName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_rec_card, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.category_text.text = category.name
        holder.category_card_bg.setBackgroundResource(category.imageResource)

        if (category.name == "Car" || category.name == "Home") {
            holder.count_txt.visibility = View.VISIBLE
        } else {
            holder.count_txt.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            when (category.name) {
                "Car" -> {
                    val intent = Intent(context, CarInsurance::class.java)
                    context.startActivity(intent)
                }

                "Health" -> {
                    val intent = Intent(context, HealthInsurance::class.java)
                    context.startActivity(intent)
                }

                "Bike" -> {
                    val intent = Intent(context, BikeInsurance::class.java)
                    context.startActivity(intent)
                }

                "Term Life" -> {
                    val intent = Intent(context, TermLifeInsurance::class.java)
                    context.startActivity(intent)
                }
//
//                "Travel" -> {
//                    val intent = Intent(context, TravelInsuranceActivity::class.java)
//                    context.startActivity(intent)
//                }
//
//                "Home" -> {
//                    val intent = Intent(context, HomeInsuranceActivity::class.java)
//                    context.startActivity(intent)
//                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category_card_bg: LinearLayout = itemView.findViewById(R.id.category_card_bg)
        val count_txt: LinearLayout = itemView.findViewById(R.id.count_txt)
        val category_text: TextView = itemView.findViewById(R.id.category_text)
    }
}
