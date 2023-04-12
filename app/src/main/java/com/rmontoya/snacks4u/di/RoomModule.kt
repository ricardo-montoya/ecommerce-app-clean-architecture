package com.rmontoya.snacks4u.di

import android.content.Context
import androidx.room.Room
import com.rmontoya.snacks4u.database.SnacksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const private val DATABASE_NAME = "snacks_database"

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesSnacksDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, SnacksDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesProductDao(
        snacksDatabase: SnacksDatabase
    ) = snacksDatabase.productDao()

    @Provides
    @Singleton
    fun providesRecentSearchDao(
        snacksDatabase: SnacksDatabase
    ) = snacksDatabase.recentSearchDao()
}