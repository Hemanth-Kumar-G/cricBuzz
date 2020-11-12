package com.hemanth.cricbuzz.ui.home

import com.hemanth.cricbuzz.data.model.NewsResponse
import com.hemanth.cricbuzz.di.scope.ActivityScoped
import com.hemanth.cricbuzz.ui.home.adapter.HomeNewsListAdapter
import dagger.Module
import dagger.Provides

/**
 *<h1>HomeModule</h1>
 *<p>dagger module class</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
@Module
class HomeModule {

    @ActivityScoped
    @Provides
    fun provideNewsList() = ArrayList<NewsResponse.Article>()

    @ActivityScoped
    @Provides
    fun provideNewsAdapter(newsList:ArrayList<NewsResponse.Article>)=HomeNewsListAdapter(newsList)

}