package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.data.model.ExaminationModel
import com.lambui.healthcare_doctor.enums.TypeExaminationSelect
import com.lambui.healthcare_doctor.utils.extension.hide
import com.lambui.healthcare_doctor.utils.extension.show
import kotlinx.android.synthetic.main.item_examination.view.*
import kotlinx.android.synthetic.main.item_swipe_examination.view.*

class ExaminationDoctorAdapter(
    private var listItem: ArrayList<ExaminationModel>?,
    private val listener: IItemListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IItemListener {
    private var selectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM_EXAMINATION -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_swipe_examination, parent, false)
                return ExaminationVH(view, this)

            }
            TYPE_CREATE_NEW_ITEM -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_add_new_examination, parent, false)
                return AddNewExaminationVH(view, this)
            }
            else -> throw IllegalArgumentException()
        }

    }

    override fun getItemCount(): Int {
        return if (listItem.isNullOrEmpty()) 0 else listItem!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExaminationVH -> {
                listItem?.let {
                    holder.bindData(listItem!![position], position, selectedPosition == position)
                }
            }
            is AddNewExaminationVH -> holder.bindData(position, selectedPosition == position)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (listItem == null || position == listItem?.size) {
            return TYPE_CREATE_NEW_ITEM
        }
        return TYPE_ITEM_EXAMINATION
    }

    companion object {
        private const val TYPE_ITEM_EXAMINATION = 0
        private const val TYPE_CREATE_NEW_ITEM = 1
    }

    override fun onDeleteExamination(item: ExaminationModel, position: Int) {
        listener.onDeleteExamination(item, position)
    }

    override fun onItemChoice(position: Int) {
        val prePositionSelect = selectedPosition
        selectedPosition = position
        notifyItemChanged(prePositionSelect)
        notifyItemChanged(position)
        listener.onItemChoice(position)
    }

    fun initData(creditCards: List<ExaminationModel>?) {
        if (listItem == null) {
            listItem = arrayListOf()
        }
        listItem?.clear()
        creditCards?.let {
            listItem?.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun getPositionExaminationSelected(): TypeExaminationSelect {
        if (selectedPosition < 0) {
            return TypeExaminationSelect.NO_SELECT
        }
        if (selectedPosition == listItem?.size) {
            return TypeExaminationSelect.CREATE_NEW_EXAMINATION
        }
        return TypeExaminationSelect.OLD_EXAMINATION
    }
}

class ExaminationVH(private val view: View, private val listener: IItemListener) :
    RecyclerView.ViewHolder(view) {
    fun bindData(examination: ExaminationModel, position: Int, isSelect: Boolean) = with(view) {
        checkSelected(isSelected)
        initEvent(examination, position)
        tvExamination.text = examination.serviceName
    }

    private fun checkSelected(selected: Boolean) = with(view) {
        if (selected) {
            imgSelectItemExamination.show()
            return@with
        }
        imgSelectItemExamination.hide()
    }

    private fun initEvent(examinationModel: ExaminationModel, position: Int) = with(view) {
        viewDelete.setOnClickListener {
            listener.onDeleteExamination(examinationModel, position)
        }
        itemExamination.setOnClickListener {
            listener.onItemChoice(position)
        }
    }
}

class AddNewExaminationVH(private val view: View, private val listener: IItemListener) :
    RecyclerView.ViewHolder(view) {
    fun bindData(position: Int, isSelect: Boolean) = with((view)) {
        checkSelected(isSelected)
        llContainerItemExamination.setOnClickListener {
            listener.onItemChoice(position)
        }
    }

    private fun checkSelected(selected: Boolean) = with(view) {
        if (selected) {
            imgSelectItemExamination.show()
            return@with
        }
        imgSelectItemExamination.hide()
    }
}


interface IItemListener {
    fun onDeleteExamination(item: ExaminationModel, position: Int)
    fun onItemChoice(position: Int)
}