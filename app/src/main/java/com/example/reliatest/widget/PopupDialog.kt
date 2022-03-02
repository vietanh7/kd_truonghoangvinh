package com.example.reliatest.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.reliatest.R
import com.example.reliatest.constant.Constant.ARGUMENT_KEY_POPUP
import com.example.reliatest.vo.PopUp

class PopupDialog : DialogFragment() {

    private lateinit var popup: PopUp
    private var binding: ViewDataBinding? = null

    companion object {
        fun newInstance(popup: PopUp?): PopupDialog =
            PopupDialog().apply {
                arguments = bundleOf(ARGUMENT_KEY_POPUP to popup)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        popup = arguments?.getParcelable(ARGUMENT_KEY_POPUP) ?: PopUp()
        if (popup.popupId == 0)
            return layoutInflater.inflate(R.layout.layout_loading, container, false)

        binding = DataBindingUtil.inflate(inflater, popup.popupId, container, false)
        return if (binding?.root != null)
            binding?.root
        else
            layoutInflater.inflate(popup.popupId, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popup.callback?.invoke(binding, view, dialog)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(popup.isCancelable)

        if (popup.isMatchParent)
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        else
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }
}