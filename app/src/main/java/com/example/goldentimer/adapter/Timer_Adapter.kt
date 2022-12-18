package com.example.goldentimer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goldentimer.R
import com.example.goldentimer.database.Timers

class Timer_Adapter(private var itemList: List<Timers>) :
    RecyclerView.Adapter<TimerViewHolder>() /*, ItemTouchHelperListener*/ {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.timer_items, parent, false)
        return TimerViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
        holder.apply {
            bind(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getItem(): List<Timers>? {
        return itemList
    }

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    //외부에서 클릭시 이벤트 설정
    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    // setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener: OnItemClickListener


}


