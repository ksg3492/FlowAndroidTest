package com.sunggil.flowandroidtest.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sunggil.flowandroidtest.data.ConstValue

@Entity(tableName = ConstValue.DB_TABLE_NAME_KEYWORD)
data class KeywordEntity(
    @SerializedName("time")
    @ColumnInfo(name = Column.TIME)
    var time : Long,

    @SerializedName("keyword")
    @ColumnInfo(name = Column.KEYWORD)
    @PrimaryKey
    var keyword : String
) {
    object Column {
        const val KEYWORD = "keyword"
        const val TIME = "time"
    }
}

fun KeywordEntity.mapper() : String {
    return this.keyword
}