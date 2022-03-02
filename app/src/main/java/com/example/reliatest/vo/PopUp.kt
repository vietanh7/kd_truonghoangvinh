package com.example.reliatest.vo

import android.app.Dialog
import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PopUp(
    var popupId: Int = 0,
    var isBottomSheet: Boolean = false,
    var isCancelable: Boolean = true,
    var isMatchParent: Boolean = true,
    var popupBinding: @RawValue ViewDataBinding? = null,
    var callback: @RawValue ((popupBinding: ViewDataBinding?, view: View?, dialog: Dialog?) -> Unit)? = null
) : Parcelable