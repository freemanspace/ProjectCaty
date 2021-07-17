package com.freeman.project.fragment.plant

import com.freeman.project.data.data.PlantInfo
import com.freeman.project.data.json.PlantInfoJson
import com.freeman.project.fragment.BaseViewModel
import com.freeman.project.http.DemoApiManager
import com.freeman.project.http.OnApiFinish
import com.freeman.project.http.SingleLiveEvent
import com.freeman.project.http.request.PlantInfoRequest
import com.freeman.project.sql.table.ZoomInfo
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class PlantFragmentModel : BaseViewModel() {

    lateinit var plantInfo: PlantInfo

    fun getPlantMsg():String{
        return """
${plantInfo.name}
${plantInfo.nameLatin}
            
別名
${plantInfo.alsoKnown}
            
簡介
${plantInfo.brief}
            
特徵
${plantInfo.future}
            
應用
${plantInfo.function}
            
更新時間
${plantInfo.updateTime}
            
        """.trimIndent()
    }
}