package com.freeman.project.http.request

import com.freeman.project.http.URLSetting
import okhttp3.Request
import org.json.JSONObject

/**
 * getConfig 的request
 */
class ZoomInfoRequest() : BaseRequest() {

    fun createRequest(): Request{
        var requestBuilder = createRequestBuilder(
            URLSetting.getBaseUrl("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire&limit=200&offset=0")
        )
        return requestBuilder.build()
    }

}