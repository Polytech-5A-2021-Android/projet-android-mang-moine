package com.polytech.myapplication.utils

import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    @JvmStatic
    @InverseMethod("stringToFloat")
    fun floatToString(
        value: Float
    ): String {
        return value.toString()
    }
    @JvmStatic
    fun stringToFloat( value: String
    ): Float {
        return value.toFloat()
    }

    @JvmStatic
    @InverseMethod("stringToLong")
    fun longToString(
        value: Long
    ): String {
        return value.toString()
    }
    @JvmStatic
    fun stringToLong( value: String
    ): Long {
        return value.toLong()
    }


}