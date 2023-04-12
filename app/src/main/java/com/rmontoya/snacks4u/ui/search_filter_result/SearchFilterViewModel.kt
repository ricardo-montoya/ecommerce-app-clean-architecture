package com.rmontoya.snacks4u.ui.search_filter_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmontoya.snacks4u.product.data.Resource
import com.rmontoya.snacks4u.product.domain.Product
import com.rmontoya.snacks4u.product.domain.use_case.GetProductsUseCase
import com.rmontoya.snacks4u.search.domain.use_case.GetAllRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    fun getProductsFilteredByKeyWord(keyword: String) {
        viewModelScope.launch {
            val productList = getProductsUseCase()
            if (productList is Resource.Success) {
                _products.postValue(productList.data!!.filterByKeyWord(keyword))
            }
        }
    }

    private fun List<Product>.filterByKeyWord(keyword: String): List<Product> {
        return this.filter {
            it.name.contains(keyword) ||
                    it.vendor.contains(keyword) ||
                    it.imageUrl.contains(keyword) ||
                    it.description.contains(keyword)
        }
    }
}