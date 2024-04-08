package com.adisastrawan.storyapp.ui.liststory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.data.api.response.ListStoryItem
import com.adisastrawan.storyapp.databinding.ItemStoryBinding
import com.bumptech.glide.Glide

class ListStoryAdapter : ListAdapter<ListStoryItem,ListStoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object  {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder(private val binding:ItemStoryBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(story:ListStoryItem){
            with(binding){
                tvUsername.text = story.name
                tvDescription.text = story.description
                Glide.with(itemView)
                    .load(story.photoUrl)
                    .into(ivStoryImage)
            }
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("name",story.name)
                bundle.putString("description",story.description)
                bundle.putString("photoUrl",story.photoUrl)
                itemView.findNavController().navigate(R.id.action_listStoryFragment_to_detailStoryFragment,bundle)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }
}