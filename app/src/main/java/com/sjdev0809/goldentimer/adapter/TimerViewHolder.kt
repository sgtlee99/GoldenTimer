package com.sjdev0809.goldentimer.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sjdev0809.goldentimer.database.Timers
import kotlinx.android.synthetic.main.timer_items.view.*

class TimerViewHolder(v : View) : RecyclerView.ViewHolder(v) {
    var view : View = v

    fun bind(item : Timers) {
        view.title.text = item.t_title
        view.menu.text = item.t_menu
        view.menu_img.setImageBitmap(item.t_img)
        view.time_view_min.text = "%02d".format(item.t_min.toInt())
        view.time_view_sec.text = "%02d".format(item.t_sec.toInt())
    }
}