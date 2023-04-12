package com.rmontoya.snacks4u.product.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM productentity")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM productentity WHERE id = :id")
    fun getProductById(id : String) : Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg products : ProductEntity)

    @Query("DELETE FROM productentity")
    fun clearAll()
}