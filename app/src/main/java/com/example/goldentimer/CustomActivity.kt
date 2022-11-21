package com.example.goldentimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goldentimer.adapter.Menu_Adapter
import com.example.goldentimer.adapter.Timer_Adapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.activity_main.*

class CustomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        //제목, 메뉴, 시간
        //이미지는 기본 에셋제공하여 선택하는 구조
        val adapter = GroupAdapter<GroupieViewHolder>()

        //면을 선택했을때
        adapter.add(Menu_Adapter("라면"))
        adapter.add(Menu_Adapter("소면"))
        adapter.add(Menu_Adapter("중면"))
        adapter.add(Menu_Adapter("칼국수"))
        adapter.add(Menu_Adapter("우동"))
        adapter.add(Menu_Adapter("냉면"))
        recyclerview_menu.adapter = adapter

    }
}