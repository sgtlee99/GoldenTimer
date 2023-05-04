package com.sjdev0809.goldentimer.activity

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sjdev0809.goldentimer.R
import com.sjdev0809.goldentimer.adapter.Timer_Adapter
import com.sjdev0809.goldentimer.database.AppDatabase
import com.sjdev0809.goldentimer.database.Timers
import com.sjdev0809.goldentimer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    val TAG = "TAG_Main_Activity"

    private var adapter: Timer_Adapter? = null

    var db: AppDatabase? = null

    var timersList = mutableListOf<Timers>()

    //swipe
    private val p: Paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //test swipe
        initSwipe()
        initalize()
        initData()
        initLayout()
    }

    private fun initalize() {
        //초기화
        db = AppDatabase.getInstance(this)
        //이전에 저장한 내용 모두 불러와서 추가
        val savedTimers = db!!.timersDao().getAll1()
        if (savedTimers.isNotEmpty()) {
            timersList.addAll(savedTimers)
        }

    }

    private fun initData() {

    }

    private fun initLayout() {
        initTimerRecyclerView()

        binding.loAddButton.setOnClickListener {
            //추가 버튼 -> 타이머 커스텀 페이지로 넘어가야함. CustomActivity
//            Log.d(TAG, "Main -> Custom | Button | Clicked!")
//            toCustom()
            var intent = Intent(this, CustomActivity::class.java)
            startActivity(intent)

            adapter!!.notifyDataSetChanged()    //이 코드가 있어야 정상적으로 추가가 됨 (실시간)
        }

        binding.btnStopwatch.setOnClickListener {
            //스톱워치로 이동
            Log.d(TAG, "Main -> Stopwatch | Button | Clicked!")
            var intent = Intent(this, StopwatchActivity::class.java)
            startActivity(intent)
        }
        btn_more.setOnClickListener {
            //더보기 -> firebase 연동
            Log.d(TAG, "Main -> Login | Button | Clicked!")
            var intent = Intent(this, ShareActivity::class.java)
            startActivity(intent)
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
                        val timer = Timers()
                        timer.id = timersList[position].id
                        timersList.removeAt(position)
                        adapter?.notifyItemRemoved(position)

                        var db: AppDatabase? = AppDatabase.getInstance(applicationContext)
                        db?.timersDao()?.delete(timer)
                        Log.d(TAG, "$timersList ${timersList.size}")
                    } else {    //오른쪽으로 스와이프

                    }
                }
                //

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

    private fun initTimerRecyclerView() {
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

}