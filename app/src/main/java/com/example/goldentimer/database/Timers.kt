package com.example.goldentimer.database

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.goldentimer.R


//알람이름, 메뉴제목, 이미지, 분, 초
@Entity(tableName = "tb_timers")
data class Timers(
    var t_title : String,
    var t_menu : String,
    var t_img : Bitmap? = null,
    var t_min : String,
    var t_sec : String
) {
    constructor() : this("","",null ,"","")

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}
//fun loadBitmap(img_resourse: Int): Bitmap? {
//    //이미지 리소스를 비트맵으로 변경
//    val drawable = getDrawable(img_resourse)
//    val bitmapDrawable = drawable as BitmapDrawable
//    val bitmap = bitmapDrawable.bitmap
//    return bitmap
//}


