package com.rmontoya.snacks4u.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rmontoya.snacks4u.R
import com.rmontoya.snacks4u.databinding.FragmentCartBinding
import com.rmontoya.snacks4u.ui.actions.swipe_to_delete.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartProductListAdapter
    private val viewModel: CartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        adapter = CartProductListAdapter()
        setupBindings()
        setupObservers()
        setupOnClickListeners()
        return binding.root
    }

    private fun setupBindings() {
        binding.cartProductsList.adapter = this.adapter
        itemTouchHelper.attachToRecyclerView(binding.cartProductsList)
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner) { cartList ->
            adapter.submitList(cartList)
            binding.swipeLayout.isRefreshing = false
            var totalPrice :Double = 0.0
            cartList.forEach{
                totalPrice += (it.product.price * it.amount)
            }
            val priceText = if(totalPrice > 0) ": $$totalPrice" else '.'
            binding.checkoutButton.text = StringBuilder().append(getString(R.string.checkout)).append(priceText)
        }
    }

    private fun setupOnClickListeners(){
        binding.swipeLayout.setOnRefreshListener {
            viewModel.reloadProducts()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadProducts()
    }
    private val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = adapter.getItemAtPosition(position)
            viewModel.removeProduct(item)
            viewModel.reloadProducts()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)

}