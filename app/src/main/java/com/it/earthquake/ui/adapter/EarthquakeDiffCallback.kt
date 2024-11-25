package com.it.earthquake.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.it.earthquake.model.Feature

class EarthquakeDiffCallback(
    private val oldList: ArrayList<Feature>,
    private val newList: ArrayList<Feature>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        // 对比基本内容和颜色
        return oldItem == newItem && oldItem.properties.mag == newItem.properties.mag
    }

}