package com.example.goldentimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.goldentimer.database.AppDatabase
import kotlinx.android.synthetic.main.activity_timer.*

class TimerActivity : AppCompatActivity() {

    val TAG = "TAG_Timer_Activity"

    var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        var received_id : Int = 0
        if (intent.hasExtra("timer-id")) {
            received_id = intent.getIntExtra("timer-id", 404)
            db_test_text.text = received_id.toString()
        } else {
            Toast.makeText(this, "있었는데요, 없었습니다", Toast.LENGTH_SHORT).show()
        }

        db = AppDatabase.getInstance(this)

        var set_timer = db!!.timersDao().getById(num = received_id)
        Log.d(TAG, "${set_timer.t_title} ${set_timer.t_menu} ${set_timer.t_img} ${set_timer.t_min} ${set_timer.t_sec}")


    }
}


