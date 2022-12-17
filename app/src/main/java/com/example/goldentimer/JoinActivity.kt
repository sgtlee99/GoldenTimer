package com.example.goldentimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.goldentimer.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join.*

//회원가입을 하는 액티비티
class JoinActivity : AppCompatActivity() {

    private val TAG = "TAG_Join_Activity"

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = FirebaseAuth.getInstance()

        //회원가입 버튼 / 회원가입 성공시 ShareActivity로 이동 / 이미 회원이 있으면 실패
        join_button.setOnClickListener {

            val email : String = email_area.text.toString()
            val password : String = password_area.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Success!")

                        val uid = FirebaseAuth.getInstance().uid ?: ""
                        val user = User(uid, username.text.toString())
                        //db에 넣음
                        val db = FirebaseFirestore.getInstance().collection("users")
                        db.document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG,"DB성공")
                            }
                            .addOnFailureListener {
                                Log.d(TAG,"DB실패")
                            }

                        toShare()
                    } else {
                        Log.d(TAG, "Fail!")

                    }
                }
        }

    }
    private fun toShare() {
        var intent = Intent(this, ShareActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //전 액티비티 지워줌
        startActivity(intent)
    }
}