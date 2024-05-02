package com.adisastrawan.storyapp.ui.liststory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.databinding.ItemStoryBinding
import com.bumptech.glide.Glide

class ListStoryAdapter : PagingDataAdapter<StoryEntity,ListStoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object  {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>(){
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryEntity,
                newItem: StoryEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder(private val binding:ItemStoryBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(story:StoryEntity){
            with(binding){
                tvItemName.text = story.username
                tvItemDescription.text = story.description
                Glide.with(itemView)
                    .load(story.imageUrl)
                    .into(ivItemPhoto)
            }
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("name",story.username)
                bundle.putString("description",story.description)
                bundle.putString("photoUrl",story.imageUrl)
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
        if (story != null) {
            holder.bind(story)
        }
    }
}