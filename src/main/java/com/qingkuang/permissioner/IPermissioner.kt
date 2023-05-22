package com.qingkuang.permissioner

import androidx.fragment.app.FragmentActivity

interface IPermissioner {
    fun launch()
    fun launch(callback: (permission: String?, allGranted: Boolean) -> Unit)
    fun registerLauncher(activity: FragmentActivity)
    fun registerGrantedCallback(callback: (permission: String?, allGranted: Boolean) -> Unit)
    fun registerSettingCallback(callback: () -> Unit)
    fun registerRejectCallback(callback: () -> Unit)
}