package com.hemanth.cricbuzz.data

import com.hemanth.cricbuzz.BuildConfig
import com.hemanth.cricbuzz.data.model.NewsResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface RepoService {

    companion object {
        const val COUNTRY_CODE = "in"
    }

    @GET("v2/top-headlines?country=${COUNTRY_CODE}&apiKey=${BuildConfig.API_key}")
    fun getNewsDetails(): Single<Response<NewsResponse>>

}