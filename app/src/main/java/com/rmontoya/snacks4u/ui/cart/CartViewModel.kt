package com.rmontoya.snacks4u.ui.cart

import androidx.lifecycle.*
import com.rmontoya.snacks4u.cart.domain.GetCartProductsUseCase
import com.rmontoya.snacks4u.cart.domain.RemoveCartProductUseCase
import com.rmontoya.snacks4u.cart.domain.model.CartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel
@Inject
constructor(
    val getCartProductsUseCase: GetCartProductsUseCase,
    val removeCartProductUseCase: RemoveCartProductUseCase
) : ViewModel() {
    private val _products = MutableLiveData<List<CartProduct>>()
    val products: LiveData<List<CartProduct>>
        get() = _products

    init {
        reloadProducts()
    }

    fun reloadProducts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getCartProductsUseCase().collect{
                    _products.postValue(it)
                }
            }
        }
    }
    fun removeProduct(cartProduct : CartProduct){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                removeCartProductUseCase(cartProduct)
            }
        }
    }
}