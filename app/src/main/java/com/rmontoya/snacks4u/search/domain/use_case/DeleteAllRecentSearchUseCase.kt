package com.rmontoya.snacks4u.search.domain.use_case

import com.rmontoya.snacks4u.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAllRecentSearchUseCase
@Inject
constructor(
    val repository: RecentSearchRepository
) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            repository.deleteHistory()
        }
    }
}