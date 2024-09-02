package com.bnet.sarvesuraksha.view.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bnet.savresuraksha.R
import com.bnet.sarvesuraksha.model.Department

class DepartmentAdapter(
    private val context: Context,
    private var departmentList: List<Department>
) : RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_explore_rec, parent, false)
        return DepartmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = departmentList[position]
        holder.deptImageView.setImageResource(department.imageResource)
        holder.deptName.text = department.name
    }

    override fun getItemCount(): Int = departmentList.size

    fun updateList(newDepartmentList: List<Department>) {
        departmentList = newDepartmentList
        notifyDataSetChanged()
    }

    class DepartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deptImageView: ImageView = itemView.findViewById(R.id.dept_IamgeView)
        val deptName: TextView = itemView.findViewById(R.id.dept_name)
    }
}