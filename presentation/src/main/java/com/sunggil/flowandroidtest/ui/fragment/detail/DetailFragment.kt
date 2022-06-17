package com.sunggil.flowandroidtest.ui.fragment.detail

import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.sunggil.flowandroidtest.NavigationArgument
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.FragmentDetailBinding
import com.sunggil.flowandroidtest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val detailViewModel : DetailViewModel by viewModels()

    @Inject
    lateinit var requestManager : RequestManager

    override fun getLayout() : Int = R.layout.fragment_detail

    override fun setContentView() {
        this.parseArguments()

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
        arguments?.let {
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