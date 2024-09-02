package com.bnet.sarvesuraksha.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.model_api.GetQuoteFormAddOn
import com.bnet.sarvesuraksha.model_api.GetQuoteFormMainRes
import com.bnet.savresuraksha.R
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CarInsuranceQuoteAdapter(
    private val quotes: List<GetQuoteFormMainRes>?,
    private val listener: OnConfirmAndProceedClickListener
) : RecyclerView.Adapter<CarInsuranceQuoteAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val policy_name: TextView = view.findViewById(R.id.policy_name)
        val policy_image: ImageView = view.findViewById(R.id.policy_image)
        val IdvCover: TextView = view.findViewById(R.id.IdvCover)
        val premium: TextView = view.findViewById(R.id.premium)
        val availabe_add_ons_parent: LinearLayout = view.findViewById(R.id.availabe_add_ons_parent)
        val availabe_add_ons_child: LinearLayout = view.findViewById(R.id.availabe_add_ons_child)
        val availabe_add_ons_parent_arrow_img: ImageView = view.findViewById(R.id.availabe_add_ons_parent_arrow_img)
        val confirmAndProceed: LinearLayout = view.findViewById(R.id.confirmAndProceed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_list_rec_card, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes!![position]
        holder.policy_name.text = quote.planName
        holder.IdvCover.text = formatToRupees(quote.iDVCover!!)
        holder.premium.text = formatToRupees(quote.premium!!) + "/year"

        Glide.with(holder.policy_image.context).load(quote.insurerImg).into(holder.policy_image)

        holder.availabe_add_ons_child.removeAllViews()


        val checkedAddOns = mutableListOf<GetQuoteFormAddOn>()

        quote.addOn?.forEach { addOn ->
            val addOnView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.add_on_item, holder.availabe_add_ons_child, false)
            val addOnName: TextView = addOnView.findViewById(R.id.add_on_name)
            val addOnCheckBox: CheckBox = addOnView.findViewById(R.id.add_on_checkBox)

            addOnName.text = "${addOn.addOnName} - ${formatToRupees(addOn.addOnPremium)}"

            addOnCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedAddOns.add(addOn)
                } else {
                    checkedAddOns.remove(addOn)
                }
            }

            holder.availabe_add_ons_child.addView(addOnView)
        }
        holder.availabe_add_ons_child.visibility = View.GONE
        holder.availabe_add_ons_parent_arrow_img.rotation = 0f

        holder.confirmAndProceed.setOnClickListener {
            listener.onConfirmAndProceed(quote, position, checkedAddOns)
        }
        holder.availabe_add_ons_parent.setOnClickListener {
            val isVisible = holder.availabe_add_ons_child.visibility == View.VISIBLE
            if (isVisible) {
                holder.availabe_add_ons_child.visibility = View.GONE
                rotateArrow(holder.availabe_add_ons_parent_arrow_img, 180f, 0f)
            } else {
                holder.availabe_add_ons_child.visibility = View.VISIBLE
                rotateArrow(holder.availabe_add_ons_parent_arrow_img, 0f, 180f)
            }
        }
    }

    override fun getItemCount() = quotes!!.size

    private fun formatToRupees(amount: Int?): String {
        return if (amount != null) {
            val formatter = DecimalFormat("#,##,###")
            "₹${formatter.format(amount)}"
        } else {
            "₹0"
        }
    }

    private fun rotateArrow(imageView: ImageView, fromDegrees: Float, toDegrees: Float) {
        val rotateAnimation = RotateAnimation(
            fromDegrees,
            toDegrees,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        imageView.startAnimation(rotateAnimation)
    }

    interface OnConfirmAndProceedClickListener {
        fun onConfirmAndProceed(quote: GetQuoteFormMainRes, position: Int, checkedAddOns: List<GetQuoteFormAddOn>)
    }
}
