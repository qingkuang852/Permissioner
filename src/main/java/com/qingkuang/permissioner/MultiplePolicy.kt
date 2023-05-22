package com.qingkuang.permissioner

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

class MultiplePolicy(permissions: Array<String>) : PermissionPolicy(permissions){

    override fun registerLauncher(activity: FragmentActivity) {
        super.registerLauncher(activity)
        resultLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            var hasPermissionReject = false
            val strings: Set<String> = result.keys

            for (key in strings) {
                if (!result[key]!!) {
                    hasPermissionReject = true
                }
            }

            if (hasPermissionReject) {
                if (repeatCount > 0) {
                    launch()
                    repeatCount--
                } else {
                    rejectCallback?.invoke() ?: run {
                        if (gotoSetting) {
                            val intent: Intent = goToSetting(activity)
                            launcher.launch(intent)
                        } else
                            Toast.makeText(
                                context,
                                "Permission is not granted, please handle the reject callback!",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            } else {
                grantedCallback?.invoke(permissions[0], true)
            }
        }
    }

    override fun launch() {
        if (checkPermissionsAllGrantedOrNot(context, permissions)) {
            grantedCallback?.invoke(permissions[0], true)
            return
        }
        resultLauncher.launch(this.permissions)
    }

    override fun launch(callback: (permission: String?, allGranted: Boolean) -> Unit) {
        super.launch(callback)
        launch()
    }
}