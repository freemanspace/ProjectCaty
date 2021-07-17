package com.freeman.project.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics

object AppSetting {
    var DeviceWidth:Int=0   //寬
    var DeviceHeight:Int=0  //高
    var ScreenDensity = 1.0f   //手機密度
    var ScreenDPI = 0           //手機DPI

    var DeviceID = "" //手機device id
    var DeviceName = "" //手機device name
    var PACKAGENAME = ""    //package name

    var CAMERAPATH = "" //相簿位置(只可放入相面),用在相機應用
    var IMAGEPATH = "" //圖片存取位置
    var FilePATH = "" //file存取位置


    //手機密度
    @SuppressLint("HardwareIds")
    fun init(activity: Activity){
        val outMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = activity.display
            display?.getRealMetrics(outMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
        }
        DeviceWidth = outMetrics.widthPixels
        DeviceHeight = outMetrics.heightPixels
        ScreenDensity = outMetrics.density
        ScreenDPI = outMetrics.densityDpi


        //android 10以上getSerial不可以使用
        //取得ANDROID_ID在裝置恢復出廠原始設定才會衝新生成
        DeviceID = Settings.Secure.getString(
                activity.contentResolver,
                Settings.Secure.ANDROID_ID
        )
        if(DeviceID.isEmpty() || DeviceID == "9774d56d682e549c"){
            DeviceID = UtilSP.getDevUUID()   //讀取已存在的uuid
            if(DeviceID.isEmpty()){  //產生新的uuid
                DeviceID = UtilRandom.createRandomDevUUID()
            }
        }
        //DeviceID = "070be077d059b00a" //"070be077d059b00a" //"weffwefewwe"
        //DeviceID = "12345rew" //"070be077d059b00a" //"weffwefewwe"

        //device name
        val manufactuer = Build.MANUFACTURER
        val model = Build.MODEL
        DeviceName = if (manufactuer != null) {
            if (model != null && model.startsWith(manufactuer)) {
                model
            } else {
                "$manufactuer $model"
            }
        } else {
            model!!
        }
        PACKAGENAME = activity.packageName

        //檔案位置
        val dirFile = activity.getExternalFilesDir(null) //建立檔案目錄
        FilePATH = dirFile!!.absolutePath+"/"
        if (!dirFile!!.exists()) {
            dirFile.mkdir()
        }

        val dirCamera = activity.getExternalFilesDir("Camera") //建立相片目錄
        CAMERAPATH = dirCamera!!.absolutePath+"/"
        if (!dirCamera!!.exists()) {
            dirCamera.mkdir()
        }

        val dirImg = activity.getExternalFilesDir("Image") //建立相片目錄
        IMAGEPATH = dirImg!!.absolutePath+"/"
        if (!dirImg!!.exists()) {
            dirImg.mkdir()
        }
    }


}