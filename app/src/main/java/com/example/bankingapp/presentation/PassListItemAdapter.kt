package com.example.bankingapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.databinding.LayoutPassItemBinding


class PassListItemAdapter :
    ListAdapter<PassListItem, PassListItemAdapter.MyViewHolder>(MainDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutPassItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MyViewHolder(
        private val binding: LayoutPassItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PassListItem) {
            if (item.isChecked) {
                binding.itemImage.setImageResource(R.drawable.icon_is_checked)
            } else {
                binding.itemImage.setImageResource(R.drawable.icon_is_not_checked)
            }
        }
    }
}