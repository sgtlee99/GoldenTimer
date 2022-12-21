package com.example.goldentimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*

//로그인을 하는 액티비티
class LoginActivity : AppCompatActivity() {

    private val TAG = "TAG_Login_Activity"

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        login_button.setOnClickListener {
            val email : String = login_email.text.toString()
            val password : String = login_pwd.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        //DB에 데이터 넣어줘야 함

                        toShare()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)

                    }
                }
        }

        to_join_button.setOnClickListener {
            Log.d(TAG,"Login -> Join | Button | Clicked!")
            toJoin()
        }
    }

    private fun toShare() {
        var intent = Intent(this, ShareActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //전 액티비티 지워줌
        startActivity(intent)
    }
    private fun toJoin() {
        var intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }
}