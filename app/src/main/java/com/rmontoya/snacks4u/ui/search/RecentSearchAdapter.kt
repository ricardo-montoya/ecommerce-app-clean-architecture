package com.rmontoya.snacks4u.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.rmontoya.snacks4u.databinding.ClearHistoryButtonBinding
import com.rmontoya.snacks4u.databinding.ComponentRecentSearchItemBinding
import com.rmontoya.snacks4u.search.domain.model.RecentSearch

private const val CLEAR_ITEM_ID: Int = -1

class RecentSearchAdapter (private val onClickListener : (value : String) -> Unit):
    ListAdapter<RecentSearch, RecentSearchViewHoder>(RecentSearchDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            (itemCount - 1) -> CLEAR_ITEM_ID
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHoder {
        return RecentSearchViewHoder.from(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecentSearchViewHoder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }
}

class RecentSearchViewHoder constructor(private val binding: ViewBinding) :
    ViewHolder(binding.root) {
    fun bind(item: RecentSearch , onClickListener: (value: String) -> Unit) {
        when (binding) {
            is ComponentRecentSearchItemBinding -> {
                binding.recentSearchTextView.text = item.query
                binding.root.setOnClickListener{
                    onClickListener(item.query)
                }
            }
            is ClearHistoryButtonBinding -> {
                binding.clearTextView.setOnClickListener{
                    onClickListener(CLEAR_ITEM)
                }
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup, viewType: Int): RecentSearchViewHoder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ViewBinding
            when (viewType) {
                CLEAR_ITEM_ID -> {
                    binding = ClearHistoryButtonBinding.inflate(inflater, parent, false)
                }
                else -> {
                    binding = ComponentRecentSearchItemBinding.inflate(inflater, parent, false)
                }
            }
            return RecentSearchViewHoder(binding)

        }
    }
}

class RecentSearchDiffUtil : DiffUtil.ItemCallback<RecentSearch>() {
    override fun areItemsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
        return oldItem.query == newItem.query
    }

    override fun areContentsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
        return oldItem == newItem
    }

}
