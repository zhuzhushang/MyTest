package com.fuusy.floatwindow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mytest.R

class MainActivity : AppCompatActivity() {

    companion object{

        fun startActivity(context: Context){

            val intent = Intent(context,MainActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_window)

        findViewById<Button>(R.id.bt_play).setOnClickListener {
            Intent(this,VideoPlayActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}