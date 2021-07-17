package com.freeman.project.http.request

import com.freeman.project.http.URLSetting
import com.freeman.project.utils.UtilEncrypt.urlEncode
import okhttp3.Request
import org.json.JSONObject

/**
 * getConfig 的request
 */
class PlantInfoRequest(var key:String, var offset:Int) : BaseRequest() {

    fun createRequest(): Request{
        var requestBuilder = createRequestBuilder(
            URLSetting.getBaseUrl("f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire&q=${key.urlEncode()}&limit=20&offset=$offset")
        )
        return requestBuilder.build()
    }

}