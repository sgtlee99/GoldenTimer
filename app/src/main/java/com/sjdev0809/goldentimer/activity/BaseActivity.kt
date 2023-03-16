package com.sjdev0809.goldentimer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sjdev0809.goldentimer.app.MyApplication
import com.sjdev0809.goldentimer.app.MyPreferencesManager

open class BaseActivity : AppCompatActivity() {

    protected var myApplication: MyApplication? = null
    protected var myPreferencesManager: MyPreferencesManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initalize()
    }

    private fun initalize() {

    }
}