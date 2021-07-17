package com.freeman.project.sql.dao

import androidx.room.*
import com.freeman.project.sql.table.ZoomInfo

@Dao
interface ZoomInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertZoomInfo(zoomInfo: ZoomInfo)

    @Query("select * from zoom_info")
    fun getZoomInfos():List<ZoomInfo>

    @Query("delete from zoom_info")
    fun clearZoomInfo()
}