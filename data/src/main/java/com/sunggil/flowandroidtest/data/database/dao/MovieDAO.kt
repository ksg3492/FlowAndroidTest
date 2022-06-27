package com.sunggil.flowandroidtest.data.database.dao

import androidx.room.*
import com.sunggil.flowandroidtest.data.ConstValue
import com.sunggil.flowandroidtest.data.database.entity.MovieEntity
import com.sunggil.flowandroidtest.data.database.entity.MovieEntity.Column
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface MovieDAO {
    @Query("select * from ${ConstValue.DB_TABLE_NAME_MOVIE} where ${Column.ID} = :id limit 1")
    fun selectMovie(id : String) : Maybe<MovieEntity>

    @Query("select * from ${ConstValue.DB_TABLE_NAME_MOVIE} order by ${Column.TIME} DESC")
    fun selectMovies() : Flowable<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(users : MovieEntity) : Maybe<Long>

    @Delete
    fun deleteMovie(movie : MovieEntity) : Maybe<Int>
}