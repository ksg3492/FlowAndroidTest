package com.sunggil.flowandroidtest.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sunggil.flowandroidtest.R

/**
 * Activity 애니메이션 추가
 */
abstract class BaseActivity<VB : ViewDataBinding>(
    private val transitionMode : TransitionMode
) : AppCompatActivity() {
    private var _binding : VB? = null
    protected val binding get() = _binding!!

    abstract fun getLayout() : Int
    abstract fun setContentView()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        this._binding = DataBindingUtil.setContentView(this, getLayout())
        this.binding.lifecycleOwner = this

        when (transitionMode) {
            TransitionMode.HORIZON -> {
                overridePendingTransition(R.anim.horizon_enter, R.anim.none)
            }
            TransitionMode.VERTICAL -> {
                overridePendingTransition(R.anim.vertical_enter, R.anim.none)
            }
        }

        setContentView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun finish() {
        super.finish()

        when (transitionMode) {
            TransitionMode.HORIZON -> {
                overridePendingTransition(R.anim.none, R.anim.horizon_exit)
            }
            TransitionMode.VERTICAL -> {
                overridePendingTransition(R.anim.none, R.anim.vertical_exit)
            }
        }
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            //setDisplayHomeAsUpEnabled(true)
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    enum class TransitionMode {
        NONE,
        HORIZON,
        VERTICAL
    }
}