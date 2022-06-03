package com.sunggil.flowandroidtest.data.network.json

import android.os.Build
import android.text.Html
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
        //db 를 위해 unique key 생성
        id = convertHtml(it.title) + convertHtml(it.link),
        title = convertHtml(it.title),
        link = it.link,
        image = convertHtml(it.image),
        pubDate = it.pubDate,
        userRating = it.userRating
    ) } as ArrayList<Movie>
}

/**
 * Html to String
 */
fun convertHtml(input : String) : String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(input).toString()
    }
}