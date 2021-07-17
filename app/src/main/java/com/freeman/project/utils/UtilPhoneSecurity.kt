package com.freeman.project.utils

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


/**
 * 偵測root 模擬器等等資訊
 */
object UtilPhoneSecurity {

    /**
     * 使否有root
     */
    @Synchronized
    fun checkRoot():Boolean{
        return (checkRootMethod1() || checkRootMethod2() || checkRootMethod3())
    }

    /**
     * 是否是模擬器
     */
    @Synchronized
    fun checkEmulator():Boolean{
        return checkEmulatorMethod1()
    }

    /**
     * 設定可否做螢幕截圖
     */
    @Synchronized
    fun setScreenShot(activity:Activity,canScreenShot:Boolean){
        if(canScreenShot) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else{
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    //---------------------root check-------------------------------------------
    private val rootPaths = arrayOf(
            "/sbin/",
            "/system/bin/",
            "/system/xbin/",
            "/data/local/xbin/",
            "/data/local/bin/",
            "/system/sd/xbin/",
            "/system/bin/failsafe/",
            "/data/local/"
    )

    private fun checkRootMethod1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        for (path in rootPaths) {
            val file = File(String.format("%s/su", path))
            if (file.exists())
                return true
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val `in` = BufferedReader(InputStreamReader(process!!.inputStream))
            `in`.readLine() != null
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }
    //---------------------root check-------------------------------------------

    //---------------------Emulator check---------------------------------------
    private fun checkEmulatorMethod1():Boolean{
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
    }


}