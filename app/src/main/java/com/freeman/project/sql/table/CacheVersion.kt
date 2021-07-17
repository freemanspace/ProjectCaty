package com.freeman.project.sql.table

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * cache version
 */
@Parcelize
@Entity(tableName = "cache_version")
class CacheVersion() :Parcelable{
    companion object{
        val zoomInfo = "zoomInfo"    //zoom info
    }

    constructor(strID:String,strVersion:String) : this() {
        id = strID
        version = strVersion
    }

    @PrimaryKey(autoGenerate = false)
    var id: String = "" //id pkey
    var version:String = ""    //版本
}
