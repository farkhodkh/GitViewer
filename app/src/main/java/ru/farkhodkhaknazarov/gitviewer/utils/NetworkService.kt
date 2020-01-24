package ru.farkhodkhaknazarov.gitviewer.utils

import android.content.Context
import android.net.ConnectivityManager
import ru.farkhodkhaknazarov.gitviewer.activities.MainActivity

class NetworkService {
    companion object{
        fun checkNetworkState(): Boolean {
            val cm: ConnectivityManager = MainActivity.Companion.context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

            val wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

            if (wifiInfo != null && wifiInfo.isConnected) {
                return true
            }

            val gsmInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

            if (gsmInfo != null && gsmInfo.isConnected) {
                return true
            }

            val activeNetworkInfo = cm.activeNetworkInfo

            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
            return false
        }
    }
}