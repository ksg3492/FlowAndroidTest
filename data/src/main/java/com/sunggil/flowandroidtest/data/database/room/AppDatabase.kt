package com.sunggil.flowandroidtest.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunggil.flowandroidtest.data.database.dao.KeywordDAO
import com.sunggil.flowandroidtest.data.database.dao.MovieDAO
import com.sunggil.flowandroidtest.data.database.entity.KeywordEntity
import com.sunggil.flowandroidtest.data.database.entity.MovieEntity
import com.sunggil.flowandroidtest.data.database.entity.mapper
import com.sunggil.flowandroidtest.domain.Movie
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Database(
    entities = [KeywordEntity::class, MovieEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getKeywordDAO() : KeywordDAO
    abstract fun getMovieDAO() : MovieDAO

    /**
     * 키워드 관련
     */
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

    /**
     * 즐겨찾기 관련
     */
    fun selectMovie(id : String) : Maybe<Movie?> {
        return this.getMovieDAO().selectMovie(id).map { it.mapper() }
    }

    fun selectMovies() : Flowable<ArrayList<Movie>> {
        return this.getMovieDAO().selectMovies().map { list ->
            list.map {
                it.mapper()
            } as ArrayList<Movie>
        }
    }

    fun insertMovie(movie : Movie) : Maybe<Long> {
        return this.getMovieDAO().insertMovie(movie.mapper().apply { time = System.currentTimeMillis() })
    }

    fun deleteMovie(movie : Movie) : Maybe<Int> {
        return this.getMovieDAO().deleteMovie(movie.mapper())
    }
}