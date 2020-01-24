package ru.farkhodkhaknazarov.gitviewer.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.activities.MainActivity

class ManagePermissionsUtility {
    lateinit var list: List<String>

    val PermissionsRequestCode = 100

    fun checkPermissions() {

        list = getPermissionList()

        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            showAlert()
        }
    }

    private fun isPermissionsGranted(): Int {
        var counter = 0
        for (permission in list) {

            counter += ContextCompat.checkSelfPermission(MainActivity.context, permission)

        }
        return counter
    }

    private fun deniedPermission(): List<String> {
        return list.filter {
            ContextCompat.checkSelfPermission(
                MainActivity.activity,
                it
            ) == PackageManager.PERMISSION_DENIED
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(MainActivity.activity)
        builder.setTitle(R.string.permission_header)
        builder.setMessage(R.string.permission_text)
        builder.setPositiveButton("OK", { dialog, which -> requestPermissions() })
        builder.setNeutralButton(R.string.cancel, null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun requestPermissions() {
        val deniedPermission = deniedPermission()
        ActivityCompat.requestPermissions(
            MainActivity.activity as Activity,
            deniedPermission.toTypedArray(),
            PermissionsRequestCode
        )
    }

    fun getPermissionList() = listOf<String>(
//        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}