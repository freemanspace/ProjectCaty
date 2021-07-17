package com.freeman.project.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri

/**
 * 使用手機功能，如打電話等等
 */
object UtilPhone {

    /**
     * 打電話
     */
    fun callPhone(activity: Activity,phone:String){
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phone}")
        }.let {
            activity.startActivity(it)
        }
    }

    /**
     * 開瀏覽器
     */
    fun openBrowser(activity: Activity,url:String){
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }.let {
            activity.startActivity(it)
        }
    }

}