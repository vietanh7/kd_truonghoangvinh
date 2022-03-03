package com.example.reliatest.ui.main

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.reliatest.MainApplication
import com.example.reliatest.R
import com.example.reliatest.adapter.ProductAdapter
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.databinding.FragmentProductBinding
import com.example.reliatest.param.SearchProductParam
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductFragment : BaseFragment<FragmentProductBinding>(), View.OnClickListener,
    SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    override val layoutId: Int
        get() = R.layout.fragment_product

    private val viewModel: ProductViewModel by viewModel()

    private lateinit var productAdapter: ProductAdapter

    override fun initViews() {
        binding.fragment = this
        binding.isLoggedIn = MainApplication.instance.token != null
        if (binding.isLoggedIn) {
            binding.searchView.setOnQueryTextListener(this)
            binding.searchView.setOnCloseListener(this)
        }
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
        viewModel.networkError.observe(this) {
            PopupUtil.showPopupError(it.first)
        }
//        if (MainApplication.instance.token != null) {
//            viewModel.getProducts()
//        }
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
                val action = ProductFragmentDirections.actionProductToAdd()
                findNavController().navigate(action)
            }
            R.id.tvLogout -> {
                binding.isLoggedIn = false
                MainApplication.instance.setToken(null)
                viewModel.productsLiveData.value = null
                with(binding.searchView) {
                    setOnQueryTextListener(null)
                    setOnCloseListener(null)
                    clearFocus()
                    onActionViewCollapsed()
                }
            }
        }
    }

    override fun onQueryTextChange(input: String?): Boolean {
        if (input?.isEmpty() == true) {
            viewModel.getProducts()
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query?.isNotBlank() == true) {
            viewModel.searchProducts(SearchProductParam(query))
        }
        return true
    }

    override fun onClose(): Boolean {
        binding.searchView.clearFocus()
        binding.searchView.onActionViewCollapsed()
        return true
    }
}