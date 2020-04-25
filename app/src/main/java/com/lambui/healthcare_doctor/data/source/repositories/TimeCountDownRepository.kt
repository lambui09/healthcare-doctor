package com.lambui.healthcare_doctor.data.source.repositories

import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsKey
import com.lambui.healthcare_doctor.utils.extension.notNull

interface TimeCountDownRepository {
    fun getPreviousTime() : Long?
    fun saveTimeCurrent()
    fun clearTime()
}
class TimeCountDownImpl(val sharedPrefsApi: SharedPrefsApi): TimeCountDownRepository{
    private var timeCache : Long? = null
    override fun getPreviousTime(): Long? {
        val previousTimeStart = sharedPrefsApi.get(SharedPrefsKey.KEY_START_COUNT_DOWN, Long::class.java)
        previousTimeStart.notNull {
            return it
        }
        return null
    }

    override fun saveTimeCurrent() {
        timeCache = System.currentTimeMillis().div(1000L)
        sharedPrefsApi.put(SharedPrefsKey.KEY_START_COUNT_DOWN, timeCache)
    }

    override fun clearTime() {
        timeCache = 0
        sharedPrefsApi.put(SharedPrefsKey.KEY_START_COUNT_DOWN, timeCache)
    }
}