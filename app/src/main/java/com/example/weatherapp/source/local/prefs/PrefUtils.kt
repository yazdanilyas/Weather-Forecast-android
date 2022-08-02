package com.example.weatherapp.source.local.prefs

import android.content.Context
import android.content.SharedPreferences


class PrefUtils {
    companion object {
        private const val PREF_NAME = "_Pref_"
        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        @JvmStatic
        fun setBoolean(context: Context, key: String?, value: Boolean): Boolean {
            return getPreferences(context).edit().putBoolean(key, value).commit()
        }

        @JvmStatic
        fun getBoolean(context: Context, key: String?): Boolean {
            return getPreferences(context).getBoolean(key, false)
        }

        @JvmStatic
        fun getNotificationSetting(context: Context, key: String?): Boolean {
            return getPreferences(context).getBoolean(key, true)
        }

        @JvmStatic
        fun setString(context: Context, key: String?, value: String?) {
            getPreferences(context).edit().putString(key, value).apply()
        }

        @JvmStatic
        fun clearStringData(context: Context, key: String?) {
            getPreferences(context).edit().putString(key, null).apply()
        }

        @JvmStatic
        fun getString(context: Context, key: String?): String? {
            return getPreferences(context).getString(key, null)
        }

        @JvmStatic
        fun getString(context: Context, key: String?, defaultValue: String?): String? {
            return getPreferences(context).getString(key, defaultValue)
        }



        @JvmStatic
        fun setLong(context: Context, key: String?, value: Long) {
            getPreferences(context).edit().putLong(key, value).apply()
        }

        @JvmStatic
        fun getLong(context: Context, key: String?): Long {
            return getPreferences(context).getLong(key, 0)
        }

        @JvmStatic
        fun setInt(context: Context, key: String?, value: Int) {
            getPreferences(context).edit().putInt(key, value).apply()
        }

        @JvmStatic
        fun getInt(context: Context, key: String?): Int {
            return getPreferences(context).getInt(key, 0)
        }

        @JvmStatic
        fun setFloat(context: Context, key: String?, value: Float) {
            getPreferences(context).edit().putFloat(key, value).apply()
        }

        @JvmStatic
        fun getFloat(context: Context, key: String?): Float {
            return getPreferences(context).getFloat(key, 0f)
        }


        @JvmStatic
        fun removeValue(context: Context, key: String?) {
            getPreferences(context).edit().remove(key).apply()
        }
    }
}