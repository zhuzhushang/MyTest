package com.example.mytest

import android.app.Application
import com.tencent.mmkv.MMKV

class MyApplication : Application(){

    companion object{

        var appContext : Application? = null


    }



    override fun onCreate() {
        super.onCreate()
        appContext = this

        MMKV.initialize(this)

    }


}