package com.example.easyworks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworks.R
import com.example.easyworks.models.EmployeeModel
import com.example.easyworks.models.Users

class EmpAdapter(private val empList: ArrayList<Users>):RecyclerView.Adapter<EmpAdapter.ViewHolder>(){

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: EmpAdapter.ViewHolder, position: Int) {
        val currentEmp = empList[position]
        holder.Empname.text = currentEmp.email
    }
    override fun getItemCount(): Int {
        return empList.size
    }
    class ViewHolder(itemView: View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val Empname : TextView = itemView.findViewById(R.id.tvEmpName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }


    }

}