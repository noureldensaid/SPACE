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
import com.spc.space.models.workspaceRoom.RoomItem

class RoomsAdapter : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: WorkspaceRoomsRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RoomItem) {
            itemView.setOnClickListener { onItemClickListener?.invoke(item) }
            binding.apply {
                roomName.text = item.roomName?.lowercase()?.capitalize()
                roomType.text = item.type?.capitalize()
                roomCapacity.text = "${item.capacity} guests"
                 roomPrice.text = "From ${item.price} EGP/HOUR"
                Glide.with(itemView)
                    .load(item.roomImages?.firstOrNull())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(roomIv)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<RoomItem>() {
        override fun areItemsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
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

    var onItemClickListener: ((RoomItem) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}