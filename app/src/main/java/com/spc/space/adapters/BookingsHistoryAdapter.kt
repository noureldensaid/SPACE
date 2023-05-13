package com.spc.space.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spc.space.databinding.BookingHistoryRvItemsBinding
import com.spc.space.models.BookingsHistory.History

class BookingsHistoryAdapter : RecyclerView.Adapter<BookingsHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: BookingHistoryRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: History) {

            binding.apply {
                //workspaceName.text = item.name?.lowercase()?.capitalize()
                roomName.text = "Room : ${item.room?.lowercase()?.capitalize()}"
                workspacePrice.text = "${item.price.toString()} EGP/HOUR"
                workspaceTime.text = "${item.startTime} to ${item.endTime}"


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