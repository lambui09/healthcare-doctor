package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppointmentFullModel(
  @Expose
  @SerializedName("_id")
  val id: String? = null,
  @Expose
  @SerializedName("price")
  var price: Int? = null,
  @Expose
  @SerializedName("duration")
  var duration: Int? = null,
  @Expose
  @SerializedName("status")
  var status: String? = null,
  @Expose
  @SerializedName("symptom_list")
  var symptomList: List<SymptomModel>? = null,
  @Expose
  @SerializedName("date")
  var dataStartBook: String? = null,
  @Expose
  @SerializedName("time")
  var timeStartBook: String? = null,
  @Expose
  @SerializedName("doctor_id")
  var doctorId: DoctorModel? = null,
  @Expose
  @SerializedName("patient_id")
  var patientId: PatientModel? = null,
  @Expose
  @SerializedName("time_remainder_send_notification")
  var timeRemainSendNotification: Int? = 0,
  @Expose
  @SerializedName("examination_list")
  var listExamination: List<ExaminationModel>?,
  @Expose
  @SerializedName("createdAt")
  var createdAt: String? = null,
  @Expose
  @SerializedName("updatedAt")
  var updatedAt: String? = null
) : Parcelable