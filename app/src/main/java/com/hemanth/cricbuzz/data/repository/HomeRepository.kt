package com.hemanth.cricbuzz.data.repository

import com.hemanth.cricbuzz.data.model.NewsResponse
import io.reactivex.Single
import retrofit2.Response

interface HomeRepository {

    fun getNewsDetails(): Single<Response<NewsResponse>>

}