package com.sunggil.flowandroidtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sunggil.flowandroidtest.data.network.ErrorCode
import com.sunggil.flowandroidtest.domain.BaseException
import com.sunggil.flowandroidtest.domain.usecase.EditKeywordsUseCase
import com.sunggil.flowandroidtest.domain.usecase.GetMovieListUseCase
import com.sunggil.flowandroidtest.ui.activity.main.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    lateinit var mainViewModel : MainViewModel

    @Inject
    lateinit var getMovieListUseCase : GetMovieListUseCase

    @Inject
    lateinit var editMovieListUseCase : EditKeywordsUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
        mainViewModel = MainViewModel(getMovieListUseCase, editMovieListUseCase)
    }

    @Test
    fun 영화검색_유효한키워드_성공케이스() {
        runBlocking {
            val result = getMovieListUseCase.searchMovieListByCoroutine("강아지")

            assertThat(result.isSuccess).isEqualTo(true)
        }
    }

    @Test
    fun 영화검색_빈키워드_에러케이스_빈키워드() {
        runBlocking {
            val result = getMovieListUseCase.searchMovieListByCoroutine("")

            when (val exception = result.exceptionOrNull()) {
                is BaseException -> {
                    assertThat(exception.errorCode).isEqualTo(ErrorCode.EMPTY_KEYWORD)
                }
                else -> {
                    assert(false)
                }
            }
        }
    }
}