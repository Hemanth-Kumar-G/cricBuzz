package com.hemanth.cricbuzz.di


import com.hemanth.cricbuzz.ui.home.HomeActivity
import com.hemanth.cricbuzz.di.scope.ActivityScoped
import com.hemanth.cricbuzz.ui.articleDetail.ArticleDetailActivity
import com.hemanth.cricbuzz.ui.home.HomeModule
import com.hemanth.cricbuzz.ui.splash.SplashActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [ViewModelModule::class]
)
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun homeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun articleDetailActivity(): ArticleDetailActivity

}