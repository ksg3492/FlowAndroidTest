package com.sunggil.flowandroidtest.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunggil.flowandroidtest.base.BaseViewModel
import com.sunggil.flowandroidtest.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {
    private var _movie : MutableLiveData<Movie?> = MutableLiveData(null)
    var movie : LiveData<Movie?> = _movie

    fun setMovie(movie : Movie) {
        _movie.value = movie
    }
}