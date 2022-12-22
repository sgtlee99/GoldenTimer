package com.example.goldentimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.goldentimer.adapter.Menu_Adapter
import com.example.goldentimer.adapter.Record_Adapter
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_stopwatch.*
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*
import kotlin.concurrent.timer

class StopwatchActivity : AppCompatActivity() {
    //TAG
    private val TAG = "TAG_Stopwatch_Activity"

    //스톱워치에 사용
    private var time = 0
    private var timerTask: Timer? = null

    //기록 어뎁터 그룹피
    val adapter = GroupAdapter<GroupieViewHolder>()
    var generated_num: Int = 0
    var rec: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        //레코드
        record_list.adapter = adapter
        //타이머 시작, 정지, 리셋 버튼
        s_timer_start.setOnClickListener { startTimer() }
        s_timer_pause.setOnClickListener { pauseTimer() }
        s_timer_reset.setOnClickListener {
            resetTimer()
            adapter.clear()
        }
        s_timer_record.setOnClickListener {
            rec = s_timer_count.text.toString()
            adapter.add(Record_Adapter(++generated_num, rec))
        }


        s_btn_timerlist.setOnClickListener {
            //메인으로 이동
            Log.d(TAG, "Stopwatch -> Main | Button | Clicked!")
            toMain()
        }
        s_btn_more.setOnClickListener {
            //더보기 -> firebase 연동
            toMore()
        }

    }

    private fun startTimer() {      //타이머 시작
        timerTask = timer(period = 10) {
            time++

            var sec = time / 100
            var milli = time % 100
            runOnUiThread {
                s_timer_count.text = "${sec} : ${milli}"
            }
        }
    }

    private fun pauseTimer() {      //타이머 정지
        timerTask?.cancel()
    }

    private fun resetTimer() {      //타이머 리셋
        timerTask?.cancel()
        time = 0
        s_timer_count.text = "00 : 00"
    }
    //메인페이지
    private fun toMain() {
        var intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
    //찾아보기
    private fun toMore() {
        var intent = Intent(this, ShareActivity::class.java)
        startActivity(intent)
    }
}