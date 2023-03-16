package com.sjdev0809.goldentimer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sjdev0809.goldentimer.R
import com.sjdev0809.goldentimer.adapter.Record_Adapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_stopwatch.*
import java.util.*
import kotlin.concurrent.timer

class StopwatchActivity : BaseActivity() {
    //TAG
    private val TAG = "TAG_Stopwatch_Activity"

    //스톱워치에 사용
    private var time = 0
    private var timerTask: Timer? = null

    //기록 어뎁터 그룹피
    val adapter = GroupAdapter<GroupieViewHolder>()
    var generated_num: Int = 0
    var rec: String = ""

    //
    private var button_state : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        //레코드
        record_list.adapter = adapter
        //스톱워치 시작|정지, 리셋 버튼

        s_timer_reset.setOnClickListener {
            resetTimer()
            //버튼 원래 색을 되돌림
            s_play_stop_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            s_play_stop_btn.setBackgroundResource(R.drawable.button_state_blue)
            adapter.clear()
        }
        s_timer_record.setOnClickListener {
            rec = s_timer_count.text.toString()
            adapter.add(Record_Adapter(++generated_num, rec))
        }
        //play | stop switch button
        s_play_stop_btn.setOnClickListener {
            if (button_state==true) {
                s_play_stop_btn.setImageResource(R.drawable.ic_baseline_stop_24)
                s_play_stop_btn.setBackgroundResource(R.drawable.button_state_red)
                //stopwatch start
                startTimer()    //시작
                button_state=false
            } else {
                s_play_stop_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                s_play_stop_btn.setBackgroundResource(R.drawable.button_state_blue)
                //stopwatch pause
                pauseTimer()    //정지
                button_state=true
            }
        }

        //하단버튼
        s_btn_timerlist.setOnClickListener {
            //메인으로 이동
            Log.d(TAG, "Stopwatch -> Main | Button | Clicked!")
            toMain()
        }
        s_btn_share.setOnClickListener {
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