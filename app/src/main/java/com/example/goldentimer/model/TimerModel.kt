package com.example.goldentimer.model

import com.example.goldentimer.R

class TimerModel(
    title : String,
    menu : String,
    image : Int,
    time : Int
) {
    constructor() : this("","", R.drawable.android_default_logo,0)
}
