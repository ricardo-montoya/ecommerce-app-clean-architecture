package com.rmontoya.snacks4u.search.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rmontoya.snacks4u.search.domain.model.RecentSearch

@Entity
class RecentSearchEntity(
    @PrimaryKey
    val id: Int?,
    @ColumnInfo("search_query")
    val query: String
)

fun RecentSearchEntity.asDomainModel(): RecentSearch{
    return RecentSearch(
        query = this.query
    )
}