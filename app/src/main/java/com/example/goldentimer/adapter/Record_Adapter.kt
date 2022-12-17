package com.example.goldentimer.adapter

import com.example.goldentimer.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.menu_items.view.*
import kotlinx.android.synthetic.main.record_items.view.*

class Record_Adapter(val r_num : Int, val r_time : String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.record_items
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.record_num.text = r_num.toString()
        viewHolder.itemView.record_time.text = r_time.toString()

    }
}