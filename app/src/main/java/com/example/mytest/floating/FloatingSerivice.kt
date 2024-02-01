package com.example.mytest.floating

import android.app.Service
import android.content.Intent
import android.os.IBinder

class FloatingSerivice : Service(){

    companion object{

        const val ACTION_TYPE = "action_type"
        const val ACTION_ADD_VIEW = "action_add_view"
        const val ACTION_REMOVE_VIEW = "action_remove_view"

    }


    private var mFloatingManager : FloatingManager? = null

    override fun onCreate() {
        super.onCreate()

        mFloatingManager = FloatingManager(this)

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var action = intent?.getStringExtra(ACTION_TYPE)
        if(action == ACTION_ADD_VIEW){

            mFloatingManager?.addView()

        }else if(action == ACTION_REMOVE_VIEW){


            mFloatingManager?.removeView()
        }


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        mFloatingManager?.release()
        mFloatingManager = null
    }


    override fun onBind(p0: Intent?): IBinder? {

        return null
    }
}