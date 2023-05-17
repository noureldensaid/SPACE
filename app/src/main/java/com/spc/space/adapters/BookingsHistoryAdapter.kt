package com.spc.space.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.spc.space.R
import com.spc.space.databinding.BookingHistoryRvItemsBinding
import com.spc.space.models.bookingsHistory.History
import com.spc.space.utils.Helper

class BookingsHistoryAdapter : RecyclerView.Adapter<BookingsHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: BookingHistoryRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: History) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.room?.roomImages?.firstOrNull())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(bookingHistoryRoomIv)
                bookingHistoryStatus.apply {
                    text = when {
                        item.isUpcoming == true -> {
                            setTextColor(ContextCompat.getColor(context, R.color.md_green_800))
                            "Upcoming"
                        }
                        item.isCancelled == true -> {
                            setTextColor(ContextCompat.getColor(context, R.color.md_red_800))
                            "Canceled"
                        }
                        item.isMissed == true -> {
                            setTextColor(ContextCompat.getColor(context, R.color.md_orange_800))
                            "Missed"
                        }
                        else -> {
                            setTextColor(ContextCompat.getColor(context, R.color.md_grey_800))
                            "Done"
                        }
                    }
                }
                bookingHistoryWsName.text = item.room?.workspace?.name?.lowercase()?.trim()?.capitalize()
                bookingHistoryRoomName.text = item.room?.roomName?.lowercase()?.trim()?.capitalize()
                bookingHistoryWsLocation.text = item.room?.workspace?.location?.region.toString()
                bookingHistoryRoomTime.text = Helper.convertTimeFormat(item.startTime!!)
                    .toString() + " to " + Helper.convertTimeFormat(item.endTime!!).toString()
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
            BookingHistoryRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}