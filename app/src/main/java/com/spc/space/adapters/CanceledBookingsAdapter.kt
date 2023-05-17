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
import com.spc.space.databinding.CanceledBookingsRvItemsBinding
import com.spc.space.models.canceledBookingsHistory.CanceledHistory
import com.spc.space.utils.Helper

class CanceledBookingsAdapter : RecyclerView.Adapter<CanceledBookingsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CanceledBookingsRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CanceledHistory) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.room.roomImages.firstOrNull())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(bookingCanceledRoomIv)
                bookingCanceledWsName.text =
                    item.room.workspace.name.lowercase().trim().capitalize()
                bookingCanceledRoomName.text = item.room.roomName.lowercase().trim().capitalize()
                bookingCanceledWsLocation.text = item.room.workspace.location.region.toString()
                bookingCanceledRoomTime.text = Helper.convertTimeFormat(item.startTime!!)
                    .toString() + " to " + Helper.convertTimeFormat(item.endTime!!).toString()
                bookingCanceledStatus.text =
                    if (item.isCancelled) "Canceled" else if (item?.isMissed == true && !item.isCancelled) "Missed"
                    else "Canceled"
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CanceledHistory>() {
        override fun areItemsTheSame(
            oldItem: CanceledHistory,
            newItem: CanceledHistory
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CanceledHistory,
            newItem: CanceledHistory
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CanceledBookingsRvItemsBinding.inflate(
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