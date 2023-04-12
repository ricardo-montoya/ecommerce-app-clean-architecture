package com.rmontoya.snacks4u.product.domain

import com.rmontoya.snacks4u.product.data.database.ProductEntity

data class Product(
    val id : String = "",
    val name: String = "",
    val price: Double = 99_999.0,
    val description: String = "",
    val vendor: String = "",
    val imageUrl: String = "",
    val prepTime: Int = 0
)
fun Product.asEntityModel() : ProductEntity {
    return ProductEntity(
        name = this.name,
        price = this.price,
        description = this.description,
        vendor = this.vendor,
        imageUrl = this.imageUrl,
        prepTime = this.prepTime,
        id = this.id
    )
}