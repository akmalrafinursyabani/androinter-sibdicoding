package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.StoryCardBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.DetailStoryActivity
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.MainActivity

class StoriesPagingAdapter :
    PagingDataAdapter<ListStoryItem, StoriesPagingAdapter.StoryHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

    inner class StoryHolder(private val binding: StoryCardBinding) :
        ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.userName.text = data.name
            Glide.with(binding.root).load(data.photoUrl).into(binding.userStory);
            Glide.with(binding.root).load(data.photoUrl).into(binding.photoProfile);
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(data.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val binding = StoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}