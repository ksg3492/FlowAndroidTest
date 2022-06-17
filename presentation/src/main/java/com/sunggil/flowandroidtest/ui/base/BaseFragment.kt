package com.sunggil.flowandroidtest.ui.base

import android.content.Context
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

    override fun onCreate(savedInstanceState : Bundle?) {
        Log.e("SG2", "${this.javaClass.name} onCreate()")
        super.onCreate(savedInstanceState)
    }

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

    override fun onAttach(context : Context) {
        Log.e("SG2", "${this.javaClass.name} onAttach()")
        super.onAttach(context)
    }

    override fun onDetach() {
        Log.e("SG2", "${this.javaClass.name} onDetach()")
        super.onDetach()
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

    override fun onDestroy() {
        Log.e("SG2", "${this.javaClass.name} onDestroy()")
        super.onDestroy()
    }
}