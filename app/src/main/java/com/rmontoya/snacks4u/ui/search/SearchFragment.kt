package com.rmontoya.snacks4u.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rmontoya.snacks4u.databinding.FragmentSearchBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rmontoya.snacks4u.search.domain.model.RecentSearch
import com.rmontoya.snacks4u.search.ui.RecentSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

const val CLEAR_ITEM = "CLEARLISTITEM"

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: RecentSearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: RecentSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        this.context?.let { setOnClickListeners(it) } //RUN FIRST ALWAYS
        setUiComponents()
        setViewModelObservers()
        return binding.root
    }

    private fun setUiComponents() {
        binding.recentSearchList.adapter = adapter
    }

    private fun setViewModelObservers() {
        viewModel.searches.observe(viewLifecycleOwner) {
            adapter.submitList(it + RecentSearch(CLEAR_ITEM))
        }
    }

    private fun setOnClickListeners(context: Context) {
        adapter = RecentSearchAdapter {
            when (it) {
                CLEAR_ITEM -> viewModel.clearSearchHistory()
                else -> {
                    this.findNavController().navigate(
                        SearchFragmentDirections.actionSearchFragmentToSearchFilterFragment(it)
                    )
                }
            }
        }

        binding.searchBar.textInput.setOnEditorActionListener { _, _, _ ->
            val editable = binding.searchBar.textInput.text
            if (editable?.isNotEmpty() == true) {
                viewModel.registerNewSearch(RecentSearch(editable.toString()))
                this.findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToSearchFilterFragment(editable.toString())
                )
            }
            true
        }
    }


}