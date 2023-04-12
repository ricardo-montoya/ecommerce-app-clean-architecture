package com.rmontoya.snacks4u.ui.search_filter_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.rmontoya.snacks4u.R
import com.rmontoya.snacks4u.databinding.ComponentSearchFilterItemBinding
import com.rmontoya.snacks4u.product.domain.Product

class SearchFilterListAdapter(val onClickListener: (productId: String) -> Unit) :
    ListAdapter<Product, SearchFilterViewHolder>(SearchFilterDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilterViewHolder {
        return SearchFilterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchFilterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }
}

class SearchFilterViewHolder private constructor(val binding: ComponentSearchFilterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Product,
        onClickListener: (productId: String) -> Unit
    ) {
        Glide.with(binding.root.context).load(item.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_downloading)
            ).into(binding.productImageView)
        binding.productNameTextView.text = item.name
        binding.productDescriptionTextView.text = item.description
        //TODO: Replace string for resources
        binding.timeTextView.text = "${item.prepTime}mins"
        binding.priceTextView.text = "$${item.price}"
        binding.root.setOnClickListener {
            onClickListener(item.id)
        }
    }

    companion object {
        fun from(parent: ViewGroup): SearchFilterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ComponentSearchFilterItemBinding.inflate(layoutInflater, parent, false)
            return SearchFilterViewHolder(binding)
        }
    }
}

class SearchFilterDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}