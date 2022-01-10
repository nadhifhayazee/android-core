package com.nadhif.hayazee.android_core.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson

class SharedPreferenceUtil(context: Context) {

    companion object {
        const val FILE_NAME = "MY_PREF"
    }

    var pref: SharedPreferences
    private val gson = Gson()

    init {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        pref = EncryptedSharedPreferences.create(
            FILE_NAME,
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun <T> saveObject(key: String, obj: T) {
        val editor = pref.edit()

        when (obj) {
            is String -> {
                editor.putString(key, obj)
            }
            is Int -> {
                editor.putInt(key, obj)
            }
            is Boolean -> {
                editor.putBoolean(key, obj)
            }
            is Float -> {
                editor.putFloat(key, obj)
            }
            is Long -> {
                editor.putLong(key, obj)
            }
            else -> {
                editor.putString(key, gson.toJson(obj))
            }
        }

        editor.apply()
    }

    fun <T> getObject(key: String, mClass: Class<T>): T? {
        return when (mClass) {
            String::class.java -> {
               pref.getString(key,"") as T
            }
            Int::class.java -> {
               pref.getInt(key,0) as T
            }
            Boolean::class.java -> {
                pref.getBoolean(key,false) as T
            }
            Float::class.java -> {
                pref.getFloat(key,0f) as T
            }
            Long::class.java -> {
                pref.getLong(key, 0L) as T
            }
            else -> {
                gson.fromJson(pref.getString(key, ""), mClass)
            }
        }
    }
}