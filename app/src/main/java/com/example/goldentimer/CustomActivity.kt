package com.example.goldentimer

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.goldentimer.adapter.Menu_Adapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer


class CustomActivity : AppCompatActivity(), View.OnClickListener {
    val TAG: String = "[GT]<CustomActivity>"
    val DEFAULT_SELECT: Int = 1
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        //제목, 메뉴, 시간
        //이미지는 기본 에셋제공하여 선택하는 구조
        //버튼 하나를 선택했을때 다른버튼들의 채도가 낮아지며
        //아래의 메뉴 리스트의 아이템들이 변경된다
        //====================================================
        var alarm_title : String = timer_title.text.toString()               //알람제목

        noodle.setOnClickListener(this)
        fry.setOnClickListener(this)
        bake.setOnClickListener(this)
        boil.setOnClickListener(this)

        recyclerview_menu.adapter = adapter
        //면을 선택했을때
        adapter.setOnItemClickListener { item, view ->
            var selected : String = ""
            selected = (item as Menu_Adapter).menu                            //선택한 미리보기 이미지
            Log.d(TAG, selected)     //선택한 메뉴가 뭔지 보여주는 LOG
            //이미지 리소스 하드 코딩
            when (selected) {
                "라면" -> menu_preview_img.setImageResource(R.drawable.ramen)
                "소면" -> menu_preview_img.setImageResource(R.drawable.soba)
                "우동" -> menu_preview_img.setImageResource(R.drawable.udon)
                "파스타" -> menu_preview_img.setImageResource(R.drawable.pasta)
                else -> menu_preview_img.setImageResource(R.drawable.default_image)
            }
        }
        test_button.setOnClickListener {

        }
        //시간을 선택하는 NumberPicker
//        timer_number_picker_min.maxValue = 60
//        timer_number_picker_min.minValue = 0

//        val timer_picker_min_text = timer_number_picker_min
//        val timer_picker_min = timer_number_picker_min.apply {
//            maxValue = 60
//            minValue = 0
//            wrapSelectorWheel = false
//            setOnValueChangedListener {
//                    numberPicker, i, i2 ->
//
//            }
//        }
//        timer_number_picker_sec.maxValue = 59
//        timer_number_picker_sec.minValue = 0

        timer_save_button.setOnClickListener {
            ReConfirmDialog()
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(p0: View?) {
        when (p0?.id) {
            noodle.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("라면"))
                adapter.add(Menu_Adapter("소면"))
                adapter.add(Menu_Adapter("우동"))
                adapter.add(Menu_Adapter("파스타"))
            }
            fry.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("프라이"))
            }
            bake.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("베이킹"))
            }
            boil.id -> {
                adapter.clear()
                adapter.add(Menu_Adapter("완숙"))
                adapter.add(Menu_Adapter("반숙"))
                adapter.add(Menu_Adapter("중면"))
            }
            else -> {
                adapter.add(Menu_Adapter("라면"))
                adapter.add(Menu_Adapter("소면"))
                adapter.add(Menu_Adapter("중면"))
                adapter.add(Menu_Adapter("우동"))
                adapter.add(Menu_Adapter("파스타"))
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun Noodle_slected() {
        noodle.setBackgroundColor(R.color.bright_red)
        fry.setBackgroundColor(R.color.dark_red)
        bake.setBackgroundColor(R.color.dark_red)
        boil.setBackgroundColor(R.color.dark_red)
    }

    @SuppressLint("ResourceAsColor")
    fun Fry_slected() {
        noodle.setBackgroundColor(R.color.dark_red)
        fry.setBackgroundColor(R.color.bright_red)
        bake.setBackgroundColor(R.color.dark_red)
        boil.setBackgroundColor(R.color.dark_red)
    }

    @SuppressLint("ResourceAsColor")
    fun Bake_slected() {
        noodle.setBackgroundColor(R.color.dark_red)
        fry.setBackgroundColor(R.color.dark_red)
        bake.setBackgroundColor(R.color.bright_red)
        boil.setBackgroundColor(R.color.dark_red)
    }

    @SuppressLint("ResourceAsColor")
    fun Boil_slected() {
        noodle.setBackgroundColor(R.color.dark_red)
        fry.setBackgroundColor(R.color.dark_red)
        bake.setBackgroundColor(R.color.dark_red)
        boil.setBackgroundColor(R.color.bright_red)
    }



    fun ReConfirmDialog() {     //설정한 데이터가 맞는지 재확인하는 dialog
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("입력하신 정보가 맞나요?")
        dialog.setMessage("")
        dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
            //mainactivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            //전 액티비티 지워줌
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            //토스트 메시지
            Toast.makeText(this, "타이머가 저장되었습니다", Toast.LENGTH_SHORT).show()
        })
        dialog.show()
    }

}