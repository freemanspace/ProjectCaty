package com.freeman.project.sql

import android.content.Context
import androidx.room.Room


object SqlManager {

    lateinit var dataBase: AppDatabase

    @Synchronized
    fun init(context: Context):Boolean{
        dataBase = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.Name)
            .allowMainThreadQueries()
            //.addMigrations(AppDatabase.MIGRATION_1_2)//版本更新執行script
            .fallbackToDestructiveMigration()//版本更新直接移除table重建
            .build()
        return true
    }
}