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
    JUNGMYEN("중면"),
    UDON("우동", R.drawable.udon),
    PASTA("파스타", R.drawable.pasta),
    //menu - fry
    FRY("프라이"),
    //menu - bake
    BAKEING("베이킹"),
    //menu - boil
    HALF("반숙"),
    WELLDONE("완숙")
    //
    companion object {
        private val MENU_CACHE : Map<String, MenuEnum> = values().associateBy{
            it.name
        }
    }
    //메뉴 이름 string으로 이미지 id를 반환
    fun findByMenu(selected : String) = MENU_CACHE[selected]
}

