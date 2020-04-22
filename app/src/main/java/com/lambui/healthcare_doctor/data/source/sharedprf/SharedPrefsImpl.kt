package com.lambui.healthcare_doctor.data.source.sharedprf

import android.content.SharedPreferences
import android.content.Context
import androidx.core.content.edit
import com.lambui.healthcare_doctor.constant.Constants.EMPTY
import com.lambui.healthcare_doctor.constant.Constants.SHARDED_PRE_BOOLEAN_DEFAULT
import com.lambui.healthcare_doctor.constant.Constants.SHARDED_PRE_FLOAT_DEFAULT
import com.lambui.healthcare_doctor.constant.Constants.SHARDED_PRE_INT_DEFAULT
import com.lambui.healthcare_doctor.constant.Constants.SHARDED_PRE_LONG_DEFAULT
import com.google.gson.Gson

class SharedPrefsImpl(context: Context) : SharedPrefsApi {

  private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
      SharedPrefsKey.PREF_NAME, Context.MODE_PRIVATE)
  override fun <T> get(key: String, clazz: Class<T>): T {
    return when (clazz) {
      String::class.java -> sharedPreferences.getString(key, EMPTY).let { it as T }

      Boolean::class.java -> sharedPreferences.getBoolean(key,
        SHARDED_PRE_BOOLEAN_DEFAULT).let { it as T }

      Float::class.java -> sharedPreferences.getFloat(key,
        SHARDED_PRE_FLOAT_DEFAULT).let { it as T }

      Int::class.java -> sharedPreferences.getInt(key,
       SHARDED_PRE_INT_DEFAULT).let { it as T }

      Long::class.java -> sharedPreferences.getLong(key,
        SHARDED_PRE_LONG_DEFAULT).let { it as T }

      else -> Gson().fromJson(sharedPreferences.getString(key, EMPTY), clazz)
    }
  }

  override fun <T> put(key: String, data: T) {
    sharedPreferences.edit {
      when (data) {
        is String -> putString(key, data)
        is Boolean -> putBoolean(key, data)
        is Float -> putFloat(key, data)
        is Int -> putInt(key, data)
        is Long -> putLong(key, data)
        else -> putString(key, Gson().toJson(data))
      }
    }
  }

  override fun clear() {
    sharedPreferences.edit()?.clear()?.apply()
  }

  override fun clearKey(key: String) {
    sharedPreferences.edit()?.remove(key)?.apply()
  }
}