package com.example.goldentimer.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

//알람이름, 메뉴제목, 이미지, 분, 초
@Entity(tableName = "tb_timers")
data class Timers(
    var t_title : String,
    var t_menu : String,
    var t_img : Bitmap? = null,
    var t_min : String,
    var t_sec : String
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}