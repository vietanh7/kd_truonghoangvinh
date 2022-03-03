package com.example.reliatest.ui.main

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.reliatest.MainApplication
import com.example.reliatest.R
import com.example.reliatest.adapter.ProductAdapter
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.databinding.FragmentProductBinding
import com.example.reliatest.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductFragment : BaseFragment<FragmentProductBinding>(), View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_product

    private val viewModel: ProductViewModel by viewModel()

    private lateinit var productAdapter: ProductAdapter


    override fun initViews() {
        binding.fragment = this
        binding.isLoggedIn = MainApplication.instance.token != null
    }

    override fun initAdapters() {
        with(binding.incTableProduct.rcvProducts) {
            productAdapter = ProductAdapter()
            val dividerItemDecoration = DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
            adapter = productAdapter
        }
    }

    override fun initObservers() {
        viewModel.productsLiveData.observe(this) {
            productAdapter.submitList(it?.toMutableList())
        }
        if (MainApplication.instance.token != null) {
            viewModel.getProducts()
        }
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
                viewModel.productsLiveData.value = null
                binding.isLoggedIn = false
            }
        }
    }
}