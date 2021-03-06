package com.sunggil.flowandroidtest.data.database.dao

import androidx.room.*
import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.data.database.entity.KeywordEntity.Column
import com.sunggil.flowandroidtest.data.database.entity.KeywordEntity
import io.reactivex.rxjava3.core.Maybe

@Dao
interface KeywordDAO {
    @Query("select * from ${ConstValue.DB_TABLE_NAME_KEYWORD} order by ${Column.TIME} DESC limit ${ConstValue.DB_QUERY_LIMIT}")
    fun selectKeyword() : Maybe<List<KeywordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeyword(users: KeywordEntity) : Maybe<Long>

    @Query("delete FROM ${ConstValue.DB_TABLE_NAME_KEYWORD} where ${Column.KEYWORD} NOT IN (select ${Column.KEYWORD} from ${ConstValue.DB_TABLE_NAME_KEYWORD} order by ${Column.TIME} DESC limit ${ConstValue.DB_QUERY_LIMIT})")
    fun deleteKeywords() : Maybe<Int>
}