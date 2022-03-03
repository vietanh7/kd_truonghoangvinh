package com.example.reliatest.utils

import androidx.databinding.ViewDataBinding
import com.example.reliatest.R
import com.example.reliatest.databinding.LayoutPopupBinding
import com.example.reliatest.listener.PopUpListener
import com.example.reliatest.vo.PopUp

object PopupUtil {
    fun showLoading() {
        AppEvent.notifyShowPopUp()
    }

    fun hideAllPopup() {
        AppEvent.notifyClosePopUp()
    }

    fun showPopupError(msg: String) {
        AppEvent.notifyShowPopUp(
            PopUp(
                R.layout.layout_popup,
                callback = { binding: ViewDataBinding?, _, _ ->
                    (binding as? LayoutPopupBinding)?.apply {
                        title = "Error"
                        message = msg
                        center = "Close"
                        clickListener = PopUpListener(
                            clickCenterListener = { AppEvent.notifyClosePopUp() }
                        )
                    }
                }
            )
        )
    }

    fun showPopupConfirm(msg: String, onConfirm: () -> Unit) {
        AppEvent.notifyShowPopUp(
            PopUp(
                R.layout.layout_popup,
                callback = { binding: ViewDataBinding?, _, _ ->
                    (binding as? LayoutPopupBinding)?.apply {
                        message = msg
                        left = "Close"
                        right = "Confirm"
                        clickListener = PopUpListener(
                            clickLeftListener = { AppEvent.notifyClosePopUp() },
                            clickRightListener = {
                                AppEvent.notifyClosePopUp()
                                onConfirm.invoke()
                            }
                        )
                    }
                }
            )
        )
    }
}