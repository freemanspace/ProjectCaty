package com.freeman.project.http.request

import okhttp3.Request


/**
 * request base
 */
open class BaseRequest {

    fun createRequestBuilder(url:String):Request.Builder{
        return Request.Builder()
            .addHeader("Authorization", "")
            //.addHeader("Authorization", "9b08ccc19901b99b75c9922b4caba9bf17a9a870cd8c64f2489893ebc45daebc")
            .url(url)
            .get()
    }

}