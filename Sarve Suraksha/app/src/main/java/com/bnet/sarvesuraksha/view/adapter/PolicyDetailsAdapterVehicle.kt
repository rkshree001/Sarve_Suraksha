package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.model_api.*
import com.bnet.savresuraksha.R
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PolicyDetailsAdapterVehicle<T>(
    private val context: Context,
    private val policies: List<T>?
) : RecyclerView.Adapter<PolicyDetailsAdapterVehicle<T>.PolicyDetailsViewHolder>() {

    inner class PolicyDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val policyImage: ImageView = itemView.findViewById(R.id.policy_image)
        val policyName: TextView = itemView.findViewById(R.id.policy_name)
        val policyNo: TextView = itemView.findViewById(R.id.policy_no)
        val dueDate: TextView = itemView.findViewById(R.id.dueDate)
        val coverageAmount: TextView = itemView.findViewById(R.id.coverage_amount)
        val premiumAmount: TextView = itemView.findViewById(R.id.premimum_amount)
        val headerLL: LinearLayout = itemView.findViewById(R.id.headerLL)
        val dueDateLL: LinearLayout = itemView.findViewById(R.id.dueDateLL)
        val headerText: TextView = itemView.findViewById(R.id.headerText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyDetailsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_health_policy_rec_card, parent, false)
        return PolicyDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PolicyDetailsViewHolder, position: Int) {
        val policy = policies?.get(position)
        Log.d("PolicyDetailsAdapter", "Binding policy at position $position: $policy")
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy")
        when (policy) {
            is VehicleActivePolicyRes -> {
                holder.headerLL.visibility=View.GONE
                holder.policyName.text = policy.insurerName
                holder.policyNo.text = policy.pNo
                try {
                    val date: Date? = inputDateFormat.parse(policy.due)
                    holder.dueDate.text = "Due - ${outputDateFormat.format(date)}"
                } catch (e: Exception) {

                    Log.e("PolicyDetailsAdapter", "Error parsing date: ${e.message}")
                }


                holder.dueDate.setTextColor(ContextCompat.getColor(context, R.color.policy_header_ActiveText))

                holder.dueDateLL.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.policy_due_Active)
                )

                holder.coverageAmount.text = formatToRupees(policy.idv!!)
                holder.premiumAmount.text = "Premium ₹${formatToCommas(policy.premium!!)}/year"

                Glide.with(context)
                    .load(policy.insurerImg)
                    .into(holder.policyImage)
            }
            is VehicleExpiredPolicyeRes -> {
                holder.headerLL.visibility=View.GONE
                holder.policyName.text = policy.insurerName
                holder.policyNo.text = policy.pNo

                try {
                    val date: Date? = inputDateFormat.parse(policy.due)
                    holder.dueDate.text = "Due - ${outputDateFormat.format(date)}"
                } catch (e: Exception) {

                    Log.e("PolicyDetailsAdapter", "Error parsing date: ${e.message}")
                }

                holder.coverageAmount.text = formatToRupees(policy.sumInsured!!)
                holder.premiumAmount.text = "Premium ₹${formatToCommas(policy.premium!!)}/year"
                Log.d("BINDADAT", "onBindViewHolder Expired: "+policy.sumInsured+"~~~"+policy.premium)

                Glide.with(context)
                    .load(policy.insurerImg)
                    .into(holder.policyImage)
            }
            else -> {

                Log.d("PolicyDetailsAdapter", "Unexpected policy type: $policy")
            }
        }
    }



    override fun getItemCount(): Int {
        return policies?.size ?: 0
    }

    private fun formatToRupees(amount: Int): String {
        val formatter = DecimalFormat("#,###")
        return "₹${formatter.format(amount)}"
    }

    private fun formatToCommas(amount: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(amount)
    }
}
