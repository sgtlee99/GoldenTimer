package com.example.goldentimer

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.goldentimer.adapter.Share_Adapter
import com.example.goldentimer.database.Timers
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : AppCompatActivity() {

    var timersList = mutableListOf<Timers>()

    val TAG : String = "TAG_Share_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val adapter = GroupAdapter<GroupieViewHolder>()

        //데이터 불러오기
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

                    val imageBytes = Base64.decode(image,0)
                    val set_image = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)

                    val min = document.get("sh_min").toString()
                    val sec = document.get("sh_sec").toString()
                    val name = document.get("sh_name").toString()

                    adapter.add(Share_Adapter(title,menu,set_image,min,sec,name))
                    //제목, 메뉴, 사진, 분, 초, 이름
                }
                recyclerview_share.adapter = adapter
            }
            .addOnFailureListener {

                Log.e(TAG,"FireStore 불러오기 실패!")

            }
    }
}