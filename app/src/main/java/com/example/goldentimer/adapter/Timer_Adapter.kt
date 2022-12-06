package com.example.goldentimer.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.goldentimer.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.timer_items.view.*

class Timer_Adapter(val itemList: ArrayList<TimerModel>) : RecyclerView.Adapter<Timer_Adapter.ViewHolder>() {

    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Timer_Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_items, parent, false)
        return ViewHolder(view)
    }
    // 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return itemList.size
    }
    //    override fun onBindViewHolder(holder: Timer_Adapter.ViewHolder, position: Int) {
//        holder.tm_title.text = itemList[position].m_title
//
//    }
//     View에 내용 입력
    override fun onBindViewHolder(holder: Timer_Adapter.ViewHolder, position: Int) {
        holder.tm_title.text = itemList[position].m_title
        holder.tm_menu.text = itemList[position].m_menu
        holder.tm_image.setImageBitmap(itemList[position].m_image)
        holder.tm_min.text = itemList[position].m_min.toString()
        holder.tm_sec.text = itemList[position].m_sec.toString()

        // 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

    }

    // (4) 레이아웃 내 View 연결                                            // contextmenu 코드
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        val tm_title: TextView = itemView.findViewById(R.id.title)
        val tm_menu: TextView = itemView.findViewById(R.id.menu)
        val tm_image: ImageView = itemView.findViewById(R.id.menu_img)
        val tm_min: TextView = itemView.findViewById(R.id.time_view_min)
        val tm_sec: TextView = itemView.findViewById(R.id.time_view_sec)


        // contextmenu 코드
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            // menu?.setHeaderTitle("선택하세요")
            var item1 = menu?.add(0, 0, 0, "수정")
            var item2 = menu?.add(0, 1, 1, "삭제")

            item1?.setOnMenuItemClickListener { true } // 입력되어 있던 데이터를 불러와서 보여줌 -> 수정
            item2?.setOnMenuItemClickListener { true } // 입력되어 있던 데이터, 해당 카드뷰를 삭제

        }

    }
    //리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)

    }

    //외부에서 클릭시 이벤트 설정
    fun setItemClickListener(onItemClickListener: Timer_Adapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    // setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener: Timer_Adapter.OnItemClickListener

}