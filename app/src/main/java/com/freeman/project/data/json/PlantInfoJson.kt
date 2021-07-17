package com.freeman.project.data.json

import android.os.Parcelable
import com.freeman.project.data.OnJsonParser
import com.freeman.project.data.data.PlantInfo
import com.freeman.project.utils.UtilJson.getJsonArray
import com.freeman.project.utils.UtilJson.getJsonObject
import com.freeman.project.utils.UtilJson.toJsonObject
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
class PlantInfoJson : BaseJson(), OnJsonParser,Parcelable {

    var plantInfoList = ArrayList<PlantInfo>()

    override fun parser(str: String?) {
        try{
            plantInfoList.clear()

            val jsonData = str.toJsonObject()
            baseJsonParser(jsonData)

            if(isSuccess()){
                val result = jsonData.getJsonObject("result")
                val results = result.getJsonArray("results")

                for (i in 0 until results.length()){
                    plantInfoList.add(PlantInfo().apply {
                        parser(results.getJSONObject(i))
                    })
                }
            }
        } catch (e:Exception){
            status = -1
        }
    }

}