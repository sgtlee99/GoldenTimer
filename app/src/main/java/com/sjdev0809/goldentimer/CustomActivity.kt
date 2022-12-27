package com.sjdev0809.goldentimer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sjdev0809.goldentimer.adapter.Menu_Adapter
import com.sjdev0809.goldentimer.database.AppDatabase
import com.sjdev0809.goldentimer.database.Timers
import com.sjdev0809.goldentimer.model.MenuType
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.numberpicker_themes.*
import java.util.*
//


class CustomActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = "TAG_Custom_Activity"
    val adapter = GroupAdapter<GroupieViewHolder>()

    var db: AppDatabase? = null

    var selected: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        //DB초기화
        db = AppDatabase.getInstance(this)

        //제목, 메뉴, 이미지, 시간
        //이미지는 기본 에셋제공하여 선택하는 구조
        //버튼 하나를 선택했을때 다른버튼들의 채도가 낮아지며
        //아래의 메뉴 리스트의 아이템들이 변경된다
        //설정이 완료되면 내부 DB에 저장된다


        //tab 최초상태
        adapter.add(Menu_Adapter("라면"))
        adapter.add(Menu_Adapter("소면"))
        adapter.add(Menu_Adapter("중면"))
        adapter.add(Menu_Adapter("우동"))
        adapter.add(Menu_Adapter("파스타"))

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    "면" -> {
                        adapter.clear()
                        adapter.add(Menu_Adapter("라면"))
                        adapter.add(Menu_Adapter("소면"))
                        adapter.add(Menu_Adapter("중면"))
                        adapter.add(Menu_Adapter("우동"))
                        adapter.add(Menu_Adapter("파스타"))
                    }
                    "튀김" -> {
                        adapter.clear()
                        adapter.add(Menu_Adapter("프라이"))
                    }
                    "굽기" -> {
                        adapter.clear()
                        adapter.add(Menu_Adapter("베이킹"))
                    }
                    "삶기" -> {
                        adapter.clear()
                        adapter.add(Menu_Adapter("완숙"))
                        adapter.add(Menu_Adapter("반숙"))
                    }
                    else -> {
                        adapter.clear()
                        adapter.add(Menu_Adapter("라면"))
                        adapter.add(Menu_Adapter("소면"))
                        adapter.add(Menu_Adapter("중면"))
                        adapter.add(Menu_Adapter("우동"))
                        adapter.add(Menu_Adapter("파스타"))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //Not yet implemented
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //Not yet implemented
            }

        })
        recyclerview_menu.adapter = adapter

        //면을 선택했을때
        adapter.setOnItemClickListener { item, view ->
            selected = (item as Menu_Adapter).menu //선택한 미리보기 이미지
            Log.d(TAG, selected)     //선택한 메뉴가 뭔지 보여주는 LOG

            val menuType = MenuType.findByMenu(selected)

            menu_preview_img.setImageResource(menuType!!.img_resource)

        }
        test_button.setOnClickListener {
            callNumberPickerDialog()
            Log.d(TAG, "dialog 실행")
        }
        //타이머 내용 DB에 저장
        timer_save_button.setOnClickListener {
            val menuType = MenuType.findByMenu(selected)

            //설정내용 재확인
//            ReConfirmDialog()
            var db_alarm_title = alarm_title.text.toString()
            var db_alarm_menu: String = menuType!!.menu_name
            var db_alarm_min = time_set_min.text.toString()
            var db_alarm_sec = time_set_sec.text.toString()
            var db_alarm_img = menu_preview_img.drawable
            //바로 DB에 넣기
            val custom_timer = Timers(
                db_alarm_title,
                db_alarm_menu,
                loadBitmap(menuType.img_resource),
                db_alarm_min,
                db_alarm_sec
            )
            db?.timersDao()?.insert(custom_timer)

            Log.d(TAG, "$db_alarm_title $db_alarm_menu $db_alarm_min $db_alarm_sec")

            //mainactivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            //전 액티비티 지워줌
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
        //뒤로버튼
        back_button.setOnClickListener {
            finish()
        }
    }

    //분, 초를 선택하는 numberPicker
    fun callNumberPickerDialog() {
        val dialog = AlertDialog.Builder(this).create()

        val edialog: LayoutInflater = LayoutInflater.from(this)
        val mView: View = edialog.inflate(R.layout.numberpicker_themes, null)

        val minute: NumberPicker = mView.findViewById(R.id.numberPicker_min)
        val second: NumberPicker = mView.findViewById(R.id.numberPicker_sec)

        val cancel: Button = mView.findViewById<Button>(R.id.btn_settime_no)
        val start: Button = mView.findViewById<Button>(R.id.btn_settime_ok)
        // editText 설정해제
        minute.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        second.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        //최소값 설정
        minute.minValue = 0
        second.minValue = 0

        //최대값 설정
        minute.maxValue = 30
        second.maxValue = 59
        //기본값 설정
        minute.value = 1
        second.value = 0

        //취소버튼
        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }
        start.setOnClickListener {
            Toast.makeText(this, "${minute.value}분 ${second.value}초", Toast.LENGTH_SHORT).show()
            if (minute.value == null) {
                minute.value = 1
            }
            if (second.value == null) {
                second.value = 0
            }
            time_set_min.text = "%02d".format(minute.value)
            time_set_sec.text = "%02d".format(second.value)
            dialog.dismiss()
        }

        dialog.setView(mView)
        dialog.create()
        dialog.show()
        dialog.window!!.setLayout(750, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun loadBitmap(img_resourse: Int): Bitmap? {
        //이미지 리소스를 비트맵으로 변경
        val drawable = getDrawable(img_resourse)
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        return bitmap
    }


    //왜 오버라이드 구현해야겠는지는 모르겠음
    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}

