package com.freeman.project.sql

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freeman.project.sql.dao.CacheVersionDao
import com.freeman.project.sql.dao.ZoomInfoDao
import com.freeman.project.sql.table.CacheVersion
import com.freeman.project.sql.table.ZoomInfo


@Database(
        entities = [CacheVersion::class,ZoomInfo::class],//PayCreditCard::class],
        version = 1
)
abstract class AppDatabase: RoomDatabase() {
    companion object{
        val Name = "temp.db"

        //--------------table update-------------------------------------
        //版本更新1->2，會執行的script
        /*
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(@NonNull database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE cars ADD COLUMN html_url TEXT")
            }
        }*/
    }

    //dao define-----------------------------------------
    abstract fun cacheVersionDao():CacheVersionDao
    abstract fun zoomInfoDao():ZoomInfoDao
    //abstract fun zoomInfoDao(): ZoomInfoDao
}
