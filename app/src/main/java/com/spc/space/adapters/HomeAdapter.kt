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
import com.spc.space.databinding.WorkspaceHomeRvItemsBinding
 import com.spc.space.models.workspace.WorkSpaceItem

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: WorkspaceHomeRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WorkSpaceItem) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            binding.apply {
                //  offerPercentage.text = item.user.username
                workspaceInfo.text = item.name?.lowercase()?.capitalize()

                Glide.with(itemView)
                    .load(item.images?.get(0))
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(workspaceIv)
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
            WorkspaceHomeRvItemsBinding.inflate(
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