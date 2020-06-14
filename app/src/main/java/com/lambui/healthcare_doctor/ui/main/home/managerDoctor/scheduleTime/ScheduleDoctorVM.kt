package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.scheduleTime

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.WorkingScheduleFullModel
import com.lambui.healthcare_doctor.data.source.repositories.DoctorRepository
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider

class ScheduleDoctorVM(private val baseSchedulerProvider: BaseSchedulerProvider,
                       private val doctorRepository: DoctorRepository) : BaseViewModel() {
    var workingSchedule = MutableLiveData<WorkingScheduleFullModel>()
}