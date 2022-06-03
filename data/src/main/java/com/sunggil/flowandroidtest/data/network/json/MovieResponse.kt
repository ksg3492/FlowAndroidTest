package com.sunggil.flowandroidtest.data.network.json

import com.google.gson.annotations.SerializedName
import com.sunggil.flowandroidtest.domain.Movie

data class MovieResponse(
    @SerializedName("total")
    var total : Int,
    @SerializedName("start")
    var start : Int,
    @SerializedName("display")
    var display : Int,
    @SerializedName("items")
    var items : ArrayList<MovieItemResponse>? = arrayListOf()
)

fun MovieResponse.mapper() : ArrayList<Movie> {
    return this.items?.map { Movie(
        id = it.title + it.link,    //db 를 위해 unique key 생성
        title = it.title,
        link = it.link,
        image = it.image,
        pubDate = it.pubDate,
        userRating = it.userRating
    ) } as ArrayList<Movie>
}