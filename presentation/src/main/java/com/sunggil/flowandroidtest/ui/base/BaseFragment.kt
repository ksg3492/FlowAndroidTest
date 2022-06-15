package com.sunggil.flowandroidtest.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    private var _binding : VB? = null
    protected val binding get() = _binding!!

    abstract fun getLayout() : Int
    abstract fun setContentView()

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        this._binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)

        return this.binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setContentView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}