package com.sunggil.flowandroidtest.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    private var _binding : VB? = null
    protected val binding get() = _binding!!
    private var snackbar : Snackbar? = null
    private var snackbarText : String? = null

    abstract fun getLayout() : Int
    abstract fun setContentView()

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        this._binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        this.binding.lifecycleOwner = viewLifecycleOwner

        return this.binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        Log.e("SG2", "${this.javaClass.name} onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        this.setContentView()
    }

    protected fun showSnackbar(msg : String) {
        this.snackbarText = msg
        this.snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
        this.snackbar?.show()
    }

    override fun onDestroyView() {
        Log.e("SG2", "${this.javaClass.name} onDestroyView()")
        super.onDestroyView()
        _binding = null
    }
}