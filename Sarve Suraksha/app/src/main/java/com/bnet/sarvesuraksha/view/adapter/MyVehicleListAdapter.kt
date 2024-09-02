package com.bnet.sarvesuraksha.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.model_api.GetListVehicleMainRes
import com.bnet.sarvesuraksha.model_api.GetUserDetailMainRes
import com.bnet.savresuraksha.R
import de.hdodenhof.circleimageview.CircleImageView

class MyVehicleListAdapter(private val familyMembers: List<GetListVehicleMainRes>, private val onClick: (GetListVehicleMainRes) -> Unit) :
    RecyclerView.Adapter<MyVehicleListAdapter.FamilyMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyMemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_rec_card, parent, false)
        return FamilyMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: FamilyMemberViewHolder, position: Int) {
        val familyMember = familyMembers[position]
        holder.bind(familyMember)
        holder.itemView.findViewById<LinearLayout>(R.id.viewVehicleOverView).setOnClickListener { onClick(familyMember) }
    }

    override fun getItemCount(): Int = familyMembers.size

    class FamilyMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val vehicleNumber: TextView = itemView.findViewById(R.id.vehicleNumber)
        private val vehicleModel: TextView = itemView.findViewById(R.id.vehicleModel)
        private val vehicleImageView: ImageView = itemView.findViewById(R.id.vehicleImageView)

        fun bind(familyMember: GetListVehicleMainRes) {

            vehicleNumber.text = familyMember.registrationNumber.toString()
            vehicleModel.text = familyMember.makerName.toString()+" "+familyMember.variantName
            // Load image using an image loading library like Glide or Picasso if needed
            // Glide.with(imageView.context).load(familyMember.getProfilePicture()).into(imageView)
        }
    }
}
