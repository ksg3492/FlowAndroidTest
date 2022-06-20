package com.sunggil.flowandroidtest.ui.activity.detail

import androidx.activity.viewModels
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.NavigationArgument
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ActivityDetailBinding
import com.sunggil.flowandroidtest.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(TransitionMode.HORIZON) {

    private val detailViewModel : DetailViewModel by viewModels()

    @Inject
    lateinit var requestManager : RequestManager

    override fun getLayout() : Int = R.layout.activity_detail

    override fun setContentView() {
        this.parseArguments()

        setSupportActionBar(this.binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        this.supportActionBar?.title = getString(R.string.title_detail)

        this.requestManager.load(this.detailViewModel.imageUrl)
            .centerCrop()
            .error(R.drawable.img_error)
            .into(this.binding.ivThumb)

        this.binding.tvTitle.text = this.detailViewModel.title
    }

    /**
     * Arguments 파싱
     */
    private fun parseArguments() {
        intent?.extras?.let {
            if (it.containsKey(NavigationArgument.ARGUMENT_IMAGE)) {
                val image = it.getString(NavigationArgument.ARGUMENT_IMAGE)
                if (image.isNullOrEmpty().not()) {
                    this.detailViewModel.imageUrl = image!!
                }
            }
            if (it.containsKey(NavigationArgument.ARGUMENT_TITLE)) {
                val title = it.getString(NavigationArgument.ARGUMENT_TITLE)
                if (title.isNullOrEmpty().not()) {
                    this.detailViewModel.title = title!!
                }
            }
        }
    }
}