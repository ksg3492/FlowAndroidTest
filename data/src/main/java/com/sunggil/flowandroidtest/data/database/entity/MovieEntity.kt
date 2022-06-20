package com.sunggil.flowandroidtest.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.domain.Movie

@Entity(tableName = ConstValue.DB_TABLE_NAME_MOVIE)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = Column.ID)
    @SerializedName("id")
    var id : String,
    @ColumnInfo(name = Column.TITLE)
    @SerializedName("title")
    var title : String,
    @ColumnInfo(name = Column.LINK)
    @SerializedName("link")
    var link : String,
    @ColumnInfo(name = Column.IMAGE)
    @SerializedName("image")
    var image : String,
    @ColumnInfo(name = Column.PUBDATE)
    @SerializedName("pubDate")
    var pubDate : String,
    @ColumnInfo(name = Column.USERRATING)
    @SerializedName("userRating")
    var userRating : String,
    @SerializedName("time")
    @ColumnInfo(name = Column.TIME)
    var time : Long? = 0,
) {
    object Column {
        const val ID = "id"
        const val TITLE = "title"
        const val LINK = "link"
        const val IMAGE = "image"
        const val PUBDATE = "pubDate"
        const val USERRATING = "userRating"
        const val TIME = "time"
    }
}

fun MovieEntity.mapper() : Movie {
    return Movie(
        id = this.id,
        title = this.title,
        link = this.link,
        image = this.image,
        pubDate = this.pubDate,
        userRating = this.userRating,
    )
}

fun Movie.mapper() : MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        link = this.link,
        image = this.image,
        pubDate = this.pubDate,
        userRating = this.userRating
    )
}