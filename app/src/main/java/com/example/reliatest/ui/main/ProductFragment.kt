package com.example.reliatest.ui.main

import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.reliatest.MainApplication
import com.example.reliatest.R
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.databinding.FragmentProductBinding

class ProductFragment : BaseFragment<FragmentProductBinding>(), View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_product

    override fun initViews() {
        binding.fragment = this
        binding.isLoggedIn = MainApplication.instance.token != null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvRegister -> {
                val action = ProductFragmentDirections.actionProductToRegister()
                findNavController().navigate(action)
            }
            R.id.tvLogin -> {
                val action = ProductFragmentDirections.actionProductToLogin()
                findNavController().navigate(action)
            }
            R.id.tvAddProduct -> {
            }
            R.id.tvLogout -> {
                MainApplication.instance.setToken(null)
                binding.isLoggedIn = false
            }
        }
    }
}