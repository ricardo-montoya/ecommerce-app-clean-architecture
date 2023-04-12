package com.rmontoya.snacks4u.search.data.repository

import com.rmontoya.snacks4u.search.data.database.RecentSearchDao
import com.rmontoya.snacks4u.search.data.database.asDomainModel
import com.rmontoya.snacks4u.search.domain.model.RecentSearch
import com.rmontoya.snacks4u.search.domain.model.asEntityModel
import com.rmontoya.snacks4u.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSearchRepositoryImpl
@Inject
constructor(
    val recentSearchDao: RecentSearchDao
) : RecentSearchRepository {
    override suspend fun getRecentSearchFromDatabase(): Flow<List<RecentSearch>> {
        return recentSearchDao.getAll().map { list ->
            list.map { it.asDomainModel() }
        }
    }

    override suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        recentSearchDao.insertSearch(recentSearch.asEntityModel())
    }

    override suspend fun deleteHistory() {
        recentSearchDao.clearAll()
    }
}