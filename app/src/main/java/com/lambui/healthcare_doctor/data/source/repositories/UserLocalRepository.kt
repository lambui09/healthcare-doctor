package com.lambui.healthcare_doctor.data.source.repositories

import android.util.Log
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsKey
import com.lambui.healthcare_doctor.data.model.DoctorModel

interface UserLocalRepository {
    fun saveUserLocal(doctorModel: DoctorModel?)
    fun getUserLocal(): DoctorModel?
    fun saveUserId(id: String)
    fun getUserId(): String?
}

class UserLocalRepositoryImpl(private val sharedPrefsApi: SharedPrefsApi) : UserLocalRepository {
    private var userCache: DoctorModel? = null
    override fun saveUserLocal(doctorModel: DoctorModel?) {
        userCache = doctorModel
        sharedPrefsApi.put(SharedPrefsKey.KEY_USER_LOCAL, userCache)
        Log.d("@@@@", "${userCache.toString()}")
    }

    override fun getUserLocal(): DoctorModel? {
        if (userCache != null) {
            return userCache
        }
        userCache = sharedPrefsApi.get(SharedPrefsKey.KEY_USER_LOCAL, DoctorModel::class.java)
        if (userCache != null) {
            return userCache
        }
        Log.d("@@@@", "${userCache.toString()}")
        return null
    }

    override fun saveUserId(id: String) {
        sharedPrefsApi.put(SharedPrefsKey.KEY_ID_USER, id)
    }

    override fun getUserId(): String? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_ID_USER, String::class.java)
    }

}