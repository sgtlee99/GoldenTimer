package com.example.goldentimer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldentimer.adapter.TimerModel
import com.example.goldentimer.adapter.Timer_Adapter
import com.example.goldentimer.database.AppDatabase
import com.example.goldentimer.database.Timers
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Timer

class MainActivity : AppCompatActivity() {

    val TAG = "TAG_Main_Activity"
    lateinit var main_recyclerView: RecyclerView
    lateinit var main_adapter: Timer_Adapter


    //    lateinit var itemList : ArrayList<Timers>
//    var itemList : mutableListOf<Timers>()

    var db: AppDatabase? = null

    var timersList = mutableListOf<Timers>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //내부DB에 있는 데이터들을 불러와 어댑터에 추가해야 한다

        //초기화
        db = AppDatabase.getInstance(this)
        //이전에 저장한 내용 모두 불러와서 추가
        val savedTimers = db!!.timersDao().getAll()
        if(savedTimers.isNotEmpty()) {
            timersList.addAll(savedTimers)
        }

        //어댑터 연결, 아이템 클릭 -> 토스트
        val adapter = Timer_Adapter(timersList)
        adapter.setItemClickListener(object : Timer_Adapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val timers = timersList[position]

                Toast.makeText(this@MainActivity, "Clicked item ${timers}!! ${timers.t_title}", Toast.LENGTH_SHORT ).show()

            }

        })



        recyclerview_timer.adapter = adapter

        add_button.setOnClickListener {
            //추가 버튼 -> 타이머 커스텀 페이지로 넘어가야함. CustomActivity
//            var intent = Intent(this, CustomActivity::class.java)
//            startActivity(intent)
            //추후 삭제 예정

            //테스트용!!!!!!
            //데이터 추가!!!!
            val timer = Timers("테스트", "메뉴", loadBitmap(R.drawable.ramen),3,15)    //Timers 생성
            db?.timersDao()?.insert(timer)
            timersList.add(timer)

            adapter.notifyDataSetChanged()

        }


//        //어댑터 연걸
//        main_recyclerView = findViewById(R.id.recyclerview_timer)
//        itemList = tempTimer()
////        setAdapter()
//
//        main_recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        main_adapter = Timer_Adapter(itemList)
//        main_recyclerView.adapter = main_adapter

    }


    fun loadBitmap(img_resourse: Int): Bitmap? {        //이미지 리소스를 비트맵으로 변경시켜주는 함수
        val drawable = getDrawable(img_resourse)
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        return bitmap
    }

    private fun tempTimer(): ArrayList<Timers> {
        var tempTimers = ArrayList<Timers>()


        tempTimers.add(Timers("황금비율", "신라면", loadBitmap(R.drawable.ramen), 30, 0))
        tempTimers.add(Timers("황금비율", "신라면", loadBitmap(R.drawable.ramen), 34, 0))
        tempTimers.add(Timers("황금비율", "신라면", loadBitmap(R.drawable.ramen), 5, 0))
        return tempTimers

    }

}