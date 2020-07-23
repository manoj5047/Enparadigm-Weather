package io.hustler.enparadignwaether.utils

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import io.hustler.enparadignwaether.BuildConfig



object ActivityUtils {
    fun isAppRunning(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInformations = activityManager.runningAppProcesses
        if (processInformations != null) {
            for (processInfo in processInformations) {
                if (processInfo.processName == BuildConfig.APPLICATION_ID) {
                    return true
                }
            }
        }
        return false
    }

    fun isAppInBackground():Boolean
    {
        val myProcess = RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(myProcess)
        return myProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
    }
}