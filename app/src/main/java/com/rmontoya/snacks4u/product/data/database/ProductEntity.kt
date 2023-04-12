package com.rmontoya.snacks4u.product.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rmontoya.snacks4u.product.domain.Product
@Entity
data class ProductEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("product_price") val price: Double,
    @ColumnInfo("product_name") val name : String,
    @ColumnInfo("product_description") val description : String,
    @ColumnInfo("product_vendor") val vendor :String,
    @ColumnInfo("image_source_url") val imageUrl : String,
    @ColumnInfo("preparation_time_in_minutes") val prepTime : Int
)

fun ProductEntity.asDomainModel() : Product {
    return Product(
        name = this.name,
        price = this.price,
        description = this.description,
        vendor = this.vendor,
        imageUrl = this.imageUrl,
        prepTime = this.prepTime,
        id = this.id
    )
}
