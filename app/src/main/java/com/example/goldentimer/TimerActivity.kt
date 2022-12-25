package com.example.goldentimer

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.goldentimer.database.AppDatabase
import com.example.goldentimer.model.Share
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_timer.*
import java.io.ByteArrayOutputStream
import java.util.*

class TimerActivity : AppCompatActivity() {
    //TAG
    val TAG = "TAG_Timer_Activity"

    //DB
    var db: AppDatabase? = null

    //타이머
    private var currentCountDownTimer: CountDownTimer? = null
    //밀리초

    //파이어베이스
    val fb_db = FirebaseFirestore.getInstance()

    private var button_state : Boolean = true

    //

    @SuppressLint( "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //main 에서 선택한 타이머 리스트의 id 값을 intent로 받아옴
        var received_id: Int = 0
        if (intent.hasExtra("timer-id")) {
            received_id = intent.getIntExtra("timer-id", 404)
        } else {
            Toast.makeText(this, "있었는데요, 없었습니다", Toast.LENGTH_SHORT).show()
        }

        db = AppDatabase.getInstance(this)
        //받아온 id를 사용하여 db에서 해당 행을 반환
        var set_timer = db!!.timersDao().getById(num = received_id)
        Log.d(
            TAG,
            "${set_timer.t_title} ${set_timer.t_menu} ${set_timer.t_img} ${set_timer.t_min} ${set_timer.t_sec}"
        )

        //이름추가, 공유 버튼
        name_adding.setOnClickListener {
            Log.d(TAG, "Share Button Click!")

            shareDialog(
                set_timer.t_title,
                set_timer.t_menu,
                set_timer.t_img,
                set_timer.t_min,
                set_timer.t_sec
            )
        }


        //타이머 시작, 정지, 리셋 버튼
//        timer_start.setOnClickListener { startTimer() }
//        timer_pause.setOnClickListener { pauseTimer() }
//        timer_reset.setOnClickListener { resetTimer() }

//        convertTime(set_timer.t_min, set_timer.t_sec)

        //제목, 메뉴, 이미지 받기
        timer_get_title.text = set_timer.t_title
        timer_get_menu.text = set_timer.t_menu
        timer_get_menuimage.setImageBitmap(set_timer.t_img)

        //최초 시간띄우기
        timer_count_min.text = set_timer.t_min
        timer_count_sec.text = set_timer.t_sec

//        timer_count_min.text = "%02d".format(set_timer.t_min)
//        timer_count_sec.text = set_timer.t_sec

        //타이머 코드
        timer_start.setOnClickListener {
            startCountDown(
                convertTime(
                    set_timer.t_min,
                    set_timer.t_sec
                )
            )
        }
        timer_pause.setOnClickListener {
            stopCountDown(
                convertTime(
                    set_timer.t_min,
                    set_timer.t_sec
                )
            )
        }


        //화면 이동 버튼
        t_btn_stopwatch.setOnClickListener {
            toStopwatch()
        }
        t_btn_timerlist.setOnClickListener {
            toMain()
        }

        t_play_stop_btn.setOnClickListener {
            if (button_state==true) {   ///a.k.a 최초상태
                //버튼이 play 상태일때 -> 파랑
                t_play_stop_btn.setImageResource(R.drawable.ic_baseline_stop_24)
                t_play_stop_btn.setBackgroundResource(R.drawable.button_state_red)
                //타이머

                //button state
                button_state=false
            } else {
                //버튼이 stop 상태일때 -> 빨강
                t_play_stop_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                t_play_stop_btn.setBackgroundResource(R.drawable.button_state_blue)
                //타이머

                //button state
                button_state=true
            }
        }

    }

    private fun convertTime(min: String, sec: String): Long {
        //String으로 들어온 시간 형변환
        var c_min = min.toInt()
        var c_sec = sec.toInt()
        //밀리초로 변환
        var m_min: Long = c_min * 60000L
        var m_sec: Long = c_sec * 1000L
        //밀리초 단위로 변환한 수를 반환
        return m_min + m_sec
    }

    //카운트다운 타이머
    private fun createCountDownTimer(initialMills: Long) =
        object : CountDownTimer(initialMills, 1000L) {
            override fun onTick(p0: Long) {
                updateRemainTime(p0)
            }

            override fun onFinish() {
                completeCountDown()
            }

        }

    //카운트다운 끝남
    private fun completeCountDown() {
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
    private fun updateRemainTime(remainMillis: Long) {
        // 총 남은 초
        val remainSeconds = remainMillis / 1000

        // 분만 보여줌, 초만 보여줌
        timer_count_min.text = "%02d".format(remainSeconds / 60)
        timer_count_sec.text = "%02d".format(remainSeconds % 60)

    }

    //카운트 다운 멈춤
    private fun stopCountDown(millitime: Long) {
        currentCountDownTimer = createCountDownTimer(millitime)
        currentCountDownTimer = null
    }

    //카운트 다운 시작
    private fun startCountDown(millitime: Long) {
        currentCountDownTimer = createCountDownTimer(millitime)
        currentCountDownTimer?.start()

        //사운드

    }


    //=====다이얼로그
    private fun shareDialog(title: String, menu: String, image: Bitmap?, min: String, sec: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("공유에 사용할 이름을 입력해주세요")
        val inflater: LayoutInflater = layoutInflater
        builder.setView(inflater.inflate(R.layout.sharedialog_edittext, null))

        var listener = DialogInterface.OnClickListener { dialogInterface, i ->
            var dialog = dialogInterface as AlertDialog
            //공유에 사용할 이름
            var name: EditText? = dialog.findViewById<EditText>(R.id.share_by_name)
            var sh_name: String = name?.text.toString()
            //받아온 데이터
            Log.d(TAG, "$title $menu $image $min $sec")
            Log.d(TAG, "${sh_name}")

            var imageurl : String = BitmaptoString(image)

            val share = Share(
                sh_title = title,
                sh_menu = menu,
                sh_image = imageurl,
                sh_min = min,
                sh_sec = sec,
                sh_name = sh_name
            )
            //firestore
            fb_db.collection("shares")
                .add(share)
                .addOnCompleteListener {
                    Log.d(TAG,"성공")
                    Toast.makeText(this,"업로드 성공!",Toast.LENGTH_SHORT).show()
                    toShare()
                }
                .addOnFailureListener {
                    Log.e(TAG,"실패!")
                }


        }
        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", null)
        builder.show()
    }

    //bitmap to string
    private fun BitmaptoString(bitmap : Bitmap?) : String {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes = stream.toByteArray()
        return Base64.getEncoder().encodeToString(bytes)
    }

    //share activity
    private fun toShare() {
        val intent = Intent(this,ShareActivity::class.java)
        startActivity(intent)
    }
    private fun toMain() {
        val intent = Intent(this,MainActivity::class.java)
        finish()
        startActivity(intent)
    }
    private fun toStopwatch() {
        val intent = Intent(this,StopwatchActivity::class.java)
        finish()
        startActivity(intent)
    }
}


