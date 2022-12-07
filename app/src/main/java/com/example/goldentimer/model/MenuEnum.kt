package com.example.goldentimer.model

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.example.goldentimer.R

enum class MenuEnum (var menu_name : String ,var img_resource : Int) {
    DEFAULT("default", R.drawable.default_image),
    //menu - noodle
    RAMEN("라면", R.drawable.ramen),
    //소면 우동 파스타
    SOBA("소면", R.drawable.soba),
    UDON("우동", R.drawable.udon),
    PASTA("파스타", R.drawable.pasta)
    //menu - fry


    //menu - bake

    //menu - boil

}

