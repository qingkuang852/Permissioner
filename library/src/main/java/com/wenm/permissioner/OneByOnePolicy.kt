package com.wenm.permissioner

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

data class OnePermissionInfo(
    var permission: String,
    var index: Int
)

class OneByOnePolicy(permissions: Array<String>) : PermissionPolicy(permissions){
    private var currentIndex = 0
    private val mapping = mutableListOf<OnePermissionInfo>()
    lateinit var singleLauncher: ActivityResultLauncher<String>

    init {
        for(i in 0..permissions.size){
            mapping.add(OnePermissionInfo(permissions[i], i))
        }
    }

    override fun registerLauncher(activity: FragmentActivity) {
        super.registerLauncher(activity)
        singleLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
           onSingleLauncherCallback(result)
        }
    }

    private fun onSingleLauncherCallback(result: Boolean): Boolean {
        if (result) {
            val item = mapping[currentIndex]
            val isAllGranted = checkPermissionsAllGrantedOrNot(context, permissions)
            grantedCallback?.invoke(item.permission, isAllGranted)

            currentIndex++
            if (currentIndex == mapping.size || isAllGranted) {
                if (!isAllGranted && gotoSetting) {
                    val intent: Intent = goToSetting(context)
                    launcher.launch(intent)
                }
                return true
            }
        }
        return startLaunch()
    }

    private fun startLaunch(): Boolean{
        val item = mapping[currentIndex]
        val isGranted = checkPermissionGrantedOrNot(context, item.permission)
        if (isGranted) {
            val isAllGranted = checkPermissionsAllGrantedOrNot(context, permissions)
            grantedCallback?.invoke(item.permission, isAllGranted)

            currentIndex++
            if (currentIndex == mapping.size || isAllGranted) {
                if (!isAllGranted && gotoSetting) {
                    val intent: Intent = goToSetting(context)
                    launcher.launch(intent)
                }
                return true
            }
            return startLaunch()
        } else {
            singleLauncher.launch(item.permission)
        }
        return false
    }

    override fun launch() {
        startLaunch()
    }

    override fun launch(callback: (permission: String?, allGranted: Boolean) -> Unit) {
        super.launch(callback)
        launch()
    }
}