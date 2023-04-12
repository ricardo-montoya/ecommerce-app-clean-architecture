package com.rmontoya.snacks4u.search.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmontoya.snacks4u.product.data.Resource
import com.rmontoya.snacks4u.product.domain.Product
import com.rmontoya.snacks4u.product.domain.use_case.GetProductsUseCase
import com.rmontoya.snacks4u.search.domain.model.RecentSearch
import com.rmontoya.snacks4u.search.domain.use_case.DeleteAllRecentSearchUseCase
import com.rmontoya.snacks4u.search.domain.use_case.GetAllRecentSearchUseCase
import com.rmontoya.snacks4u.search.domain.use_case.InsertRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel
@Inject constructor(
    val getAllRecentSearchUseCase: GetAllRecentSearchUseCase,
    val deleteAllRecentSearchUseCase: DeleteAllRecentSearchUseCase,
    val insertAllRecentSearchUseCase: InsertRecentSearchUseCase
) : ViewModel() {

    private val _searches = MutableLiveData<List<RecentSearch>>()
    val searches: LiveData<List<RecentSearch>>
        get() = _searches
    init {
        getAllRecentSearches()
    }

    fun getAllRecentSearches() {
        viewModelScope.launch {
            val searches = getAllRecentSearchUseCase()
            searches.collect(){
                _searches.postValue(it)
            }
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            deleteAllRecentSearchUseCase()
        }
    }

    fun registerNewSearch(recentSearch : RecentSearch){
       viewModelScope.launch {
           insertAllRecentSearchUseCase(recentSearch)
       }
    }

}