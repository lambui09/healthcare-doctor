package com.lambui.healthcare_doctor.data.source.repositories

import com.lambui.healthcare_doctor.data.Token
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi

interface TokenRepository {
  fun getToken(): Token?

  fun saveToken(token: Token)

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
  private var tokenCache: Token? = null
  private var prefixRoleTemp = ""

  override fun getToken(): Token? {
    return null
  }

  override fun saveToken(token: Token) {

  }

  override fun clearToken() {

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
