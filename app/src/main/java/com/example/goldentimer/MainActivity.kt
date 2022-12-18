package com.example.goldentimer

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.goldentimer.adapter.SwipeController
import com.example.goldentimer.adapter.Timer_Adapter
import com.example.goldentimer.database.AppDatabase
import com.example.goldentimer.database.Timers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "TAG_Main_Activity"
//    lateinit var main_recyclerView: RecyclerView
//    lateinit var main_adapter: Timer_Adapter

    var db: AppDatabase? = null

    var timersList = mutableListOf<Timers>()

    //swipe test
//    var swipeController : SwipeController? = null
    var helper: ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //내부DB에 있는 데이터들을 불러와 어댑터에 추가해야 한다

        //초기화
        db = AppDatabase.getInstance(this)
        //이전에 저장한 내용 모두 불러와서 추가
        val savedTimers = db!!.timersDao().getAll()
        if (savedTimers.isNotEmpty()) {
            timersList.addAll(savedTimers)
        }

        //어댑터 연결, 아이템 클릭 -> 토스트
        val adapter = Timer_Adapter(timersList)
        adapter.setItemClickListener(object : Timer_Adapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val timers = timersList[position]
                Log.d(TAG, "Clicked item ${timers}!! ${timers.id}")
                toTimer(timers.id)
                //타이머 화면으로 이동
                Log.d(TAG, "Main -> Timer | Button | Clicked!")
            }

        })
        //swipe button 관련
        helper = ItemTouchHelper(SwipeController(adapter))
        helper!!.attachToRecyclerView(recyclerview_timer)

        recyclerview_timer.adapter = adapter

        //swipe test
//        swipeController = SwipeController(object : SwipeControllerActions() {
//            override fun onRightClicked(position: Int) {
//                adapter.players.remove(position)
//                adapter.notifyItemRemoved(position)
//                adapter.notifyItemRangeChanged(position, mAdapter.getItemCount())
//            }
//        })



        //==
        add_button.setOnClickListener {
            //추가 버튼 -> 타이머 커스텀 페이지로 넘어가야함. CustomActivity
            Log.d(TAG, "Main -> Custom | Button | Clicked!")
            toCustom()
            adapter.notifyDataSetChanged()    //이 코드가 있어야 정상적으로 추가가 됨 (실시간)
        }

        btn_stopwatch.setOnClickListener {
            //스톱워치로 이동
            Log.d(TAG,"Main -> Stopwatch | Button | Clicked!")
            toStopwatch()
        }
        btn_more.setOnClickListener {
            //더보기 -> firebase 연동
            Log.d(TAG,"Main -> Login | Button | Clicked!")
            toMore()
        }
    }
    //====test
    private fun setUpRecyclerView() {
        recyclerview_timer.addItemDecoration(object : ItemDecoration() {
            override fun onDraw(
                @NonNull c: Canvas,
                @NonNull parent: RecyclerView,
                @NonNull state: RecyclerView.State
            ) {
                helper!!.onDraw(c, parent, state)
            }
        })
    }
    //====test

    private fun toTimer(num: Int?) {
        var intent = Intent(this, TimerActivity::class.java)
        if (num == null) {
            intent.putExtra("timer-id", 404)
        } else {
            intent.putExtra("timer-id", num)
        }
        startActivity(intent)
    }
    private fun toCustom() {
        var intent = Intent(this, CustomActivity::class.java)
        startActivity(intent)
    }
    private fun toStopwatch(){
        var intent = Intent(this, StopwatchActivity::class.java)
        startActivity(intent)
    }
    //둘러보기
    private fun toMore() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}