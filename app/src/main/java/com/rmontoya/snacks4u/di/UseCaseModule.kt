package com.rmontoya.snacks4u.di

import com.rmontoya.snacks4u.cart.domain.AddProductToCartUseCase
import com.rmontoya.snacks4u.cart.domain.GetCartProductsUseCase
import com.rmontoya.snacks4u.cart.domain.RemoveCartProductUseCase
import com.rmontoya.snacks4u.cart.domain.repository.CartRepository
import com.rmontoya.snacks4u.product.data.repository.ProductRepositoryImpl
import com.rmontoya.snacks4u.product.domain.use_case.GetProductsUseCase
import com.rmontoya.snacks4u.search.domain.repository.RecentSearchRepository
import com.rmontoya.snacks4u.search.domain.use_case.DeleteAllRecentSearchUseCase
import com.rmontoya.snacks4u.search.domain.use_case.GetAllRecentSearchUseCase
import com.rmontoya.snacks4u.search.domain.use_case.InsertRecentSearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesGetAllUseCase(repository : ProductRepositoryImpl) : GetProductsUseCase {
        return GetProductsUseCase(repository)
    }
    @Provides
    @Singleton
    fun providesDeleteAllRecentSearchUseCase(repository: RecentSearchRepository) : DeleteAllRecentSearchUseCase{
        return DeleteAllRecentSearchUseCase(repository)
    }
    @Provides
    @Singleton
    fun providesGetAllRecentSearchUseCase(repository: RecentSearchRepository): GetAllRecentSearchUseCase{
        return GetAllRecentSearchUseCase(repository)
    }
    @Provides
    @Singleton
    fun providesInsertRecentSearchUseCase(repository: RecentSearchRepository) : InsertRecentSearchUseCase{
        return InsertRecentSearchUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesAddProductToCartUseCase(cartRepository: CartRepository) : AddProductToCartUseCase{
        return AddProductToCartUseCase(cartRepository)
    }
    @Provides
    @Singleton
    fun providesGetCartProductsUseCase(cartRepository : CartRepository): GetCartProductsUseCase {
        return GetCartProductsUseCase(cartRepository)
    }
    @Provides
    @Singleton
    fun providesRemoveCartProductUseCase(cartRepository: CartRepository): RemoveCartProductUseCase{
        return RemoveCartProductUseCase(cartRepository)
    }
}