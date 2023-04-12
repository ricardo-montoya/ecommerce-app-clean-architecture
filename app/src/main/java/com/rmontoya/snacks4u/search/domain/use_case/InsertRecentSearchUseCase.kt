package com.rmontoya.snacks4u.search.domain.use_case

import com.rmontoya.snacks4u.search.domain.model.RecentSearch
import com.rmontoya.snacks4u.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertRecentSearchUseCase
@Inject
constructor(
    val repository: RecentSearchRepository
) {
    suspend operator fun invoke(recentSearch: RecentSearch) {
        withContext(Dispatchers.IO) {
            repository.insertRecentSearch(recentSearch)
        }
    }
}