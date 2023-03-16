package com.sjdev0809.goldentimer.app

import android.app.Application



public class MyApplication : Application() {
//    var isShowOrderBreakdown = false
//        get() {
//            val b = field
//            field = false
//            return b
//        }
//    var isShowBenefitBubble = true // 로그인 후 최초한번만 보임(로그아웃시 다시 false로)
////    private var foregroundDetector: TaxiForegroundDetector? = null
//    var isTestMode = false
//    override fun onCreate() {
//        super.onCreate()
//        LogUtil.init(this, null)
//        myApplicationContext = this
//
//        // Kakao SDK 초기화
////        KakaoSdk.init(this, getString(R.string.kakao_app_key));
//
//        // TAXI
//        foregroundDetector = TaxiForegroundDetector(this@MyApplication)
//        foregroundDetector.addListener(object : Listener() {
//            fun onBecameForeground() {
//                LogUtil.e("Became Foreground")
//            }
//
//            fun onBecameBackground() {
//                LogUtil.e("Became Background")
//            }
//        })
//    }
//
//    override fun onTerminate() {
//        super.onTerminate()
//        foregroundDetector.unregisterCallbacks()
//    }
//
//    companion object {
//        var DEVICE_WIDTH_PX = 0
//        const val FCM_DATA = "FCM_DATA"
//        const val FCM_WRAPPER = "FCM_WRAPPER"
//        var myApplicationContext: MyApplication? = null
//            private set
//        var TAXI_DEVICE_WIDTH_PX = 0
//    }
}