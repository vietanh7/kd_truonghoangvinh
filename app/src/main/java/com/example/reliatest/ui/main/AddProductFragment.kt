package com.example.reliatest.ui.main

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.reliatest.R
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.databinding.FragmentAddProductBinding
import com.example.reliatest.param.AddProductParam
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AddProductFragment : BaseFragment<FragmentAddProductBinding>(), View.OnClickListener {

    override val toolbarLayoutId: Int
        get() = R.layout.layout_toolbar

    override val layoutId: Int
        get() = R.layout.fragment_add_product

    //    private val viewModel: ProductViewModel by viewModel()
    private val viewModel by sharedViewModel<ProductViewModel>()


    override fun toolbarFunc(curActivity: AppCompatActivity?, toolbar: Toolbar?) {
        toolbar?.run {
            findViewById<AppCompatTextView>(R.id.tvTitle)?.text = getString(R.string.add_product)
            findViewById<AppCompatImageButton>(R.id.btnBack)?.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.fragment = this
    }

    override fun initObservers() {
        viewModel.addProductLiveData.observe(this) {
            it?.let {
                findNavController().popBackStack()
            }
        }
    }

    private fun handleAddProduct() {
        val sku = binding.tfSku.editText?.text.toString()
        val name = binding.tfName.editText?.text.toString()
        val quantity = binding.tfQty.editText?.text.toString()
        val price = binding.tfPrice.editText?.text.toString()
        val unit = binding.tfUnit.editText?.text.toString()
        val status = binding.tfStatus.editText?.text.toString()
        if (sku.isBlank() || name.isBlank() || quantity.isBlank() || price.isBlank() || unit.isBlank() || status.isBlank()) {
            PopupUtil.showPopupError(getString(R.string.err_fill_all_required_field))
            return
        }
        viewModel.addProducts(
            AddProductParam(
                sku,
                name,
                quantity.toInt(),
                price.toInt(),
                unit,
                status.toInt()
            )
        )
    }

    override fun onDestroy() {
        viewModel.addProductLiveData.value = null
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAdd -> handleAddProduct()
        }
    }
}