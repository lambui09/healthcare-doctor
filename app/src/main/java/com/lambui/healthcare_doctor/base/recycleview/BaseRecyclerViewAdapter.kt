package com.lambui.healthcare_doctor.base.recycleview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.utils.extension.notNull

/**
 * Base Adapter.
 *
 * @param <V> is a type extend from {@link RecyclerView.ViewHolder}
 * @param <T> is a Object
 */
abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder>
constructor(
    protected val context: Context,
    protected var layoutInflater: LayoutInflater = LayoutInflater.from(context),
    protected var dataList: MutableList<T> = mutableListOf()) : RecyclerView.Adapter<V>() {

    protected var itemClickListener: OnItemClickListener<T>? = null

    protected var handler = Handler(Looper.getMainLooper())

    override fun getItemCount(): Int = dataList.size

    fun getData(): MutableList<T> = dataList

    fun updateData(newData: MutableList<T>?, diffResult: DiffUtil.DiffResult) {
        handler.post {
            newData.notNull {
                dataList.clear()
                dataList.addAll(it)
                diffResult.dispatchUpdatesTo(this)
                notifyDataSetChanged()
            }
        }
    }

    fun updateData(newData: MutableList<T>?) {
        handler.post {
            newData.notNull {
                dataList.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun replaceData(newData: MutableList<T>?) {
        handler.post {
            newData.notNull {
                dataList = it
                notifyDataSetChanged()
            }
        }
    }

    fun clearData(isNotify: Boolean = true) {
        dataList.clear()
        if (isNotify) notifyDataSetChanged()
    }

    open fun getItem(position: Int): T? {
        return if (position < 0 || position >= dataList.size) {
            null
        } else dataList[position]
    }

    fun addItem(data: T, position: Int) {
        dataList.add(position, data)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int, isNotifyAll: Boolean = false) {
        if (position < 0 || position >= dataList.size) {
            return
        }
        dataList.removeAt(position)
        if (isNotifyAll) notifyDataSetChanged() else notifyItemChanged(position)
    }

    fun replaceItem(item: T, position: Int, isNotifyAll: Boolean = false) {
        if (position < 0 || position >= dataList.size) {
            return
        }
        dataList[position] = item
        if (isNotifyAll) notifyDataSetChanged() else notifyItemChanged(position)
    }

    fun registerItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        itemClickListener = onItemClickListener
    }

    fun unRegisterItemClickListener() {
        itemClickListener = null
    }
}