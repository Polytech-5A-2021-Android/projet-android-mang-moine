package com.polytech.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polytech.myapplication.R
import com.polytech.myapplication.model.User
import java.text.SimpleDateFormat
import java.util.*

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lastnameTv: TextView = itemView.findViewById(R.id.tv_lastname_item)
        val firstnameTv: TextView = itemView.findViewById(R.id.tv_firstname_item)
        val birthdayTv: TextView = itemView.findViewById(R.id.tv_birthday_item)
        val genderTv: TextView = itemView.findViewById(R.id.tv_gender_item)
    }

    var data = listOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        val date = Date(item.birthdayDate)
        val format = SimpleDateFormat("dd/MM/yyyy")

        holder.firstnameTv.text =  item.firstname
        holder.lastnameTv.text =  item.lastname
        holder.birthdayTv.text =  format.format(date)
        holder.genderTv.text =  item.gender

    }
}