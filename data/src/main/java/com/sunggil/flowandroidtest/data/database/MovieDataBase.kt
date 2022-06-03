package com.sunggil.flowandroidtest.data.database

import android.content.Context
import androidx.room.Room
import com.sunggil.flowandroidtest.data.database.room.AppDatabase


class MovieDataBase constructor(
    private val applicationContext : Context,
) {
    private val dbFileName = "MovieDB"
    private var database : AppDatabase? = null


    /**
     * database 파일 load
     */
    fun openDataBaseFile() {
        this.database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, dbFileName).build()
    }

    fun getDatabase() = database
}