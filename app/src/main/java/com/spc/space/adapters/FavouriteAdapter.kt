package com.spc.space.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.spc.space.R
import com.spc.space.databinding.FavouriteRvItemBinding
import com.spc.space.models.favs.Favorite
import com.spc.space.utils.Helper

@RequiresApi(Build.VERSION_CODES.O)
class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: FavouriteRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // ashel al goz2 bta3  clickk
        fun bind(item: Favorite) {
            binding.apply {
                workspaceName.text = item.name?.lowercase()?.capitalize()
                workspaceLocation.text = "${item.location.region}, ${item.location.city}"
                workspaceTime.text =
                    Helper.convert24To12(item.schedule.openingTime) + " to " + Helper.convert24To12(
                        item.schedule.closingTime)
                workspaceRatingBar.rating = item.avgRate.toFloat()

                Glide.with(itemView)
                    .load(item.images.firstOrNull())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(workspaceIv)
            }

        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FavouriteRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
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