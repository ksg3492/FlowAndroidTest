package com.sunggil.flowandroidtest.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.data.database.entity.Column
import com.sunggil.flowandroidtest.data.database.entity.KeywordEntity
import io.reactivex.rxjava3.core.Maybe

@Dao
interface KeywordDAO {
    @Query("select * from ${ConstValue.DB_TABLE_NAME} order by ${Column.TIME} DESC limit ${ConstValue.DB_QUERY_LIMIT}")
    fun selectKeyword() : Maybe<List<KeywordEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeyword(users: KeywordEntity) : Maybe<Long>
}