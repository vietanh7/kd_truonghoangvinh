package com.example.reliatest.ui.main

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reliatest.R
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.databinding.FragmentUpdateProductBinding
import com.example.reliatest.param.UpdateProductParam
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateProductFragment : BaseFragment<FragmentUpdateProductBinding>(), View.OnClickListener {

    override val toolbarLayoutId: Int
        get() = R.layout.layout_toolbar

    override val layoutId: Int
        get() = R.layout.fragment_update_product

    private val viewModel: ProductViewModel by viewModel()

    private val args: UpdateProductFragmentArgs by navArgs()

    override fun toolbarFunc(curActivity: AppCompatActivity?, toolbar: Toolbar?) {
        toolbar?.run {
            findViewById<AppCompatTextView>(R.id.tvTitle)?.text = getString(R.string.update_product)
            findViewById<AppCompatImageButton>(R.id.btnBack)?.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initViews() {
        binding.fragment = this
        binding.product = args.product
    }

    override fun initObservers() {
        viewModel.updateProductLiveData.observe(this) {
            findNavController().popBackStack()
        }
    }

    private fun handleUpdateProduct() {
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
        viewModel.updateProducts(
            UpdateProductParam(
                sku,
                name,
                quantity.toInt(),
                price.toInt(),
                unit,
                status.toInt()
            )
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUpdate -> handleUpdateProduct()
        }
    }
}