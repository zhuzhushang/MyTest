package com.example.mytest.floating

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.fuusy.floatwindow.FloatWindowManager

class FloatingManager {


    /**
     * 支持TYPE_TOAST悬浮窗的最高API版本
     */
    private val OP_SYSTEM_ALERT_WINDOW = 24
    private var mWindowManager : WindowManager? = null
    private var mFloatingView : FloatingView? = null
    private var mLayoutParams : WindowManager.LayoutParams? = null
    private var isAdded : Boolean = false
    private var context : Context? = null


    constructor(context: Context){

        this.context = context
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mFloatingView = FloatingView(context)
        mFloatingView?.setOnTouchListener(FloatingOnTouchListener())
        mLayoutParams = WindowManager.LayoutParams()
        //屏幕宽度
        val screenWidth: Int = getScreenWidth(context)
        val rect = FloatWindowManager.FloatWindowRect(screenWidth - 400, 0, 400, 600)
        mLayoutParams?.let {

            //设置层级
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                it.type = WindowManager.LayoutParams.TYPE_PHONE
            }
            it.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            it.gravity = Gravity.CENTER_VERTICAL
            it.format = PixelFormat.TRANSLUCENT
            it.x = rect.x
            it.y = rect.y
            it.width = rect.width
            it.height = rect.height
        }
    }



    private fun getScreenWidth(context: Context): Int {
        val metric = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }


    fun addView(){

        mWindowManager?.let {

            try {

                if(!isAdded && checkOverlayPermission(context)){
                    it.addView(mFloatingView,mLayoutParams)
                    mFloatingView?.startPlay()
                    isAdded = true
                }else{

                    Log.e("---->","343434  already added or no permission")
                }

            }catch (e : Exception){

                e.printStackTrace()
            }
        }

    }



    fun removeView(){

        mWindowManager?.let {

            try {

                if(isAdded){

                    mFloatingView?.stopPlay()
                    it.removeView(mFloatingView)
                    isAdded = false
                }else{

                    Log.e("---->","343434  already remove")
                }

            }catch (e : Exception){

                e.printStackTrace()
            }
        }
    }

    fun release(){

        mFloatingView?.release()


    }


    /**
     * 检查悬浮窗权限
     *
     *
     * API <18，默认有悬浮窗权限，不需要处理。无法接收无法接收触摸和按键事件，不需要权限和无法接受触摸事件的源码分析
     * API >= 19 ，可以接收触摸和按键事件
     * API >=23，需要在manifest中申请权限，并在每次需要用到权限的时候检查是否已有该权限，因为用户随时可以取消掉。
     * API >25，TYPE_TOAST 已经被谷歌制裁了，会出现自动消失的情况
     */
    private fun requestPermission(context: Context?, op: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 6.0动态申请悬浮窗权限
            if (!Settings.canDrawOverlays(context)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.parse("package:" + context!!.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                return false
            }
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val manager = context!!.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            return try {
                val method = AppOpsManager::class.java.getDeclaredMethod(
                    "checkOp",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
                AppOpsManager.MODE_ALLOWED == method.invoke(
                    manager,
                    op,
                    Binder.getCallingUid(),
                    context.packageName
                ) as Int
            } catch (e: Exception) {
                false
            }
        }
        return true
    }

    fun checkOverlayPermission(context: Context?): Boolean {
        return requestPermission(context, OP_SYSTEM_ALERT_WINDOW)
    }

    private inner class FloatingOnTouchListener : View.OnTouchListener {
        private var startX = 0
        private var startY = 0
        private var x = 0
        private var y = 0
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                    startX = x
                    startY = y
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    mLayoutParams?.x = mLayoutParams?.x?.plus(movedX)
                    mLayoutParams?.y = mLayoutParams?.y?.plus(movedY)
                    mWindowManager?.updateViewLayout(view, mLayoutParams)
                }
                MotionEvent.ACTION_UP -> if (Math.abs(x - startX) < 5 && Math.abs(y - startY) < 5) { //手指没有滑动视为点击，回到窗口模式
                    //closeFloatWindow(false)
                }
                else -> {
                }
            }
            return true
        }
    }

}