package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.view.Profile_Page_Section.My_Policies.MyPolicies.PolicyItem

class PoliciesAdapter(
    private val context: Context,
    private var policies: List<PolicyItem> = listOf(),
    private val listener: OnPolicyClickListener? = null

) : RecyclerView.Adapter<PoliciesAdapter.PolicyViewHolder>() {

    fun updatePolicies(newPolicies: List<PolicyItem>) {
        policies = newPolicies
        notifyDataSetChanged()
    }
    interface OnPolicyClickListener {
        fun onPolicyClick(policyType: String, policy: PolicyItem)
    }

//    fun getPolicyItem(policyType: String): PolicyItem {
//        return policies.find { it.policyType == policyType }
//            ?: throw IllegalArgumentException("Policy not found")
//    }

    class PolicyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val policyImage: ImageView = itemView.findViewById(R.id.policy_image)
        val policyName: TextView = itemView.findViewById(R.id.policy_name)
        val activePolicy: TextView = itemView.findViewById(R.id.active_policy)
        val expiredPolicy: TextView = itemView.findViewById(R.id.expired_policy)
        val roundTint: View = itemView.findViewById(R.id.round_tint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.manage_policy_rec_card, parent, false)
        return PolicyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        val policy = policies[position]

        holder.policyName.text = policy.policyType

        // Set text color based on active and expired counts
        val activeColor = if (policy.activeCount > 0) "#000000" else "#666666"
        val expiredColor = if (policy.expiredCount > 0) "#000000" else "#666666"

        holder.activePolicy.text = "Active: ${policy.activeCount}"
        holder.expiredPolicy.text = "Expired: ${policy.expiredCount}"

        holder.activePolicy.setTextColor(Color.parseColor(activeColor))
        holder.expiredPolicy.setTextColor(Color.parseColor(expiredColor))


//        val tintColor = when (policy.policyType) {
//            "Health" -> R.color.tint_health
//            "Travel" -> R.color.tint_travel
//            "Vehicle" -> R.color.tint_vehicle
//            else -> R.color.tint_health
//        }
//        holder.roundTint.setBackgroundColor(ContextCompat.getColor(context, tintColor))
//
//        val imageRes = when (policy.policyType) {
//            "Health" -> R.drawable.policies_health
//            "Travel" -> R.drawable.mdi_aeroplane
//            "Vehicle" -> R.drawable.maki_car
//            else -> R.drawable.policies_health
//        }
//        holder.policyImage.setImageResource(imageRes)

        when (policy.policyType) {
            "Health" -> {
                holder.roundTint.backgroundTintList = ContextCompat.getColorStateList(context, R.color.tint_health)
                holder.policyImage.setImageResource(R.drawable.policies_health)
            }
            "Travel" -> {
                holder.roundTint.backgroundTintList = ContextCompat.getColorStateList(context, R.color.tint_travel)
                holder.policyImage.setImageResource(R.drawable.mdi_aeroplane)
            }
            "Vehicle" -> {
                holder.roundTint.backgroundTintList = ContextCompat.getColorStateList(context, R.color.tint_vehicle)
                holder.policyImage.setImageResource(R.drawable.maki_car)
            }

        }

        holder.itemView.setOnClickListener {
            listener?.onPolicyClick(policy.policyType,policy)
//            val intent = Intent(context, MyPoliciesDetailedDetails::class.java).apply {
//                putExtra("policyName", policy.policyType)
//
//            }
//            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return policies.size
    }
}
