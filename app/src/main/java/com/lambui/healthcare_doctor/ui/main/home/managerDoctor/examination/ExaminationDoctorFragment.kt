package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.data.model.ExaminationModel
import com.lambui.healthcare_doctor.dialog.DialogConfirmDelete
import com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination.adapter.ExaminationDoctorAdapter
import com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination.adapter.IItemListener
import kotlinx.android.synthetic.main.fragment_examination_doctor.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExaminationDoctorFragment : BaseFragment<ExaminationDoctorVM>(), IItemListener {
    override val layoutID: Int
        get() = R.layout.fragment_examination_doctor
    override val viewModelx: ExaminationDoctorVM by sharedViewModel()
    private val adapterExamination: ExaminationDoctorAdapter by lazy {
        ExaminationDoctorAdapter(null, this)
    }

    override fun initialize() {
        initAdapter()
        with(viewModelx){
            getListExaminationOfDoctor()
        }

    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            listExamination.observe(this@ExaminationDoctorFragment, Observer {
                adapterExamination.initData(it.toMutableList())
            })
            onError.observe(this@ExaminationDoctorFragment, Observer {
                handleApiError(it)
            })

        }
    }

    override fun registerOnClick() {
    }

    override fun onDeleteExamination(item: ExaminationModel, position: Int) {
        showConfirmDeleteDialog("${item?.serviceName}"
            , resources.getString(R.string.text_title_remove_service)
            , object : DialogConfirmDelete.OnButtonClickedListener {
                override fun onPositiveClicked() {
                    viewModelx.deleteExamination(position)
                }
                override fun onNegativeClicked() {

                }
            })
    }

    override fun onItemChoice(position: Int) {

    }

    private fun initAdapter() {
        rvExamination.adapter = adapterExamination
        rvExamination.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}