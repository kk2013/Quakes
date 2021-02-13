package com.quakes.quakeslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quakes.R
import com.quakes.model.Earthquake
import kotlinx.android.synthetic.main.quake_item_row.view.*

class QuakeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        quake: Earthquake?,
        onClickListener: (Earthquake) -> Unit
    ) {
        quake?.let {
            itemView.eqid.text = it.eqid
            itemView.datetime.text = it.datetime
            itemView.latitude.text = String.format(itemView.context.getString(R.string.latitude), it.lat.toString())
            itemView.longitude.text = String.format(itemView.context.getString(R.string.longitude), it.lng.toString())
            itemView.setOnClickListener { onClickListener(quake) }
        }
    }

    companion object {
        fun create(parent: ViewGroup): QuakeViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.quake_item_row, parent, false)
            return QuakeViewHolder(view)
        }
    }
}