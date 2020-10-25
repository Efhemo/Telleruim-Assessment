package com.efhem.farmapp.util

object Utils {

    fun removeBackSlash(url: String): String{
        return url.replace("\\","")
    }

}