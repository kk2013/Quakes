package com.quakes.quakeslist

import android.graphics.Color
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
            itemView.eqid.text = formatText(R.string.earthquake, it.eqid)
            itemView.magnitude.text = formatText(R.string.magnitude, it.magnitude.toString())
            if(it.magnitude >= 8) {
                itemView.magnitude.setTextColor(Color.RED)
            }
            itemView.datetime.text = formatText(R.string.date, it.datetime)
            itemView.latitude.text = formatText(R.string.latitude, it.lat.toString())
            itemView.longitude.text = formatText(R.string.longitude, it.lng.toString())
            itemView.setOnClickListener { onClickListener(quake) }
        }
    }

    private fun formatText(resId: Int, value: String): String {
        return String.format(itemView.context.getString(resId), value)
    }

    companion object {
        fun create(parent: ViewGroup): QuakeViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.quake_item_row, parent, false)
            return QuakeViewHolder(view)
        }
    }
}