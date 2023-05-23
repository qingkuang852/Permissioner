package com.wenm.permissioner

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.wenm.permissioner.IPermissioner

enum class PolicyType {
    Multiple, OneByOne
}

abstract class PermissionPolicy(var permissions: Array<String>): IPermissioner {
    lateinit var context: Context
    var grantedCallback: ((permission: String?, allGranted: Boolean) -> Unit)? = null
    var rejectCallback: (() -> Unit)? = null
    var settingCallback: (() -> Unit)? = null
    lateinit var resultLauncher: ActivityResultLauncher<Array<String>>
    lateinit var launcher: ActivityResultLauncher<Intent>

    var repeatCount = 3
    var gotoSetting = false

    override fun registerGrantedCallback(callback: (permission: String?, allGranted: Boolean) -> Unit) {
        this.grantedCallback = callback
    }

    override fun registerRejectCallback(callback: () -> Unit) {
        this.rejectCallback = callback
    }

    override fun registerSettingCallback(callback: () -> Unit) {
        this.settingCallback = callback
    }

    override fun registerLauncher(activity: FragmentActivity) {
        context = activity.applicationContext
        if (gotoSetting) {
            launcher = activity.registerForActivityResult<Intent, ActivityResult>(
                ActivityResultContracts.StartActivityForResult()
            ) { settingCallback?.invoke() }
        }
    }

    override fun launch(callback: (permission: String?, allGranted: Boolean) -> Unit) {
        this.grantedCallback = callback
    }
}