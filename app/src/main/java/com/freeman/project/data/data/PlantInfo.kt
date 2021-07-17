package com.freeman.project.data.data

import android.os.Parcelable
import com.freeman.project.utils.UtilJson.getString
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
class PlantInfo :Parcelable {
    var name = ""
    var nameLatin = ""
    var alsoKnown = ""
    var picList = ArrayList<String>()
    var location = ""
    var brief = ""
    var future = ""
    var function = ""
    var updateTime = ""

    fun parser(jsonObj:JSONObject){
        name = jsonObj.getString("\uFEFFF_Name_Ch","")
        nameLatin = jsonObj.getString("F_Name_Latin","")
        alsoKnown = jsonObj.getString("F_AlsoKnown","")
        jsonObj.getString("F_Pic01_URL","").apply {
            if(!this.isBlank()){
                picList.add(this)
            }
        }
        jsonObj.getString("F_Pic02_URL","").apply {
            if(!this.isBlank()){
                picList.add(this)
            }
        }
        location = jsonObj.getString("F_Location","")
        brief = jsonObj.getString("F_Brief","")
        future = jsonObj.getString("F_Feature","")
        function = jsonObj.getString("F_Function＆Application","")
        updateTime = jsonObj.getString("F_Update","")
    }
}