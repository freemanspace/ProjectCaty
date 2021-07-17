package com.freeman.project.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * cmd util
 */
object UtilCmd {

    /**
     * cmd
     * execCmd("ping -c 1 -w 1 google.com", false)
     */
    fun execCmd(cmd: String, isSu: Boolean):String{
        try{
            val p = if(isSu){
                Runtime.getRuntime().exec(arrayOf("su", "-c", cmd))
            } else{
                Runtime.getRuntime().exec(cmd)
            }
            val stdInput = BufferedReader(InputStreamReader(p.inputStream))
            val resBuilder = StringBuilder()
            stdInput.forEachLine {
                resBuilder.append("${it}\n")
            }
            p.destroy()
            return resBuilder.toString()
        } catch (e: Exception){
            return ""
        }
    }
}