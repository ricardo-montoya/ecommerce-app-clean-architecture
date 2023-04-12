package com.rmontoya.snacks4u.search.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM recentsearchentity ORDER BY id DESC")
    fun getAll() : Flow<List<RecentSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(recentSearch: RecentSearchEntity)

    @Query("DELETE FROM recentsearchentity")
    fun clearAll()

}