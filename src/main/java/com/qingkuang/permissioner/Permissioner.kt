package com.qingkuang.permissioner

import androidx.fragment.app.FragmentActivity

class Permissioner(
    permissions: Array<String>,
    policy: PolicyType = PolicyType.Multiple
) : IPermissioner {
    private val permissionPolicy: PermissionPolicy by lazy {
        if (policy == PolicyType.Multiple) MultiplePolicy(permissions) else OneByOnePolicy(permissions)
    }

    override fun registerGrantedCallback(callback: (permission: String?, allGranted: Boolean) -> Unit) {
        permissionPolicy.registerGrantedCallback(callback)
    }

    override fun registerRejectCallback(callback: () -> Unit) {
        permissionPolicy.registerRejectCallback(callback)
    }

    override fun registerSettingCallback(callback: () -> Unit) {
        permissionPolicy.registerSettingCallback(callback)
    }

    override fun registerLauncher(activity: FragmentActivity) {
        permissionPolicy.registerLauncher(activity)
    }

    override fun launch() {
        permissionPolicy.launch()
    }

    override fun launch(callback: (permission: String?, allGranted: Boolean) -> Unit) {
        permissionPolicy.launch(callback)
    }
}