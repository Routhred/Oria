package com.example.oria.backend.data.storage.dataStore

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Class to store the preferences (persistent way)
 *
 * @constructor
 *
 * @param context
 */
class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_login", Context.MODE_PRIVATE)

    /**
     * Save current data
     *
     * @param key
     * @param value
     */
    fun saveData(key: PreferencesKey, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key.toString(), value)
        editor.apply()
    }

    /**
     * Get data from current database link to the given key
     *
     * @param key
     * @param defaultValue
     * @return
     */
    fun getData(key: PreferencesKey, defaultValue: String): String {
        return sharedPreferences.getString(key.toString(), defaultValue) ?: defaultValue
    }

    companion object {
        @Volatile
        private var Instance: PreferencesManager? = null

        fun getManager(context: Context): PreferencesManager {
            Log.d("PreferencesManager", "Get Database")
            return Instance ?: synchronized(this) {
                Instance ?: PreferencesManager(context).also { Instance = it }

            }
        }


    }
}