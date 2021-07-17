package com.freeman.project.utils


object UtilList {

    /**
     * 將list轉成arraylist
     */
    fun <T> List<T>.toArrayList(): ArrayList<T>{
        return ArrayList(this)
    }
}