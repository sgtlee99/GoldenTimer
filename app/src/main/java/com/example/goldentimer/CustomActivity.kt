package com.example.goldentimer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.goldentimer.adapter.Menu_Adapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.activity_main.*

class CustomActivity : AppCompatActivity(), View.OnClickListener {
    val TAG: String = "[GT]<CustomActivity>"
    val DEFAULT_SELECT: Int = 1
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        //제목, 메뉴, 시간
        //이미지는 기본 에셋제공하여 선택하는 구조
        //버튼 하나를 선택했을때 다른버튼들의 채도가 낮아지며
        //아래의 메뉴 리스트의 아이템들이 변경된다

        noodle.setOnClickListener(this)
        fry.setOnClickListener(this)
        bake.setOnClickListener(this)
        boil.setOnClickListener(this)

        recyclerview_menu.adapter = adapter
        //면을 선택했을때
        adapter.setOnItemClickListener { item, view ->

            Log.d(TAG, (item as Menu_Adapter).menu)     //선택한 메뉴가 뭔지 보여주는 LOG


        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(p0: View?) {
        when (p0?.id) {
            noodle.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("라면"))
                adapter.add(Menu_Adapter("소면"))
                adapter.add(Menu_Adapter("우동"))
                adapter.add(Menu_Adapter("파스타"))
            }
            fry.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("프라이"))
            }
            bake.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("베이킹"))
            }
            boil.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("완숙"))
                adapter.add(Menu_Adapter("반숙"))
                adapter.add(Menu_Adapter("중면"))
            }
            else -> {
                adapter.add(Menu_Adapter("라면"))
                adapter.add(Menu_Adapter("소면"))
                adapter.add(Menu_Adapter("중면"))
                adapter.add(Menu_Adapter("우동"))
                adapter.add(Menu_Adapter("파스타"))
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun Noodle_slected() {
        noodle.setBackgroundColor(R.color.bright_red)
        fry.setBackgroundColor(R.color.dark_red)
        bake.setBackgroundColor(R.color.dark_red)
        boil.setBackgroundColor(R.color.dark_red)
    }

    @SuppressLint("ResourceAsColor")
    fun Fry_slected() {
        noodle.setBackgroundColor(R.color.dark_red)
        fry.setBackgroundColor(R.color.bright_red)
        bake.setBackgroundColor(R.color.dark_red)
        boil.setBackgroundColor(R.color.dark_red)
    }

    @SuppressLint("ResourceAsColor")
    fun Bake_slected() {
        noodle.setBackgroundColor(R.color.dark_red)
        fry.setBackgroundColor(R.color.dark_red)
        bake.setBackgroundColor(R.color.bright_red)
        boil.setBackgroundColor(R.color.dark_red)
    }

    @SuppressLint("ResourceAsColor")
    fun Boil_slected() {
        noodle.setBackgroundColor(R.color.dark_red)
        fry.setBackgroundColor(R.color.dark_red)
        bake.setBackgroundColor(R.color.dark_red)
        boil.setBackgroundColor(R.color.bright_red)
    }

}