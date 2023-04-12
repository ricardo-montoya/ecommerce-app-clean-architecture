package com.rmontoya.snacks4u.search.domain.model

import com.rmontoya.snacks4u.search.data.database.RecentSearchEntity

data class RecentSearch(
    val query : String
)

fun RecentSearch.asEntityModel() : RecentSearchEntity{
    return RecentSearchEntity(
        id =  null,
        query = this.query
    )
}
