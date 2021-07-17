package com.freeman.project.sql.table

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.freeman.project.utils.UtilJson.getString
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

/**
 * zoom info data
 */
@Parcelize
@Entity(tableName = "zoom_info")
class ZoomInfo :Parcelable{
    @PrimaryKey(autoGenerate = false)
    var name = ""  //名稱
    var info = ""  //簡介
    var category = ""   //Category
    var pic = ""  //圖片
    var url = ""  //web url


    fun parser(jsonObj: JSONObject) {
        try{
            name = jsonObj.getString("E_Name","")
            info = jsonObj.getString("E_Info","")
            category = jsonObj.getString("E_Category","")
            pic = jsonObj.getString("E_Pic_URL","")
            url = jsonObj.getString("E_URL","")
        } catch (e: Exception){
        }
    }
}
