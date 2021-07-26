package com.freeman.project.fragment.zoom

import com.freeman.project.data.data.PlantInfo
import com.freeman.project.data.json.PlantInfoJson
import com.freeman.project.fragment.BaseViewModel
import com.freeman.project.http.DemoApiManager
import com.freeman.project.http.OnApiFinish
import com.freeman.project.http.SingleLiveEvent
import com.freeman.project.http.request.PlantInfoRequest
import com.freeman.project.sql.table.ZoomInfo
import kotlinx.coroutines.*
import okhttp3.internal.notify
import kotlin.collections.ArrayList

class ZoomFragmentModel : BaseViewModel() {

    lateinit var zoomInfo:ZoomInfo
    val plantInfos = SingleLiveEvent<ArrayList<PlantInfo>>()
    var canLoadMore = true  //是否可以取得更多資料

    fun getPlantInfos(){
        if(canLoadMore) {
            startLoading()
            if (plantInfos.value != null) {
                DemoApiManager.getPlantInfo(
                    PlantInfoRequest(
                        zoomInfo.name,
                        plantInfos.value!!.size
                    ), onApiFinish
                ).let {
                    jobs.add(it)
                }
            } else {
                DemoApiManager.getPlantInfo(
                    PlantInfoRequest(zoomInfo.name, 0),
                    onApiFinish
                ).let {
                    jobs.add(it)
                }
            }
        }
    }

    private val onApiFinish = object :OnApiFinish<PlantInfoJson>{
        override fun onApiFinish(msg: Int, dataObj: PlantInfoJson) {
            stopLoading()
            if (dataObj.isSuccess()) {
                if(dataObj.plantInfoList.size<20){
                    canLoadMore = false
                }
                if(plantInfos.value==null){
                    plantInfos.postValue(dataObj.plantInfoList)
                } else {
                    plantInfos.value?.addAll(dataObj.plantInfoList)
                    plantInfos.notifyObserver()
                }
            } else {
                showApiErrMsg(dataObj)
            }
        }
    }

}