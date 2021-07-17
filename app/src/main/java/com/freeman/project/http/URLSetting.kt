package com.freeman.project.http

import com.freeman.project.BuildConfig

object URLSetting {

    /**
     * 取得baseurl
     */
    fun getBaseUrl(route:String):String{
        return "${BuildConfig.APIURL}${route}"
    }

}