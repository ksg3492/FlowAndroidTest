package com.sunggil.flowandroidtest.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunggil.flowandroidtest.data.database.dao.KeywordDAO
import com.sunggil.flowandroidtest.data.database.entity.KeywordEntity
import com.sunggil.flowandroidtest.data.database.entity.mapper
import io.reactivex.rxjava3.core.Maybe
import java.util.*

@Database(
    entities = [KeywordEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getKeywordDAO() : KeywordDAO

    fun selectKeywords() : Maybe<ArrayList<String>> {
        return this.getKeywordDAO().selectKeyword().map { list ->
            list.map {
                it.mapper()
            } as ArrayList<String>
        }
    }

    fun insertKeyword(keyword : String) : Maybe<Long> {
        return this.getKeywordDAO().insertKeyword(KeywordEntity(System.currentTimeMillis(), keyword))
    }

    fun deleteKeywords() : Maybe<Int> {
        return this.getKeywordDAO().deleteKeywords()
    }

}