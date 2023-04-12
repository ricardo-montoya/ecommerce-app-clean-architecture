package com.rmontoya.snacks4u.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.rmontoya.snacks4u.R
import com.rmontoya.snacks4u.databinding.FragmentHomeBinding
import com.rmontoya.snacks4u.product.data.Resource
import com.rmontoya.snacks4u.ui.view_model.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val viewModel by viewModels<ProductViewModel>()
        setupOnClickListeners()

        val adapter = HomeListAdapter(onClickListener = { selectedProductId ->
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(selectedProductId)
            )
        }, viewType = R.layout.product_hor_item_list)

        val adapterPopularItems = HomeListAdapter(onClickListener = { selectedProductId ->
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(selectedProductId)
            )
        }, R.layout.product_hor_item_list_2)

        binding.homeList.adapter = adapter
        binding.popularProductsList.adapter = adapterPopularItems

        viewModel.productList.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is Resource.Failed -> {
                    binding.statusText.visibility = View.VISIBLE
                    binding.statusText.text = state.message
                }
                Resource.Loading -> {
                    binding.statusText.visibility = View.VISIBLE
                    binding.statusText.text = "Loading..."
                }
                is Resource.Success -> {
                    binding.statusText.visibility = View.GONE
                    adapter.submitList(state.data)
                    adapterPopularItems.submitList(state.data.shuffled())
                }
            }
        })
        return binding.root
    }

    private fun setupOnClickListeners() {
        binding.chipList.chipGroup.children.forEach {chip->
            val content = (chip as Chip).text.toString()
            chip.setOnClickListener {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFilterFragment(content))
            }
        }
    }
}