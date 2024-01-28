package com.example.mytest

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet


class  DownTextView @JvmOverloads constructor(context : Context,attr : AttributeSet? = null,defaultStyleAttr : Int = 0) : androidx.appcompat.widget.AppCompatTextView(context,attr,defaultStyleAttr){



    private var downTime : Int = 0
    private var mCountDownTimer : CountDownTimer? = null;


    fun setDowmTime(downtime : Long){

        if(downtime > 0){

            text = ""+downtime


            mCountDownTimer?.cancel()

            mCountDownTimer = object : CountDownTimer(downtime * 1000,1000){

                override fun onTick(millisUntilFinished: Long) {

                    text = ""+(millisUntilFinished / 1000)

                }

                override fun onFinish() {

                    text = "结束";
                }
            }

            mCountDownTimer?.start()
        }else{

            text = "结束"
        }
    }



}