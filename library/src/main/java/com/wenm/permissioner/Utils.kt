package com.wenm.permissioner

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat

fun checkPermissionsAllGrantedOrNot(context: Context, permissions: Array<String>): Boolean {
    var arePermissionsAllGranted = true
    for (permission in permissions) {
        val granted = checkPermissionGrantedOrNot(context, permission)

        if (!granted) {
            arePermissionsAllGranted = false
        }
    }
    return arePermissionsAllGranted
}

fun checkPermissionGrantedOrNot(context: Context, permission: String): Boolean{
    return ActivityCompat.checkSelfPermission(
        context,
        permission
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
}

private const val MANUFACTURER_HUAWEI = "HUAWEI"
private const val MANUFACTURER_MEIZU = "Meizu"
private const val MANUFACTURER_XIAOMI = "Xiaomi"
private const val MANUFACTURER_SONY = "Sony"
private const val MANUFACTURER_OPPO = "OPPO"
private const val MANUFACTURER_LG = "LG"
private const val MANUFACTURER_VIVO = "vivo"

fun goToSetting(context: Context): Intent {
    return when (Build.MANUFACTURER) {
        MANUFACTURER_HUAWEI -> Huawei(context)
        MANUFACTURER_MEIZU -> Meizu(context)
        MANUFACTURER_XIAOMI -> Xiaomi(context)
        MANUFACTURER_SONY -> Sony(context)
        MANUFACTURER_OPPO -> OPPO(context)
        MANUFACTURER_VIVO -> VIVO(context)
        MANUFACTURER_LG -> LG(context)
        else -> {
            ApplicationInfo(context)
        }
    }
}

fun Huawei(context: Context): Intent {
    val intent = Intent()
    try {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.getApplicationInfo().packageName)
        val comp = ComponentName(
            "com.huawei.systemmanager",
            "com.huawei.permissionmanager.ui.MainActivity"
        )
        intent.setComponent(comp)
    } catch (e: Exception) {
        e.printStackTrace()
        return goIntentSetting(context)
    }
    return intent
}

fun Meizu(context: Context): Intent {
    val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
    try {
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.putExtra("packageName", context.getPackageName())
    } catch (e: Exception) {
        e.printStackTrace()
        return goIntentSetting(context)
    }
    return intent
}

fun Xiaomi(context: Context): Intent {
    val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
    try {
        intent.putExtra("extra_pkgname", context.getPackageName())
        val componentName = ComponentName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )
        intent.setComponent(componentName)
    } catch (e: Exception) {
        e.printStackTrace()
        return goIntentSetting(context)
    }
    return intent
}

fun Sony(context: Context): Intent {
    val intent = Intent()
    try {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.getPackageName())
        val comp = ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity")
        intent.setComponent(comp)
    } catch (e: Exception) {
        e.printStackTrace()
        return goIntentSetting(context)
    }
    return intent
}

fun OPPO(context: Context): Intent {
    val intent = Intent()
    try {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.getPackageName())
        //        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        val comp = ComponentName(
            "com.coloros.securitypermission",
            "com.coloros.securitypermission.permission.PermissionAppAllPermissionActivity"
        ) //R11t 7.1.1 os-v3.2
        intent.setComponent(comp)
    } catch (e: Exception) {
        e.printStackTrace()
        return goIntentSetting(context)
    }
    return intent
}

fun VIVO(context: Context): Intent {
    val localIntent: Intent
    if (Build.MODEL.contains("Y85") && !Build.MODEL.contains("Y85A") || Build.MODEL.contains("vivo Y53L")) {
        localIntent = Intent()
        localIntent.setClassName(
            "com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.PurviewTabActivity"
        )
        localIntent.putExtra("packagename", context.getPackageName())
        localIntent.putExtra("tabId", "1")
    } else {
        localIntent = Intent()
        localIntent.setClassName(
            "com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"
        )
        localIntent.setAction("secure.intent.action.softPermissionDetail")
        localIntent.putExtra("packagename", context.getPackageName())
    }
    return localIntent
}

fun LG(context: Context): Intent {
    val intent = Intent("android.intent.action.MAIN")
    try {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.getPackageName())
        val comp = ComponentName(
            "com.android.settings",
            "com.android.settings.Settings\$AccessLockSummaryActivity"
        )
        intent.setComponent(comp)
    } catch (e: Exception) {
        e.printStackTrace()
        return goIntentSetting(context)
    }
    return intent
}

fun _360(context: Context) {
    val intent = Intent("android.intent.action.MAIN")
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtra("packageName", context.getPackageName())
    val comp = ComponentName(
        "com.qihoo360.mobilesafe",
        "com.qihoo360.mobilesafe.ui.index.AppEnterActivity"
    )
    intent.setComponent(comp)
}

fun ApplicationInfo(context: Context): Intent {
    val localIntent = Intent()
    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null))
    return localIntent
}

private fun goIntentSetting(context: Context): Intent {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", context.getPackageName(), null)
    intent.setData(uri)
    return intent
}