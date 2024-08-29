package com.mr.mrplan.di

import com.mr.data.repository.DeepLinkRepositoryImpl
import com.mr.data.repository.SessionRepositoryImpl
import com.mr.domain.repository.DeepLinkRepository
import com.mr.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDeepLinkRepository(
        deepLinkRepositoryImpl: DeepLinkRepositoryImpl
    ): DeepLinkRepository

    @Binds
    abstract fun bindSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl
    ): SessionRepository
}