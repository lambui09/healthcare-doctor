package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationModel(
    @Expose
    @SerializedName("type")
    val type: String? = null,
    @Expose
    @SerializedName("coordinates")
    val coordinates: List<Double>
) : Parcelable {
    fun setLatLag(location: List<Double>): LocationModel {
        return LocationModel(
            type = type,
            coordinates = location
        )
    }
}