package com.example.goldentimer.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

//알람이름, 메뉴제목, 이미지, 분, 초
@Entity(tableName = "tb_timers")
data class Timers(
    val t_title : String = "title",
    val t_menu : String = "menu",
    val t_img : Bitmap? = null,
    val t_min : Int = 0,
    val t_sec : Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
}