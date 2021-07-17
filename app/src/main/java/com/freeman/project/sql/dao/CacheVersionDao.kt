package com.freeman.project.sql.dao

import androidx.room.*
import com.freeman.project.sql.table.CacheVersion

@Dao
interface CacheVersionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheVersion(cacheVersion: CacheVersion)

    @Query("select * from cache_version")
    fun getZoomInfos():List<CacheVersion>

    @Query("select * from cache_version where id = :id")
    fun getZoomInfo(id:String):CacheVersion?

    @Query("delete from cache_version")
    fun clearZoomInfo()
}