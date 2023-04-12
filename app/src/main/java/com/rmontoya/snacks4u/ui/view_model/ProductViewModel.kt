package com.rmontoya.snacks4u.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmontoya.snacks4u.product.data.Resource
import com.rmontoya.snacks4u.product.domain.Product
import com.rmontoya.snacks4u.product.domain.use_case.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel
@Inject
constructor(
    val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _isFailedState = MutableLiveData<Boolean>()
    val isFailedState : LiveData<Boolean>
        get() = _isFailedState

    private val _isLoadingState = MutableLiveData<Boolean>()
    val isLoadingState : LiveData<Boolean>
        get() = _isFailedState

    private val _productList = MutableLiveData<Resource<List<Product>>>()
    val productList: LiveData<Resource<List<Product>>>
        get() = _productList

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            val productList = getProductsUseCase()
            _productList.postValue(productList)
        }
    }

}