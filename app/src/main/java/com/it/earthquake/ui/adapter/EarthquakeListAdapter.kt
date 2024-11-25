package com.it.earthquake.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.it.earthquake.R
import com.it.earthquake.databinding.ItemEarthquakeBinding
import com.it.earthquake.model.Feature
import com.it.earthquake.viewmodel.EarthquakeListViewModel

class EarthquakeListAdapter(
    private val earthquakes: ArrayList<Feature>,
    private val onItemClick: (Feature) -> Unit
) :
    RecyclerView.Adapter<EarthquakeListAdapter.VM>() {

    class VM(val binding: ItemEarthquakeBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VM {
        val binding = DataBindingUtil.inflate<ItemEarthquakeBinding>(
            LayoutInflater.from(
                parent.context
            ),
            R.layout.item_earthquake,
            parent,
            false
        )
        return VM(binding)
    }

    override fun getItemCount(): Int = earthquakes.size

    override fun onBindViewHolder(holder: VM, position: Int) {
        val earthquakeItem = earthquakes[position]
        holder.binding.item = earthquakeItem
        holder.binding.tvRegion.setTextColor(if (earthquakeItem.properties.mag >= 7.5) Color.RED else Color.BLACK)
        holder.binding.tvMagnitude.setTextColor(if (earthquakeItem.properties.mag >= 7.5) Color.RED else Color.BLACK)
        holder.binding.root.setOnClickListener {
            onItemClick(earthquakeItem)
        }
    }

    fun updateEarthquakes(liste: ArrayList<Feature>) {
        val diffCallback = EarthquakeDiffCallback(oldList = earthquakes, newList = liste)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        earthquakes.clear()
        earthquakes.addAll(liste)
        diffResult.dispatchUpdatesTo(this)
    }

}