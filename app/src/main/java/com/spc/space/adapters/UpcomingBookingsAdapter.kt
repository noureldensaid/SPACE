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
import com.spc.space.databinding.UpcomingBookingsRvItemsBinding
import com.spc.space.models.bookingsHistory.History
import com.spc.space.utils.Helper

class UpcomingBookingsAdapter : RecyclerView.Adapter<UpcomingBookingsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: UpcomingBookingsRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: History) {
            itemView.setOnClickListener { onItemClickListener?.invoke(item) }
            binding.apply {
                Glide.with(itemView)
                    .load(item.room.roomImages.firstOrNull())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(bookingRoomIv)

                bookingDate.text = Helper.convertTimeFormatToDate(item.dateCreated)
                bookingWsName.text =
                    item.room.workspace?.name?.lowercase()?.trim()?.capitalize()
                bookingRoomName.text = item.room.roomName.lowercase().trim().capitalize()
                bookingWsLocation.text = item.room.workspace?.location?.region.toString()
                bookingRoomTime.text = Helper.convertTimeFormat(item.startTime!!)
                    .toString() + " to " + Helper.convertTimeFormat(item.endTime!!).toString()
                bookingStatus.text = "Upcoming"
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UpcomingBookingsRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((History) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}