package com.freeman.project.http

import androidx.lifecycle.LifecycleOwner
import com.freeman.project.data.json.PlantInfoJson
import com.freeman.project.data.json.ZoomInfoJson
import com.freeman.project.http.request.PlantInfoRequest
import com.freeman.project.http.request.ZoomInfoRequest
import kotlinx.coroutines.*

/**
 * 使用Coroutines(協程)呼叫api
 */
object DemoApiManager :BaseApiManager() {

    /**
     * 取得動物園資訊
     */
    fun getZoomInfo(zoomInfoRequest: ZoomInfoRequest, onApiFinish: OnApiFinish<ZoomInfoJson>):Job{
        val zoomInfoJson = ZoomInfoJson()
        return MainScope().launch {
            try {
                val response = async(Dispatchers.IO) {
                    HttpHelper.httpRequest(zoomInfoRequest.createRequest())
                }
                zoomInfoJson.parser(response.await().response)
                bindApiData(0, zoomInfoJson, onApiFinish)
            } catch (e:java.lang.Exception){
                bindApiData(0, zoomInfoJson, onApiFinish)
            }
        }
    }

    /**
     * 取得植物資訊
     */
    fun getPlantInfo(plantInfoRequest: PlantInfoRequest, onApiFinish: OnApiFinish<PlantInfoJson>):Job{
        val plantInfoJson = PlantInfoJson()
        return MainScope().launch {
            try {
                val response = async(Dispatchers.IO) {
                    HttpHelper.httpRequest(plantInfoRequest.createRequest())
                }
                plantInfoJson.parser(response.await().response)
                bindApiData(0, plantInfoJson, onApiFinish)
            } catch (e:java.lang.Exception){
                bindApiData(0, plantInfoJson, onApiFinish)
            }
        }
    }

    //width life data
    fun getZoomInfo(zoomInfoRequest: ZoomInfoRequest, lifecycleOwner: LifecycleOwner, onApiFinish: OnApiFinish<ZoomInfoJson>):Job{
        val zoomInfoJson = ZoomInfoJson()
        return MainScope().launch {
            try {
                val response = async(Dispatchers.IO) {
                    HttpHelper.httpRequest(zoomInfoRequest.createRequest())
                }
                zoomInfoJson.parser(response.await().response)
                bindMyApiData(lifecycleOwner, 0, zoomInfoJson, onApiFinish)
            } catch (e:java.lang.Exception){
                bindMyApiData(lifecycleOwner, 0, zoomInfoJson, onApiFinish)
            }
        }
    }

    //width life data
    fun getPlantInfo(plantInfoRequest: PlantInfoRequest, lifecycleOwner: LifecycleOwner, onApiFinish: OnApiFinish<PlantInfoJson>):Job{
        val plantInfoJson = PlantInfoJson()
        return MainScope().launch {
            try {
                val response = async(Dispatchers.IO) {
                    HttpHelper.httpRequest(plantInfoRequest.createRequest())
                }
                plantInfoJson.parser(response.await().response)
                bindMyApiData(lifecycleOwner, 0, plantInfoJson, onApiFinish)
            } catch (e:java.lang.Exception){
                bindMyApiData(lifecycleOwner, 0, plantInfoJson, onApiFinish)
            }
        }
    }
}