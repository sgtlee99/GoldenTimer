package com.sjdev0809.goldentimer.activity

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.sjdev0809.goldentimer.adapter.Share_Adapter
import com.sjdev0809.goldentimer.database.AppDatabase
import com.sjdev0809.goldentimer.database.Timers
import com.google.firebase.firestore.FirebaseFirestore
import com.sjdev0809.goldentimer.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : BaseActivity() {

    //tag
    val TAG: String = "TAG_Share_Activity"
    //room db
    var db: AppDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        //DB초기화 (room)
        db = AppDatabase.getInstance(this)

        val adapter = GroupAdapter<GroupieViewHolder>()

        //DB초기화 (firestore)
        val fb_db = FirebaseFirestore.getInstance()

        fb_db.collection("shares")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, document.toString())
                    Log.d(TAG, "FireStore 불러오기 성공!")

                    val title = document.get("sh_title").toString()
                    val menu = document.get("sh_menu").toString()
                    val image = document.get("sh_image").toString()

                    val imageBytes = Base64.decode(image, 0)
                    val set_image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    val min = document.get("sh_min").toString()
                    val sec = document.get("sh_sec").toString()
                    val name = document.get("sh_name").toString()

                    adapter.add(Share_Adapter(title, menu, set_image, min, sec, name))
                    //제목, 메뉴, 사진, 분, 초, 이름
                }
                recyclerview_share.adapter = adapter
            }
            .addOnFailureListener {
                Log.e(TAG, "FireStore 불러오기 실패!")
            }

        //터치하면 DB로 전송
        adapter.setOnItemClickListener { item, view ->
            var ti = (item as Share_Adapter).title
            var me = (item as Share_Adapter).menu
            var im = (item as Share_Adapter).img
            var mi = (item as Share_Adapter).min
            var se = (item as Share_Adapter).sec
            Log.d(TAG, "$ti $me $im $mi $se")

            val download_timer = Timers(
                ti, me, im, mi, se
            )
            db?.timersDao()?.insert(download_timer)
            Toast.makeText(this,"다운로드 성공!",Toast.LENGTH_SHORT).show()
            toMain()

        }

        sh_btn_timerlist.setOnClickListener {
            toMain()
        }
        sh_btn_stopwatch.setOnClickListener {
            toStopwatch()
        }
    }

    private fun toMain() {
        var intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
    private fun toStopwatch() {
        var intent = Intent(this, StopwatchActivity::class.java)
        finish()
        startActivity(intent)
    }
}
