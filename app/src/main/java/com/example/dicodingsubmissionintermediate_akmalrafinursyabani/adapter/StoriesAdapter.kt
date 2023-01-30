package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.StoryCardBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.Story
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItem
import de.hdodenhof.circleimageview.CircleImageView

class StoriesAdapter(private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<StoriesAdapter.StoryHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback : StoriesPagingAdapter.OnItemClickCallback {
        fun onItemClicked(data: String, position: Int)
    }

    class StoryHolder(view: View) : ViewHolder(view) {
        val photoProfile: CircleImageView = view.findViewById(R.id.photo_profile)
        val name: TextView = view.findViewById(R.id.user_name)
        val location: TextView = view.findViewById(R.id.user_location)
        val story: ImageView = view.findViewById(R.id.user_story)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.story_card, parent, false))

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {

        val getLocation: String =
            if (listStory[position].lat == null || listStory[position].lon == null) {
                "Lokasi tidak ditemukan"
            } else {
                listStory[position].lat + ", " + listStory[position].lon
            }

        Glide.with(holder.itemView).load(listStory[position].photoUrl)
            .into(holder.photoProfile);
        holder.name.text = listStory[position].name
        holder.location.text = getLocation
        Glide.with(holder.itemView).load(listStory[position].photoUrl).into(holder.story);

        holder.itemView.setOnClickListener {

            listStory[position].name?.let { it1 ->
                onItemClickCallback.onItemClicked(
                    it1,
                    holder.adapterPosition
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return listStory.size
    }
}