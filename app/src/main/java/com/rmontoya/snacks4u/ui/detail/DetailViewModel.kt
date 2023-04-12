package com.rmontoya.snacks4u.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rmontoya.snacks4u.cart.domain.AddProductToCartUseCase
import com.rmontoya.snacks4u.cart.domain.model.CartProduct
import com.rmontoya.snacks4u.product.data.database.asDomainModel
import com.rmontoya.snacks4u.product.domain.Product
import com.rmontoya.snacks4u.product.domain.use_case.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    val getProductByIdUseCase: GetProductByIdUseCase,
    val addProductToCartUseCase: AddProductToCartUseCase,
    val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int>
        get() = _quantity

    private val _user = MutableLiveData<FirebaseUser?>()
    val userNotNull: Boolean
        get() = _user.value != null

    init {
        _quantity.value = 0
        _user.value = firebaseAuth.currentUser
    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            getProductByIdUseCase(id).collect() {
                it?.let {
                    _product.postValue(it.asDomainModel())
                }
            }
        }
    }

    fun increaseAmount() {
        _quantity.postValue(quantity.value?.plus(1))
    }

    fun decreaseAmount() {
        if (quantity.value!! > 0) {
            _quantity.postValue(quantity.value?.minus(1))
        }
    }

    fun reloadUser() {
        _user.postValue(firebaseAuth.currentUser)
    }

    fun addProductToCart() {
        viewModelScope.launch {
            product.value?.let {
                val cartProduct = CartProduct(
                    product = product.value!!,
                    amount = quantity.value!!
                )
                addProductToCartUseCase(cartProduct)
            }
        }
    }

}