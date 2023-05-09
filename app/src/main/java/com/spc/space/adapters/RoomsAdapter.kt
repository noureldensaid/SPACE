package com.spc.space.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.spc.space.R
import com.spc.space.databinding.WorkspaceRoomsRvItemsBinding
import com.spc.space.models.fake.UnsplashPhoto
import com.spc.space.models.workspaces.WorkSpaceItem

class RoomsAdapter : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: WorkspaceRoomsRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WorkSpaceItem) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            binding.apply {
                roomName.text = "Hall-1"
                roomType.text = "Meetings room"
                roomCapacity.text = "20 guests"
                roomTime.text = "09:30 to 19:00"
                roomPrice.text = "From 20 EGP/HOUR"
                Glide.with(itemView)
                    .load(item.images?.get(0))
                     .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(roomIv)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<WorkSpaceItem>() {
        override fun areItemsTheSame(oldItem: WorkSpaceItem, newItem: WorkSpaceItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkSpaceItem, newItem: WorkSpaceItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WorkspaceRoomsRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((WorkSpaceItem) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}