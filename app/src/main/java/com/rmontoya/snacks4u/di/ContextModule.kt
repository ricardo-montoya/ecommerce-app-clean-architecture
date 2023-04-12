package com.rmontoya.snacks4u.di

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context : Context) : Context{
        return context
    }
}