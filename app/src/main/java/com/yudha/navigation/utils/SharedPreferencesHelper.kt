package com.yudha.navigation.utils

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Created by yudha on 22,August,2019
 */
class SharedPreferencesHelper(context: Context) {
    private val PREF_API_KEY = "Api key"

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key : String){
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey() = prefs.getString(PREF_API_KEY, null)
}