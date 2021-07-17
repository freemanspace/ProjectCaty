package com.freeman.project.utils

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Exception

object UtilJson {

    /**
     * String convert to jsonarray
     */
    fun String?.toJsonArray():JSONArray?{
        return try{
            val jsonParser = JSONTokener(this)
            jsonParser.nextValue() as JSONArray
        } catch (e:Exception){
            e.stackTrace
            null
        }
    }

    /**
     * String convert to jsonobject
     */
    fun String?.toJsonObject():JSONObject?{
        return try{
            val jsonParser = JSONTokener(this)
            jsonParser.nextValue() as JSONObject
        } catch (e:Exception){
            e.stackTrace
            null
        }
    }

    /**
     * json object 取得json object
     */
    fun JSONObject?.getJsonObject(key:String):JSONObject{
        var jsonObject:JSONObject? = null
        try{
            if(!this?.isNull(key)!!){
                jsonObject = this.getJSONObject(key)
            }
        } catch (e:Exception){
            e.stackTrace
        }
        return jsonObject ?: JSONObject()
    }

    /**
     * json object 取得json array
     */
    fun JSONObject?.getJsonArray(key:String):JSONArray{
        var jsonArray:JSONArray? = null
        try{
            if(!this?.isNull(key)!!){
                jsonArray = this.getJSONArray(key)
            }
        } catch (e:Exception){
            e.stackTrace
        }
        return jsonArray ?: JSONArray()
    }

    /**
     * json object取得string
     */
    fun JSONObject?.getString(key:String,def:String):String{
        return try{
            if(!this?.isNull(key)!!){
                this.getString(key)
            } else {
                def
            }
        } catch (e:Exception){
            e.stackTrace
            def
        }
    }

    /**
     * json object取得int
     */
    fun JSONObject?.getInt(key:String,def:Int):Int{
        return try{
            if(!this?.isNull(key)!!){
                this.getInt(key)
            } else{
                def
            }
        } catch (e:Exception){
            e.stackTrace
            def
        }
    }

    /**
     * json object取得long
     */
    fun JSONObject?.getLong(key:String,def:Long):Long{
        return try{
            if(!this?.isNull(key)!!){
                this.getLong(key)
            } else{
                def
            }
        } catch (e:Exception){
            e.stackTrace
            def
        }
    }

    /**
     * json object取得double
     */
    fun JSONObject?.getDouble(key:String,def:Double):Double{
        return try{
            if(!this?.isNull(key)!!){
                this.getDouble(key)
            } else{
                def
            }
        } catch (e:Exception){
            e.stackTrace
            def
        }
    }

    /**
     * json object取得boolean
     */
    fun JSONObject?.getBool(key:String,def:Boolean):Boolean{
        return try{
            if(!this?.isNull(key)!!){
                this.getBoolean(key)
            } else {
                def
            }
        } catch (e:Exception){
            e.stackTrace
            def
        }
    }

}