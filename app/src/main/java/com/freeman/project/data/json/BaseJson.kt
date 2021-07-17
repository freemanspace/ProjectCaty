package com.freeman.project.data.json

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
open class BaseJson :Parcelable{

    var status = -1

    fun baseJsonParser(jsonObject: JSONObject?){
        if(jsonObject!=null){
            status = 200
        }
    }

    fun isSuccess():Boolean{
        return status==200
    }
}