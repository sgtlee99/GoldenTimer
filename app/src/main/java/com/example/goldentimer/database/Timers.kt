package com.example.goldentimer.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

//알람이름, 메뉴제목, 이미지, 분, 초
@Entity(tableName = "tb_timers")
data class Timers(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val t_title : String?,
    val t_menu : String?,
    val t_img : Bitmap? = null,
    val t_min : Int,
    val t_sec : Int
)