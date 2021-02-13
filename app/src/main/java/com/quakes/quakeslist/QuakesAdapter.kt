package com.quakes.quakeslist

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quakes.model.Earthquake

class QuakesAdapter : PagedListAdapter<Earthquake, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as QuakeViewHolder).bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuakeViewHolder.create(parent)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Earthquake>() {

            override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake) =
                oldItem.eqid == newItem.eqid

            override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake) =
                oldItem == newItem
        }
    }
}