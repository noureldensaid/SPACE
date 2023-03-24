package com.spc.space.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.spc.space.R
import com.spc.space.databinding.WorkspaceDetailsRvItemsBinding
import com.spc.space.models.UnsplashPhoto

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: WorkspaceDetailsRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UnsplashPhoto) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            binding.apply {
                workspaceName.text = "O-Space"
                workspaceLocation.text = "Louran,Alexandria"
                workingHoursText.text = "Working Hours"
                workspaceTime.text = "09:30 to 19:00"
                workspacePrice.text = "From 20 EGP/HOUR"
                Glide.with(itemView)
                    .load(item.urls.regular)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(workspaceIv)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem.urls.regular == newItem.urls.regular
        }

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WorkspaceDetailsRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((UnsplashPhoto) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}