package com.example.goldentimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goldentimer.adapter.Timer_Adapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //내부DB에 있는 데이터들을 불러와 어댑터에 추가해야 한다

        //어뎁터 선언 - groupie
        val adapter = GroupAdapter<GroupieViewHolder>()

        //어댑터에 데이터 추가    ==> TimerModel( title , menu, image, time)
        adapter.add(Timer_Adapter("황금비율", "신라면", R.drawable.ramen,300))
        adapter.add(Timer_Adapter("황금비율", "신라면", R.drawable.ramen,300))



        //어뎁터 연결
        recyclerview_timer.adapter = adapter

        add_button.setOnClickListener {
            //추가 버튼 -> 타이머 커스텀 페이지로 넘어가야함. CustomActivity
            var intent = Intent(this, CustomActivity::class.java)
            startActivity(intent)
            //추후 삭제 예정
            adapter.add(Timer_Adapter("황금비율", "신라면", R.drawable.ramen,300))
        }
    }
}