package com.sunggil.flowandroidtest.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.sunggil.flowandroidtest.data.ConstValue

object Column {
    const val KEYWORD = "keyword"
    const val TIME = "time"
}

@Entity(tableName = ConstValue.DB_TABLE_NAME)
data class KeywordEntity(
    @SerializedName("time")
    @ColumnInfo(name = Column.TIME)
    var time : Long,

    @SerializedName("keyword")
    @ColumnInfo(name = Column.KEYWORD)
    var keyword : String
)

fun KeywordEntity.mapper() : String {
    return this.keyword
}