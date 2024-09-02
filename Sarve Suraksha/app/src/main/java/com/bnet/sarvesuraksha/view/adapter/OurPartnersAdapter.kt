package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.model.Department

class OurPartnersAdapter(private val context: Context, private val partners: List<Department>) :
    RecyclerView.Adapter<OurPartnersAdapter.PartnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_partner, parent, false)
        return PartnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartnerViewHolder, position: Int) {
        val partner = partners[position]
        holder.partnerImage.setImageResource(partner.imageResource)
    }

    override fun getItemCount(): Int {
        return partners.size
    }

    inner class PartnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val partnerImage: ImageView = itemView.findViewById(R.id.partnerImage)
    }
}
