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
        editor.putString(key, gson.toJson(obj))
        editor.apply()
    }

    fun <T> getObject(key: String, mClass: Class<T>): T {
        return gson.fromJson(pref.getString(key, ""), mClass)
    }

    fun getPrefEditor(): SharedPreferences.Editor {
        return pref.edit()
    }
}