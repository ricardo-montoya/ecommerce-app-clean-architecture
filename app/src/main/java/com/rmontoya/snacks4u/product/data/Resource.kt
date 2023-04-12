package com.rmontoya.snacks4u.product.data

sealed class Resource<out R>{
    data class Success<out T>(val data : T) : Resource<T>()
    data class Failed(val message: String) : Resource<Nothing>()
    object Loading: Resource<Nothing>()
}