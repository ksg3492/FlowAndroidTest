package com.sunggil.flowandroidtest.ui.activity.detail

import com.sunggil.flowandroidtest.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {
    var imageUrl : String? = null
    var title : String? = null

}