package com.hemanth.cricbuzz.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: BaseAppViewModelFactory): ViewModelProvider.Factory
}