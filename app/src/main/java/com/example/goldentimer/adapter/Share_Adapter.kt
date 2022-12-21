package com.example.goldentimer.adapter

import android.graphics.Bitmap
import com.example.goldentimer.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.share_items.view.*



class Share_Adapter(val title : String, val menu : String,  val img : Bitmap,  val min : String, val sec : String, val name : String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.share_items
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.sh_title.text = title
        viewHolder.itemView.sh_menu.text = menu
        viewHolder.itemView.sh_menu_img.setImageBitmap(img)
        viewHolder.itemView.sh_time_view_min.text = min
        viewHolder.itemView.sh_time_view_sec.text = sec
        viewHolder.itemView.sh_name.text = name

    }
}



