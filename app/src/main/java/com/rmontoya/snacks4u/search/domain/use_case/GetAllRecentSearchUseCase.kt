package com.rmontoya.snacks4u.search.domain.use_case

import com.rmontoya.snacks4u.search.domain.model.RecentSearch
import com.rmontoya.snacks4u.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllRecentSearchUseCase
@Inject
constructor(
    val repository: RecentSearchRepository
) {
    suspend operator fun invoke(): Flow<List<RecentSearch>> {
        return repository.getRecentSearchFromDatabase()
    }
}