package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.model.ConutineMyPurchaseData

class MyPolicyAdapter(
    private val context: Context,
    private val policies: List<ConutineMyPurchaseData>
) : RecyclerView.Adapter<MyPolicyAdapter.PolicyViewHolder>() {

    class PolicyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val policyImage: ImageView = itemView.findViewById(R.id.policy_image)
        val policyName: TextView = itemView.findViewById(R.id.policy_name)
        val policy_amount: TextView = itemView.findViewById(R.id.policy_amount)
        val policy_coverage: TextView = itemView.findViewById(R.id.policy_coverage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.continue_my_purchase_rec_card, parent, false)
        return PolicyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        val policy = policies[position]
        holder.policyImage.setImageResource(policy.imageResId)
        holder.policyName.text = policy.policyName
        holder.policy_amount.text = policy.amount
        holder.policy_coverage.text = policy.amountCovered

    }

    override fun getItemCount(): Int {
        return policies.size
    }
}
