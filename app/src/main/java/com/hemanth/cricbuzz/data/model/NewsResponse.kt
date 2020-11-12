package com.hemanth.cricbuzz.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsResponse(
    @SerializedName("articles")
    var articles: List<Article>,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int
) {
    data class Article(
        @SerializedName("author")
        var author: String,
        @SerializedName("content")
        var content: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("publishedAt")
        var publishedAt: String,
        @SerializedName("source")
        var source: Source,
        @SerializedName("title")
        var title: String,
        @SerializedName("url")
        var url: String,
        @SerializedName("urlToImage")
        var urlToImage: String?,
        var timeStamp:Long?
    ) : Serializable {
        data class Source(
            @SerializedName("id")
            var id: Any,
            @SerializedName("name")
            var name: String
        ) : Serializable
    }
}