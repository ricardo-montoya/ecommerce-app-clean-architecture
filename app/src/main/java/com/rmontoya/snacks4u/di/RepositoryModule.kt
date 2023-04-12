package com.rmontoya.snacks4u.di

import com.rmontoya.snacks4u.cart.data.remote.repository.CartRepositoryImpl
import com.rmontoya.snacks4u.cart.domain.repository.CartRepository
import com.rmontoya.snacks4u.product.data.repository.ProductRepositoryImpl
import com.rmontoya.snacks4u.product.domain.repository.ProductRespository
import com.rmontoya.snacks4u.search.data.repository.RecentSearchRepositoryImpl
import com.rmontoya.snacks4u.search.domain.repository.RecentSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductsRepository(productReopsitory: ProductRepositoryImpl): ProductRespository

    @Binds
    abstract fun bindRecentSearchRepository(recentSearch: RecentSearchRepositoryImpl): RecentSearchRepository

    @Binds
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository
}