package com.example.project01_danp.firebase.utils

import android.util.Log
import java.lang.reflect.Field

object GenericMapper {
    private val TAG = GenericMapper::class.java.simpleName
    @Throws(IllegalAccessException::class)
    fun <O : Any> convertMapToObject(`object`: O): Map<String, Any> {
        val objectMap: MutableMap<String, Any> = HashMap()
        val allFields: Array<Field> = `object`.javaClass.declaredFields
        for (field in allFields) {
            field.isAccessible = true
            objectMap[field.name] = field[`object`]!!
        }
        Log.e(TAG, objectMap.toString())
        return objectMap
    }
}