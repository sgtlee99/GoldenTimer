package com.example.goldentimer.adapter

import coil.api.load
import com.example.goldentimer.R
import com.example.goldentimer.model.TimerModel
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.timer_preview.view.*

class timer_adapter(val title : String, val menu : String ,val img : Int, val time : Int) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.timer_preview
    }
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.title.text = title
        viewHolder.itemView.menu.text = menu
        viewHolder.itemView.menu_img.load(img)
        viewHolder.itemView.time_view.text = time.toString()
    }
}