package com.it.earthquake.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.it.earthquake.R
import com.it.earthquake.databinding.ItemEarthquakeBinding
import com.it.earthquake.model.Feature

class EarthquakeListAdapter(private val earthquakes: ArrayList<Feature>) :
    RecyclerView.Adapter<EarthquakeListAdapter.VM>() {

    class VM(val binding: ItemEarthquakeBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VM {
        val binding = DataBindingUtil.inflate<ItemEarthquakeBinding>(
            LayoutInflater.from(
                parent.context),
                R.layout.item_earthquake,
                parent,
                false
            )
        return VM(binding)
    }

    override fun getItemCount(): Int {
        return earthquakes.size
    }

    override fun onBindViewHolder(holder: VM, position: Int) {
        val earthquakeItem = earthquakes[position]
        holder.binding.item = earthquakeItem
    }

    fun updateEarthquakes(liste: ArrayList<Feature>) {
        earthquakes.clear()
        earthquakes.addAll(liste)
        notifyDataSetChanged()
    }

}