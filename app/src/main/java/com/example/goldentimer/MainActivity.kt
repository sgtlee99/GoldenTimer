package com.example.goldentimer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldentimer.adapter.TimerModel
import com.example.goldentimer.adapter.Timer_Adapter
import com.example.goldentimer.database.AppDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "TAG_Main_Activity"
    lateinit var main_recyclerView : RecyclerView
    lateinit var main_adapter : Timer_Adapter

//    var db : AppDatabase? = null

    lateinit var itemList : ArrayList<TimerModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //내부DB에 있는 데이터들을 불러와 어댑터에 추가해야 한다

        //초기화
//        db = AppDatabase.getInstance(this)
//        //이전에 저장한 내용 모두 불러와서 추가
//        val savedTimers = db!!.timersDao().getAll()
//        if (savedTimers.isNotEmpty()) {
//            itemList.addAll(savedTimers)
//        }
        add_button.setOnClickListener {
            //추가 버튼 -> 타이머 커스텀 페이지로 넘어가야함. CustomActivity
            var intent = Intent(this, CustomActivity::class.java)
            startActivity(intent)
            //추후 삭제 예정
        }


        //어댑터 연걸
        main_recyclerView = findViewById(R.id.recyclerview_timer)
        itemList = tempTimer()
//        setAdapter()

        main_recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        main_adapter = Timer_Adapter(itemList)
        main_recyclerView.adapter = main_adapter

    }


    fun loadBitmap(img_resourse: Int): Bitmap? {        //이미지 리소스를 비트맵으로 변경시켜주는 함수
        val drawable = getDrawable(img_resourse)
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        return bitmap
    }

    private fun tempTimer(): ArrayList<TimerModel> {
        var tempTimers = ArrayList<TimerModel>()


        tempTimers.add(TimerModel("황금비율", "신라면", loadBitmap(R.drawable.ramen),30,0))
        tempTimers.add(TimerModel("황금비율", "신라면", loadBitmap(R.drawable.ramen),34,0))
        tempTimers.add(TimerModel("황금비율", "신라면", loadBitmap(R.drawable.ramen),5,0))
        return tempTimers

    }

}