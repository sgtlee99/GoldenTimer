package com.example.goldentimer.adapter

import coil.api.load
import com.example.goldentimer.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.menu_items.view.*


class Menu_Adapter(val menu: String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.menu_items
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.info_menu.text = menu
    }
}