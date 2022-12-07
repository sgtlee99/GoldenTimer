package com.example.goldentimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_stopwatch.*
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.activity_timer.timer_count
import java.util.*
import kotlin.concurrent.timer

class StopwatchActivity : AppCompatActivity() {
    //TAG
    private val TAG = "TAG_Stopwatch_Activity"
    //스톱워치에 사용
    private var time = 0
    private var timerTask : Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        //타이머 시작, 정지, 리셋 버튼
        s_timer_start.setOnClickListener { startTimer() }
        s_timer_pause.setOnClickListener { pauseTimer() }
        s_timer_reset.setOnClickListener { resetTimer() }

        s_btn_timerlist.setOnClickListener {
            //메인으로 이동
            Log.d(TAG,"Stopwatch -> Main | Button | Clicked!")
            toMain()
        }
        s_btn_more.setOnClickListener {
            //더보기 -> firebase 연동
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
    private fun recodeTimer() {     //타이머 기록

    }
    private fun toMain() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}