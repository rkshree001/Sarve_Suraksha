package com.bnet.sarvesuraksha.view.Landing_Page_Section.Term_Life_Insurance.Adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListAddOndetail
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListMainGet
import com.bnet.savresuraksha.R
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class TermLifeInsuranceQuoteAdapter(
    private val quotes: List<GetTermLifeQuoteListMainGet>?,
    private val paymentFrequency: String,
    private val listener: OnConfirmAndProceedClickListener
) : RecyclerView.Adapter<TermLifeInsuranceQuoteAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val policy_name: TextView = view.findViewById(R.id.policy_name)
        val policy_image: ImageView = view.findViewById(R.id.policy_image)
        val life_cover: TextView = view.findViewById(R.id.life_cover)
        val cover_upto: TextView = view.findViewById(R.id.cover_upto)
        val year_month: TextView = view.findViewById(R.id.year_month)
        val claim_settled: TextView = view.findViewById(R.id.claim_settled)
        val view_plan_details: TextView = view.findViewById(R.id.view_plan_details)
        val availabe_add_ons_parent: LinearLayout = view.findViewById(R.id.availabe_add_ons_parent)
        val availabe_add_ons_child: LinearLayout = view.findViewById(R.id.availabe_add_ons_child)
        val availabe_add_ons_parent_arrow_img: ImageView = view.findViewById(R.id.availabe_add_ons_parent_arrow_img)
        val confirmAndProceed: LinearLayout = view.findViewById(R.id.confirmAndProceed)
        val parentLL: LinearLayout = view.findViewById(R.id.parentLL)
        val recomendedLL: LinearLayout = view.findViewById(R.id.recomendedLL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.term_life_quote_list_rec_card, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes!![position]


        holder.policy_name.text = quote.planName
        holder.life_cover.text = formatToIndianCurrency(quote.sumAssured)

        holder.cover_upto.text = "${quote.coverUpto} years"
        holder.claim_settled.text = "${quote.claimSettled}%"

        Glide.with(holder.policy_image.context).load(quote.insurerImg).into(holder.policy_image)


        holder.year_month.text = when (paymentFrequency) {
            "monthly" -> "/ Monthly"
            "quarterly" -> "/ Quarterly"
            "halfYearly" -> "/ Half Yearly"
            "yearly" -> "/ Yearly"
            else -> ""
        }
        holder.availabe_add_ons_child.removeAllViews()

        val checkedAddOns = mutableListOf<GetTermLifeQuoteListAddOndetail>()

        quote.addOndetail?.forEach { addOn ->
            val addOnView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.add_on_item, holder.availabe_add_ons_child, false)

            val addOnCheckBox: CheckBox = addOnView.findViewById(R.id.add_on_checkBox)
            val dec_info: LinearLayout = addOnView.findViewById(R.id.dec_info)

            addOnCheckBox.text = "${addOn.addOnName} - ${formatToRupees(addOn.premium)}"

            addOnCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedAddOns.add(addOn)
                } else {
                    checkedAddOns.remove(addOn)
                }
            }
            dec_info.setOnClickListener {
                showTooltip(it, addOn.declaration.toString())
            }

            holder.availabe_add_ons_child.addView(addOnView)
        }


        holder.availabe_add_ons_child.visibility = View.GONE
        holder.availabe_add_ons_parent_arrow_img.rotation = 0f


        holder.confirmAndProceed.setOnClickListener {
            listener.onConfirmAndProceed(quote, position, checkedAddOns, quote.claimSettled,quote.insurerImg)
        }
        holder.view_plan_details.setOnClickListener {
            listener.onConfirmAndProceed(
                quote,
                position,
                checkedAddOns,
                quote.claimSettled,
                quote.insurerImg
            )
        }


        holder.availabe_add_ons_parent.setOnClickListener {
            val isVisible = holder.availabe_add_ons_child.visibility == View.VISIBLE
            if (isVisible) {
//                slideUp(holder.availabe_add_ons_child)
                holder.availabe_add_ons_child.visibility=View.GONE
                rotateArrow(holder.availabe_add_ons_parent_arrow_img, 180f, 0f)
            } else {
//                slideDown(holder.availabe_add_ons_child)
                holder.availabe_add_ons_child.visibility=View.VISIBLE
                rotateArrow(holder.availabe_add_ons_parent_arrow_img, 0f, 180f)
            }
        }

        if (position == 0) {
            holder.recomendedLL.visibility = View.VISIBLE
            holder.parentLL.setBackgroundResource(R.drawable.radius_4dp_recommended)
        } else {
            holder.recomendedLL.visibility = View.GONE
            holder.parentLL.setBackgroundResource(android.R.color.transparent)
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
        fun onConfirmAndProceed(
            quote: GetTermLifeQuoteListMainGet,
            position: Int,
            checkedAddOns: MutableList<GetTermLifeQuoteListAddOndetail>,
            claimSettled: String?,
            insurerImg: String?
        )
    }
    private fun slideDown(view: View) {
        view.visibility = View.VISIBLE
        view.translationY = -view.height.toFloat()
        view.animate()
            .translationY(0f)
            .setDuration(300)
            .start()
    }

    private fun slideUp(view: View) {
        view.animate()
            .translationY(-view.height.toFloat())
            .setDuration(300)
            .withEndAction {
                view.visibility = View.GONE
                view.requestLayout()
                view.invalidate()
            }
            .start()
    }
    private fun showTooltip(anchorView: View, message: String) {
        val context = anchorView.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tooltipView = inflater.inflate(R.layout.tooltip_layout, null)

        val tooltipText: TextView = tooltipView.findViewById(R.id.tooltip_text)
        tooltipText.text = message

        val popupWindow = PopupWindow(
            tooltipView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        popupWindow.showAsDropDown(anchorView, 0, -anchorView.height - 10)

        Handler(Looper.getMainLooper()).postDelayed({
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }
        }, 1000)
    }
    private fun formatToIndianCurrency(amount: Int?): String {
        return if (amount != null) {
            when {
                amount >= 1_00_00_000 -> {
                    val crorePart = amount / 1_00_00_000
                    "₹ $crorePart Crore"
                }
                amount >= 1_00_000 -> {
                    val lakhPart = amount / 1_00_000
                    "₹ $lakhPart Lakh"
                }
                else -> {
                    "₹ ${DecimalFormat("#,##,###").format(amount)}"
                }
            }
        } else {
            "₹0"
        }
    }

}
