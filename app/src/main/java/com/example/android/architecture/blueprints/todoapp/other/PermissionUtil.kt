package com.example.android.architecture.blueprints.todoapp.other

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object PermissionUtil {

    fun hasMessagesPermission(context: Context) = EasyPermissions.hasPermissions(
        context,
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun returnPermissionsArray() = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
}
