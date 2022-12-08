package com.example.goldentimer.model

import com.example.goldentimer.R

enum class MenuType(var menu_name: String, var img_resource: Int) {
    DEFAULT("default", R.drawable.default_image),
    //menu - noodle
    RAMEN("라면", R.drawable.ramen),
    //소면 우동 파스타
    SOBA("소면", R.drawable.soba),
    JUNGMYEN("중면",R.drawable.hot_noodles),
    UDON("우동", R.drawable.udon),
    PASTA("파스타", R.drawable.pasta),
    //menu - fry
    FRY("프라이",R.drawable.fried_chicken),
    //menu - bake
    BAKEING("베이킹",R.drawable.bread),
    //menu - boil
    HALFBOILED("반숙",R.drawable.half_boiled_egg),
    HARDBOILED("완숙",R.drawable.hard_boiled_egg);
    //
    companion object {
        private val MENU_CACHE: Map<String, MenuType> = values().associateBy {
            it.menu_name
        }

        //메뉴 이름 string으로 이미지 id를 반환
        fun findByMenu(selected: String) = MENU_CACHE[selected]
    }
}

