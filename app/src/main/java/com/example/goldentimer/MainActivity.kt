package com.example.goldentimer

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

    private var adapter: Timer_Adapter? = null

    var db: AppDatabase? = null

    var timersList = mutableListOf<Timers>()
    //swipe test
//    var helper: ItemTouchHelper? = null
    private val p: Paint = Paint()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //test swipe
        initSwipe()
        //내부DB에 있는 데이터들을 불러와 어댑터에 추가해야 한다

        //초기화
        db = AppDatabase.getInstance(this)
        //이전에 저장한 내용 모두 불러와서 추가
        val savedTimers = db!!.timersDao().getAll1()
        if (savedTimers.isNotEmpty()) {
            timersList.addAll(savedTimers)
        }



        //어댑터 연결, 아이템 클릭 -> 토스트
        adapter = Timer_Adapter(timersList)
        adapter!!.setItemClickListener(object : Timer_Adapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val timers = timersList[position]
                Log.d(TAG, "Clicked item ${timers}!! ${timers.id}")
                toTimer(timers.id)
                //타이머 화면으로 이동
                Log.d(TAG, "Main -> Timer | Button | Clicked!")
            }

        })

        recyclerview_timer.adapter = adapter

        add_button.setOnClickListener {
            //추가 버튼 -> 타이머 커스텀 페이지로 넘어가야함. CustomActivity
            Log.d(TAG, "Main -> Custom | Button | Clicked!")
            toCustom()
            adapter!!.notifyDataSetChanged()    //이 코드가 있어야 정상적으로 추가가 됨 (실시간)
        }

        btn_stopwatch.setOnClickListener {
            //스톱워치로 이동
            Log.d(TAG, "Main -> Stopwatch | Button | Clicked!")
            toStopwatch()
        }
        btn_more.setOnClickListener {
            //더보기 -> firebase 연동
            Log.d(TAG, "Main -> Login | Button | Clicked!")
            toMore()
        }
    }

    private fun initSwipe() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) { //왼쪽으로 스와이프
                            timersList.removeAt(position)
                            adapter?.notifyItemRemoved(position)
                            db?.timersDao()?.deleteById(position)
                    } else {    //오른쪽으로 스와이프

                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
//                var icon : Bitmap
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView: View = viewHolder.itemView
                        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                        val width = height / 3
                        if (dX > 0) {
                            //오른쪽으로 스와이프 했을 때
                        } else {
                            //왼쪽으로 스와이프 했을 때
                            p.setColor(Color.parseColor("#FF473D"))
                            val background = RectF(
                                itemView.right.toFloat() + dX,
                                itemView.top.toFloat(),
                                itemView.right.toFloat(),
                                itemView.bottom.toFloat()
                            )
                            c.drawRect(background, p)
                            /*
                             * icon 추가할 수 있음.
                             */
                            //icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_png); //vector 불가!
                            // RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                            //c.drawBitmap(icon, null, icon_dest, p);
                        }
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview_timer)


    }


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

    private fun toStopwatch() {
        var intent = Intent(this, StopwatchActivity::class.java)
        startActivity(intent)
    }

    //둘러보기
    private fun toMore() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}