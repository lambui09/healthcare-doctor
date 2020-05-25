package com.lambui.healthcare_doctor.ui.auths.signup

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.lambui.healthcare_doctor.BuildConfig
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.data.model.LocationModel
import kotlinx.android.synthetic.main.fragment_input_address.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InputAddressFragment : BaseFragment<RegisterVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_input_address
    override val viewModelx: RegisterVM by sharedViewModel()

    override fun initialize() {
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), BuildConfig.KEY_MAP)
        }
    }

    override fun onSubscribeObserver() {
        TODO("Not yet implemented")
    }

    override fun registerOnClick() {
        tvSearchPlace.setOnClickListener {
            callPlaceSearchIntent()
        }
        btnSend.setOnClickListener {
            if (viewModelx.address != null) {
                val patientId = viewModelx.getDoctorId() ?: ""
                viewModelx.updateAddressPatient(patientId, viewModelx.address!!)
            }
        }
    }
    private fun callPlaceSearchIntent() {
        try {
            val fields: List<Place.Field> =
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent: Intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(requireActivity())
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            when {
                resultCode == Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(it)
                        tvSearchPlace.text = place.name.toString()
                        val location = mutableListOf<Double>()
                        place.latLng?.let {
                            location.add(0, it.latitude)
                            location.add(1, it.longitude)
                        }
                        val locationModel = LocationModel(
                            type = "Point",
                            coordinates = location
                        )
                        viewModelx.setLocation(locationModel)
                    }
                }
                resultCode == AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(it)
                        Log.d("@@@@", "${status.toString()}")
                    }
                }
                requestCode == Activity.RESULT_CANCELED -> {
                    Log.d("@@@@", "failure, data return null}")
                }
            }
        }
    }

    companion object {
        private const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 100
    }
}