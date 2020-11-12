package com.hemanth.cricbuzz.di

import android.content.Context
import com.hemanth.cricbuzz.AppController
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindApplication(appController: AppController): Context
}