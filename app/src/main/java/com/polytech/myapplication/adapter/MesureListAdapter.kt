package com.polytech.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polytech.myapplication.databinding.MesureItemViewBinding
import com.polytech.myapplication.model.Mesure

class MesureListAdapter(val clickListener: MesureListener) : ListAdapter<Mesure, MesureListAdapter.ViewHolder>(MesureDiffCallback()) {

    class MesureDiffCallback : DiffUtil.ItemCallback<Mesure>() {
        override fun areItemsTheSame(oldItem: Mesure, newItem: Mesure): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mesure, newItem: Mesure): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = getItem(position)
        //holder.bind(item)
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: MesureItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Mesure, clickListener: MesureListener) {
            binding.mesure = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MesureItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}