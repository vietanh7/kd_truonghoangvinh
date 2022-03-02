package com.example.reliatest.listener

class BottomSheetListener(
    val clickAction1Listener: () -> Unit = {},
    val clickAction2Listener: () -> Unit = {},
    val clickAction3Listener: () -> Unit = {},
    val clickAction4Listener: () -> Unit = {},
    val clickAction5Listener: () -> Unit = {},
    val clickAction6Listener: () -> Unit = {},
    val clickDismissListener: () -> Unit = {}
){
    fun onClickAction1Listener() = clickAction1Listener()
    fun onClickAction2Listener() = clickAction2Listener()
    fun onClickAction3Listener() = clickAction3Listener()
    fun onClickAction4Listener() = clickAction4Listener()
    fun onClickAction5Listener() = clickAction5Listener()
    fun onClickAction6Listener() = clickAction6Listener()
    fun onClickDismissListener() = clickDismissListener()
}