package com.lambui.healthcare_doctor.ui.introduce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.data.model.IntroduceSliderModel
import kotlinx.android.synthetic.main.item_introduce_slider_page.view.*

class IntroduceViewPagerAdapter(private val listSliderItem : ArrayList<IntroduceSliderModel>): RecyclerView.Adapter<IntroduceVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroduceVH {
        return IntroduceVH(LayoutInflater.from(parent.context).inflate(
            R.layout.item_introduce_slider_page,
            parent,false
        ))
    }

    override fun getItemCount(): Int {
        return if (listSliderItem.isNullOrEmpty()) 0 else listSliderItem.size
    }

    override fun onBindViewHolder(holder: IntroduceVH, position: Int) {
        val introduceSliderModel = listSliderItem[position]
        holder.itemView.apply {
            this.tvTitle.text = introduceSliderModel.title
            this.tvDescription.text = introduceSliderModel.description
            this.imgPageIntroduce.setImageResource(introduceSliderModel.imageSlider)
        }
    }
}
class IntroduceVH(itemView : View) : RecyclerView.ViewHolder(itemView)