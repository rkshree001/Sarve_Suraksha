package com.bnet.sarvesuraksha.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.sarvesuraksha.model_api.GetUserDetailMainRes
import com.bnet.savresuraksha.R
import de.hdodenhof.circleimageview.CircleImageView

class FamilyMemberAdapter(private val familyMembers: List<GetUserDetailMainRes>, private val onClick: (GetUserDetailMainRes) -> Unit) :
    RecyclerView.Adapter<FamilyMemberAdapter.FamilyMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyMemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.family_members_added, parent, false)
        return FamilyMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: FamilyMemberViewHolder, position: Int) {
        val familyMember = familyMembers[position]
        holder.bind(familyMember)
        holder.itemView.findViewById<LinearLayout>(R.id.viewFamilyMember).setOnClickListener { onClick(familyMember) }
    }

    override fun getItemCount(): Int = familyMembers.size

    class FamilyMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.FamilyMemberName)
        private val relationshipTextView: TextView = itemView.findViewById(R.id.memberRelationShip)
        private val imageView: CircleImageView = itemView.findViewById(R.id.dept_IamgeView)

        fun bind(familyMember: GetUserDetailMainRes) {

            nameTextView.text = familyMember.fullName.toString()
            relationshipTextView.text = familyMember.memberType.toString()
            // Load image using an image loading library like Glide or Picasso if needed
            // Glide.with(imageView.context).load(familyMember.getProfilePicture()).into(imageView)
        }
    }
}
