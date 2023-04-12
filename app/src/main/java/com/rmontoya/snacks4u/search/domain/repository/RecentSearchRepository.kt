package com.rmontoya.snacks4u.search.domain.repository

import com.rmontoya.snacks4u.search.domain.model.RecentSearch
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    suspend fun getRecentSearchFromDatabase(): Flow<List<RecentSearch>>

    suspend fun insertRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteHistory()
}