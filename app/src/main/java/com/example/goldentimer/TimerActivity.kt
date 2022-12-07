package com.example.goldentimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.example.goldentimer.database.AppDatabase
import io.reactivex.Completable.timer
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class TimerActivity : AppCompatActivity() {

    val TAG = "TAG_Timer_Activity"
    //DB
    var db: AppDatabase? = null

    //스톱워치 기능에 사용=======
    private var time = 0
    private var timerTask : Timer? = null
    //========================

    //타이머
    private var currentCountDownTimer: CountDownTimer? = null
    //밀리초

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //main 에서 선택한 타이머 리스트의 id 값을 intent로 받아옴
        var received_id : Int = 0
        if (intent.hasExtra("timer-id")) {
            received_id = intent.getIntExtra("timer-id", 404)
        } else {
            Toast.makeText(this, "있었는데요, 없었습니다", Toast.LENGTH_SHORT).show()
        }

        db = AppDatabase.getInstance(this)
        //받아온 id를 사용하여 db에서 해당 행을 반환
        var set_timer = db!!.timersDao().getById(num = received_id)
        Log.d(TAG, "${set_timer.t_title} ${set_timer.t_menu} ${set_timer.t_img} ${set_timer.t_min} ${set_timer.t_sec}")

        //타이머 시작, 정지, 리셋 버튼
//        timer_start.setOnClickListener { startTimer() }
//        timer_pause.setOnClickListener { pauseTimer() }
//        timer_reset.setOnClickListener { resetTimer() }

//        convertTime(set_timer.t_min, set_timer.t_sec)

        //제목, 메뉴, 이미지 받기
        timer_get_title.text = set_timer.t_title
        timer_get_menu.text = set_timer.t_menu
        timer_get_menuimage.setImageBitmap(set_timer.t_img)

        //타이머 코드
        timer_start.setOnClickListener { startCountDown(convertTime(set_timer.t_min, set_timer.t_sec)) }
        timer_pause.setOnClickListener { stopCountDown(convertTime(set_timer.t_min, set_timer.t_sec)) }



    }

    private fun convertTime(min : String, sec : String) : Long {
        //String으로 들어온 시간 형변환
        var c_min = min.toInt()
        var c_sec = sec.toInt()
        //밀리초로 변환
        var m_min : Long = c_min * 60000L
        var m_sec : Long = c_sec * 1000L
        //밀리초 단위로 변환한 수를 반환
        return m_min + m_sec
    }

    //카운트다운 타이머
    private fun createCountDownTimer(initialMills : Long) =
        object : CountDownTimer(initialMills, 1000L) {
            override fun onTick(p0: Long) {
                updateRemainTime(p0)
            }

            override fun onFinish() {
                completeCountDown()
            }

        }
    //카운트다운 끝남
    private fun completeCountDown(){
        updateRemainTime(0)
//        updateSeekBar(0)

//
//        // 끝난 경우
//        // 끝난 벨소리 재생함
////        soundPool.autoPause()
//        bellSoundId?.let {soundId->
//            soundPool.play(soundId, 1F,1F,0,0,1F)
//        }
    }

    //남은시간 업데이트
    private fun updateRemainTime(remainMillis: Long){
        // 총 남은 초
        val remainSeconds = remainMillis/1000

        // 분만 보여줌, 초만 보여줌
        timer_count_min.text = "%02d:".format(remainSeconds/60)
        timer_count_sec.text= "%02d".format(remainSeconds%60)

    }

    //카운트 다운 멈춤
    private fun stopCountDown(millitime : Long) {
        currentCountDownTimer = createCountDownTimer(millitime)
        currentCountDownTimer = null
    }
    //카운트 다운 시작
    private fun startCountDown(millitime : Long) {
        currentCountDownTimer = createCountDownTimer(millitime)
        currentCountDownTimer?.start()

        //사운드

    }
    //===============================================
    //스톱워치 기능으로 변환예정
    private fun startTimer() {      //타이머 시작
        timerTask = timer(period = 10) {
            time++

            var sec = time / 100
            var milli = time % 100
            runOnUiThread {
                timer_count.text = "${sec} : ${milli}"
            }
        }
    }
    private fun pauseTimer() {    //타이머 정지
        timerTask?.cancel()
    }
    private fun resetTimer() {    //타이머 리셋
        timerTask?.cancel()
        time = 0
        timer_count.text = "00 : 00"
    }
}


