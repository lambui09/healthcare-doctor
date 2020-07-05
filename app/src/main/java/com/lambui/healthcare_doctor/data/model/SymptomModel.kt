package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SymptomModel(
  @Expose
  @SerializedName("_id")
  val id: String? = null,
  @Expose
  @SerializedName("name")
  val nameSymptom: String? = null,
  @Expose
  @SerializedName("createdAt")
  var createdAt: String? = null,
  @Expose
  @SerializedName("updatedAt")
  var updatedAt: String? = null,
  var isSelect: Boolean = false

) : Parcelable