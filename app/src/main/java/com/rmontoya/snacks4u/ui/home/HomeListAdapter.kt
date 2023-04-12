package com.rmontoya.snacks4u.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.rmontoya.snacks4u.R
import com.rmontoya.snacks4u.databinding.ProductHorItemList2Binding
import com.rmontoya.snacks4u.databinding.ProductHorItemListBinding
import com.rmontoya.snacks4u.product.domain.Product

class HomeListAdapter(val onClickListener: (productId: String) -> Unit, val viewType: Int) :
    ListAdapter<Product, HomeListAdapter.ProductSimpleViewHolder>(ProductDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return this.viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSimpleViewHolder {
        return ProductSimpleViewHolder.from(parent, viewType)
    }

    override fun onBindViewHolder(holder: ProductSimpleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ProductSimpleViewHolder(val binding: ViewBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Product,
            onClickListener: (productId: String) -> Unit
        ) {
            when (binding) {
                is ProductHorItemListBinding -> {
                    binding.productNameTextView.text = item.name
                    Glide.with(context).load(item.imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.ic_downloading)
                                .error(R.drawable.ic_error)
                        )
                        .into(binding.productImageView)
                }
                is ProductHorItemList2Binding -> {
                    binding.productNameTextView.text = item.name
                    Glide.with(context).load(item.imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.ic_downloading)
                                .error(R.drawable.ic_error)
                        )
                        .into(binding.productImageView)
                }

            }
            binding.root.setOnClickListener {
                onClickListener(item.id)
            }
        }

        companion object {
            fun from(parent: ViewGroup, viewType: Int): ProductSimpleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ViewBinding
                when (viewType) {
                    R.layout.product_hor_item_list_2 -> {
                        binding =
                            ProductHorItemList2Binding.inflate(layoutInflater, parent, false)
                    }
                    else -> {
                        binding =
                            ProductHorItemListBinding.inflate(layoutInflater, parent, false)
                    }
                }
                return ProductSimpleViewHolder(binding, parent.context)
            }
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}