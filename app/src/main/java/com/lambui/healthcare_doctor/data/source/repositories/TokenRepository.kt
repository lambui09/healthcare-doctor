package com.lambui.healthcare_doctor.data.source.repositories

import com.lambui.healthcare_doctor.data.Token
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsKey
import com.lambui.healthcare_doctor.utils.extension.notNull

interface TokenRepository {
    fun getToken(): String?

    fun saveToken(token: String)

    fun clearToken()

    fun setPrefixRole(prefixRole: String)

    fun getPrefixRole(): String

    fun setPrefixRoleTemp(prefixRoleTemp: String)

    fun isHasLogIn(): Boolean

    fun isVerifyEmail(): Boolean

    fun isVerifyPhone(): Boolean

    fun doLogout()
}

class TokenRepositoryImpl(private val sharedPrefsApi: SharedPrefsApi) : TokenRepository {
    private var tokenCache: String? = null
    private var prefixRoleTemp = ""

    override fun getToken(): String? {
        tokenCache.notNull {
            return it
        }

        val token = sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, String::class.java)
        token.notNull {
            tokenCache = it
            return it
        }

        return null
    }

    override fun saveToken(token: String) {
        tokenCache = token
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, tokenCache)
    }

    override fun clearToken() {
        tokenCache = null
        sharedPrefsApi.clear()
    }

    override fun setPrefixRole(prefixRole: String) {

    }

    override fun getPrefixRole(): String {
        return ""
    }

    override fun setPrefixRoleTemp(prefixRoleTemp: String) {

    }

    override fun isHasLogIn(): Boolean {
        return true
    }

    override fun isVerifyEmail(): Boolean {
        return true
    }

    override fun isVerifyPhone(): Boolean {
        return true
    }

    override fun doLogout() {

    }

}
