package com.lambui.healthcare_doctor.utils.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson

inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
  if (this != null) f(this)
}

inline fun <T : Any> T?.isNull(f: () -> Unit) {
  if (this == null) f()
}

val <T> List<T>.lastIndex: Int
  get() = size - 1


fun MutableList<Any>.swap(index1: Int, index2: Int) {

  val tmp = this[index1]

  this[index1] = this[index2]

  this[index2] = tmp

}

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(values: List<T>) {
  val value = this.value ?: arrayListOf()
  value.addAll(values)
  this.value = value
}


fun <T> String.convertJsonToObject(classType: Class<T>): T {
  return Gson().fromJson(this, classType)
}

fun Context.copyToClipboard(text: CharSequence) {
  val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
  val clip = ClipData.newPlainText(text, text)
  clipboard?.setPrimaryClip(clip)
}