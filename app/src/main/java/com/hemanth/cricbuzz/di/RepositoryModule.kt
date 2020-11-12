package com.hemanth.cricbuzz.di


import com.hemanth.cricbuzz.data.RepoService
import com.hemanth.cricbuzz.data.repository.HomeRepository
import com.hemanth.cricbuzz.data.repositoryImpl.HomeRepositoryImpl
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideHomeRepo( repoService: RepoService): HomeRepository = HomeRepositoryImpl(repoService)

}