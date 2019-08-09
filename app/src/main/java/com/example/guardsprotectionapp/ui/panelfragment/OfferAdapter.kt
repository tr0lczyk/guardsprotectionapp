package com.example.guardsprotectionapp.ui.panelfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.guardsprotectionapp.databinding.ItemWorkOfferBinding
import com.example.guardsprotectionapp.models.OfferModel

class OfferAdapter(val clickListener: OfferListener) : ListAdapter<OfferModel, OfferAdapter.ViewHolder>(OfferDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }


    class ViewHolder private constructor(val binding: ItemWorkOfferBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: OfferModel, clickListener: OfferListener) {
            binding.viewModel = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWorkOfferBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    class OfferListener(val clickListener: (offerModel: OfferModel) -> Unit){
        fun onClick(offer: OfferModel) = clickListener(offer)
    }
}

class OfferDiffCallback : DiffUtil.ItemCallback<OfferModel>(){

    override fun areItemsTheSame(oldItem: OfferModel, newItem: OfferModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OfferModel, newItem: OfferModel): Boolean {
        return oldItem == newItem
    }
}