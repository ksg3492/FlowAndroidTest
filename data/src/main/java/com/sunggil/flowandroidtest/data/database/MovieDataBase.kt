package com.sunggil.flowandroidtest.data.database

import android.content.Context
import androidx.room.Room
import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.data.database.room.AppDatabase


class MovieDataBase constructor(
    applicationContext : Context,
) {
    private var database : AppDatabase? = null

    init {
        this.database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, ConstValue.DB_FILE_NAME).build()
    }

    /**
     * DB 객체 전달
     */
    fun getDatabase() = database
}