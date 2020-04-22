package com.lambui.healthcare_doctor.data.source.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse<T> (
  @Expose
  @SerializedName("status_code")
  var status: Int = 0,
  @Expose
  @SerializedName("total")
  var total: Int = 0,
  @Expose
  @SerializedName("data")
  var data: T? = null,
  @Expose
  @SerializedName("success")
  val success: Boolean = false,
  @Expose
  @SerializedName("last_page")
  var lastPage: Int = 0,
  @Expose
  @SerializedName("current_page")
  var currentPage: Int = 0
)