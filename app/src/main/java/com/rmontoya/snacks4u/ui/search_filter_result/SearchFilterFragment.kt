package com.rmontoya.snacks4u.ui.search_filter_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rmontoya.snacks4u.databinding.FragmentSearchFilterBinding
import com.rmontoya.snacks4u.ui.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterFragment : Fragment() {

    private val viewModel: SearchFilterViewModel by viewModels()
    private val args: SearchFilterFragmentArgs by navArgs()
    private lateinit var binding: FragmentSearchFilterBinding
    private var adapter = SearchFilterListAdapter() { productId ->
        this.findNavController().navigate(SearchFilterFragmentDirections.actionSearchFilterFragmentToDetailFragment(productId))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        viewModel.getProductsFilteredByKeyWord(args.keyword)
        setUiComponents()
        setObservers()
        return binding.root
    }

    private fun setUiComponents() {
        binding.reultsList.adapter = adapter
    }

    private fun setObservers() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }
    }


}