package com.sunggil.flowandroidtest.ui.activity.detail

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.NavigationArgument
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ActivityDetailBinding
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity
    : BaseActivity<ActivityDetailBinding>(TransitionMode.HORIZON), View.OnClickListener {

    private val detailViewModel : DetailViewModel by viewModels()

    @Inject
    lateinit var requestManager : RequestManager

    override fun getLayout() : Int = R.layout.activity_detail

    override fun setContentView() {
        this.parseArguments()

        setSupportActionBar(this.binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)


        this.detailViewModel.movie.observe(this, Observer {
            it?.let {
                this.requestManager.load(it.image)
                    .centerCrop()
                    .error(R.drawable.img_error)
                    .into(this.binding.ivThumb)

                this.binding.tvTitle.text = it.title
            }
        })

        this.binding.ibFavorite.setOnClickListener(this)
        this.binding.ibUrl.setOnClickListener(this)
    }

    /**
     * Arguments 파싱
     */
    private fun parseArguments() {
        intent?.extras?.let {
            if (it.containsKey(NavigationArgument.ARGUMENT_MOVIE)) {
                if (it.getSerializable(NavigationArgument.ARGUMENT_MOVIE) is Movie) {
                    val movie = it.getSerializable(NavigationArgument.ARGUMENT_MOVIE) as Movie
                    this.detailViewModel.setMovie(movie)
                }
            }
        }
    }

    override fun onClick(v : View?) {
        when (v?.id) {
            this.binding.ibFavorite.id -> {

            }
            this.binding.ibUrl.id -> {
                try {
                    this.detailViewModel.movie.value?.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
                        startActivity(intent)
                    }
                } catch (e : Exception) {
                    showSnackbar(getString(R.string.error_browser))
                }
            }
        }

    }
}