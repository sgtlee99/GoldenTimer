package com.sjdev0809.goldentimer.model

import com.sjdev0809.goldentimer.R

enum class MenuType(var menu_name: String, var img_resource: Int) {

    DEFAULT("default", R.drawable.testtest),
    //menu - noodle
    RAMEN("라면", R.drawable.testtest),
    //소면 우동 파스타
    SOBA("소면", R.drawable.testtest),
    JUNGMYEN("중면",R.drawable.testtest),
    UDON("우동", R.drawable.testtest),
    PASTA("파스타", R.drawable.testtest),
    //menu - fry
    FRY("프라이",R.drawable.testtest),
    //menu - bake
    BAKEING("베이킹",R.drawable.testtest),
    //menu - boil
    HALFBOILED("반숙",R.drawable.testtest),
    HARDBOILED("완숙",R.drawable.testtest);

    companion object {
        private val MENU_CACHE: Map<String, MenuType> = values().associateBy {
            it.menu_name
        }

        //메뉴 이름 string으로 이미지 id를 반환
        fun findByMenu(selected: String) = MENU_CACHE[selected]
    }
}

