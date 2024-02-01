package com.example.mytest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.NO_ID
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.abrantix.roundedvideo.example.MainActivity
import com.example.mytest.floating.FloatingSerivice
import com.example.mytest.imp.OnNavigationStateListener
import com.tencent.mmkv.MMKV
import java.util.*


class MainActivity : AppCompatActivity() {


    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MMKV.defaultMMKV().putString("woShiShui","bluesky")
        MMKV.defaultMMKV().putString("1","bluesky")
        MMKV.defaultMMKV().putString("22","bluesky")
        MMKV.defaultMMKV().putString("444","bluesky")

        findViewById<Button>(R.id.clickme).setOnClickListener{

            val flags: Int = window.attributes.flags
            var hasN = flags and WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS == 0

//            findViewById<Button>(R.id.clickme).text = "${index++}->$hasN"

            val isN = isNavigationBarExist(this)
            findViewById<Button>(R.id.clickme).text = "${index++}->$isN"

            MainActivity.startActivity(this)

        }

        findViewById<Button>(R.id.clickme2).setOnClickListener {


            com.fuusy.floatwindow.MainActivity.startActivity(this)

        }


        testFloating()

    }

    private fun testFloating() {

        val intent = Intent(this,FloatingSerivice::class.java)
        startService(intent)

        findViewById<Button>(R.id.showMyFloat).setOnClickListener {

            val intent = Intent(this,FloatingSerivice::class.java)
            intent.putExtra(FloatingSerivice.ACTION_TYPE,FloatingSerivice.ACTION_ADD_VIEW)
            startService(intent)
        }

        findViewById<Button>(R.id.removeMyFloat).setOnClickListener {

            val intent = Intent(this,FloatingSerivice::class.java)
            intent.putExtra(FloatingSerivice.ACTION_TYPE,FloatingSerivice.ACTION_REMOVE_VIEW)
            startService(intent)
        }


    }


    private val NAVIGATION = "navigationBarBackground"

    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    fun isNavigationBarExist(activity: Activity): Boolean {
        val vp = activity.window.decorView as ViewGroup
        if (vp != null) {
            for (i in 0 until vp.childCount) {
                vp.getChildAt(i).context.packageName
                if (vp.getChildAt(i).id != NO_ID && NAVIGATION == activity.resources.getResourceEntryName(
                        vp.getChildAt(i).id
                    )
                ) {
                    return true
                }
            }
        }
        return false
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

//        hasNavigationBar()




    }


    override fun onResume() {
        super.onResume()



    }

//    fun  isUsingGestureNavigation() : Boolean{
//        val navigationBarHeight = SystemProperties.getInt("qemu.hw.mainkeys", 0);
//        return navigationBarHeight == 0;
//    }



    fun hasNavigationBar(){

        isNavigationBarExist(this,object : OnNavigationStateListener{
            override fun onNavigationState(isShowing: Boolean, height: Int) {

                Log.e("---->","---->687687 isShowing = $isShowing  height = $height")

            }
        })
    }


    fun isNavigationBarExist(
        activity: Activity?,
        onNavigationStateListener: OnNavigationStateListener?
    ) {
        if (activity == null) {
            return
        }
        val height = getNavigationHeight(activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            activity.window.decorView.setOnApplyWindowInsetsListener { v, windowInsets ->
                var isShowing = false
                var b = 0
                if (windowInsets != null) {
                    b = windowInsets.systemWindowInsetBottom
                    isShowing = b == height
                }
                if (onNavigationStateListener != null && b <= height) {
                    onNavigationStateListener.onNavigationState(isShowing, b)
                }
                windowInsets
            }
        }
    }

    fun getNavigationHeight(activity: Context?): Int {
        if (activity == null) {
            return 0
        }
        val resources = activity.resources
        val resourceId = resources.getIdentifier(
            "navigation_bar_height",
            "dimen", "android"
        )
        var height = 0
        if (resourceId > 0) {
            //获取NavigationBar的高度
            height = resources.getDimensionPixelSize(resourceId)
        }
        return height
    }


}