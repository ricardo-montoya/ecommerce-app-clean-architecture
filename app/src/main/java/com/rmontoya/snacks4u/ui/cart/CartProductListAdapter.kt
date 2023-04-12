package com.rmontoya.snacks4u.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.appendPlaceholders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.rmontoya.snacks4u.R
import com.rmontoya.snacks4u.cart.domain.model.CartProduct
import com.rmontoya.snacks4u.databinding.ComponentCartItemListBinding

class CartProductListAdapter : ListAdapter<CartProduct, CartProductViewHolder>(CartProductDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
    fun getItemAtPosition(position : Int): CartProduct = getItem(position)

}
class CartProductViewHolder(val binding : ComponentCartItemListBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : CartProduct){
        Glide.with(binding.root.context)
            .load(item.product.imageUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_downloading).error(R.drawable.ic_error))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.productImageView)
        binding.productNameTextView.text = item.product.name
        binding.productSinglePriceTextView.text = "$${item.product.price}"
        binding.amountTextView.text = "x${item.amount}"
        binding.itemTotalPriceTextView.text = "$${item.product.price * item.amount}"
    }

        companion object{
            fun from(parent : ViewGroup) : CartProductViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding : ComponentCartItemListBinding = ComponentCartItemListBinding.inflate(inflater, parent, false)
                return CartProductViewHolder(binding)
            }
        }
}
class CartProductDiffUtil : DiffUtil.ItemCallback<CartProduct>(){
    override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem == newItem
    }

}