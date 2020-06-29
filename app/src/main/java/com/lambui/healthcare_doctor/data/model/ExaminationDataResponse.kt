package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExaminationDataResponse(
  @Expose
  @SerializedName("data")
  var data : List<ExaminationModel>? = null
) : Parcelable