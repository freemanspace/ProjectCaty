package com.freeman.project.fragment.zoomlist

import com.freeman.project.data.json.ZoomInfoJson
import com.freeman.project.fragment.BaseViewModel
import com.freeman.project.http.DemoApiManager
import com.freeman.project.http.OnApiFinish
import com.freeman.project.http.SingleLiveEvent
import com.freeman.project.http.request.ZoomInfoRequest
import com.freeman.project.sql.SqlManager
import com.freeman.project.sql.table.CacheVersion
import com.freeman.project.sql.table.ZoomInfo
import com.freeman.project.utils.UtilDate.toString
import com.freeman.project.utils.UtilList.toArrayList
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class ZoomListFragmentModel : BaseViewModel() {

    /**
     *  0->start
     *  1->checkPermission pass
     *  2->checkPermission unpass
     */
    val zoomInfos = SingleLiveEvent<ArrayList<ZoomInfo>>()

    fun getZoomInfoJson(){

        val zoomInfoDao = SqlManager.dataBase.zoomInfoDao()
        val tmpZoominfos = zoomInfoDao.getZoomInfos()
        if(tmpZoominfos.isNotEmpty()){
            zoomInfos.postValue(tmpZoominfos.toArrayList())
        } else{
            startLoading()
            jobs.add(
                DemoApiManager.getZoomInfo(ZoomInfoRequest(),object :OnApiFinish<ZoomInfoJson>{
                    override fun onApiFinish(msg: Int, dataObj: ZoomInfoJson) {
                        stopLoading()
                        if(dataObj.isSuccess()){
                            MainScope().launch {
                                val deferred = async(Dispatchers.IO) {
                                    //塞入cache version
                                    SqlManager.dataBase.cacheVersionDao().apply {
                                        insertCacheVersion(CacheVersion(CacheVersion.zoomInfo,Calendar.getInstance().toString(2)))
                                    }
                                    //zoom info cache in db
                                    for (zoomInfo in dataObj.zoomInfoList){
                                        zoomInfoDao.insertZoomInfo(zoomInfo)
                                    }
                                }
                                deferred.await()
                                zoomInfos.postValue(dataObj.zoomInfoList)
                            }
                        } else {
                            showApiErrMsg(dataObj)
                        }
                    }
                })
            )

        }
    }

}