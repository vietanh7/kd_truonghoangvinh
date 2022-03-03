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
import com.example.reliatest.model.Product
import com.example.reliatest.param.DeleteProductParam
import com.example.reliatest.param.SearchProductParam
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ProductFragment : BaseFragment<FragmentProductBinding>(), View.OnClickListener,
    SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    override val layoutId: Int
        get() = R.layout.fragment_product

    //    private val viewModel: ProductViewModel by viewModel()
    private val viewModel by sharedViewModel<ProductViewModel>()

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
            productAdapter =
                ProductAdapter(::onEditProduct, ::onDeleteProduct)
            val dividerItemDecoration = DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
            adapter = productAdapter
        }
    }

    override fun initObservers() {
        with(viewModel) {
            productsLiveData.observe(this@ProductFragment) {
                productAdapter.submitList(it?.toMutableList())
            }
            networkError.observe(this@ProductFragment) {
                PopupUtil.showPopupError(it.first)
            }
            if (binding.isLoggedIn && !isSearching && productsLiveData.value.isNullOrEmpty() ) {
                getProducts()
            }
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
                val action = ProductFragmentDirections.actionProductToAdd()
                findNavController().navigate(action)
            }
            R.id.tvLogout -> {
                viewModel.isSearching = false
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

    private fun onEditProduct(product: Product) {
        val action = ProductFragmentDirections.actionProductToUpdate(product)
        findNavController().navigate(action)
    }

    private fun onDeleteProduct(product: Product) {
        PopupUtil.showPopupConfirm(
            getString(R.string.msg_confirm_delete_product)
        ) {
            viewModel.deleteProducts(DeleteProductParam(product.sku ?: return@showPopupConfirm))
        }
    }


    override fun onQueryTextChange(input: String?): Boolean = false

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query?.isNotBlank() == true) {
            viewModel.isSearching = true
            viewModel.searchProducts(SearchProductParam(query))
        }
        return false
    }

    override fun onClose(): Boolean {
        if (binding.isLoggedIn && viewModel.isSearching) {
            viewModel.isSearching = false
            viewModel.getProducts()
        }
        return false
    }
}