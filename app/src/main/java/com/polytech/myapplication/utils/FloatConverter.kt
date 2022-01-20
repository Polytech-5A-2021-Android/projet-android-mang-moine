package com.polytech.myapplication.utils

import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat
import java.util.*

object FloatConverter {
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

}