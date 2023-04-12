package com.rmontoya.snacks4u.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rmontoya.snacks4u.product.data.database.ProductDao
import com.rmontoya.snacks4u.product.data.database.ProductEntity
import com.rmontoya.snacks4u.search.data.database.RecentSearchDao
import com.rmontoya.snacks4u.search.data.database.RecentSearchEntity

@Database(
    entities = [ProductEntity::class, RecentSearchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SnacksDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun recentSearchDao(): RecentSearchDao
}