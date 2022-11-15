package com.example.goldentimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goldentimer.adapter.timer_adapter
import com.example.goldentimer.model.TimerModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //어뎁터 선언 - groupie
        val adapter = GroupAdapter<GroupieViewHolder>()

        //어댑터에 데이터 추가    ==> TimerModel( title , menu, image, time)
        adapter.add(timer_adapter("황금비율", "신라면", R.drawable.ramen,300))
        adapter.add(timer_adapter("황금비율", "신라면", R.drawable.ramen,300))



        //어뎁터 연결
        recyclerview_timer.adapter = adapter

        add_button.setOnClickListener {
            //추가 버튼 -> 타이머 커스텀 페이지로 넘어가야함

            //추후 삭제 예정
            adapter.add(timer_adapter("황금비율", "신라면", R.drawable.ramen,300))
        }
    }
}