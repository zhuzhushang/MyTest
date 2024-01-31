package com.example.mytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Random

class CountDownActivity : AppCompatActivity(){



    private  var downRecyclerview : RecyclerView? = null
    private var mRandom : Random = Random()
    private var mDownAdapter : DownAdapter? = null
    private var mDownList : MutableList<DownBean> = mutableListOf()


    private fun initData() {

        for(i in 0 until 100){

            val time = mRandom.nextInt(1000) + 500
            val downbean = DownBean(time);
            mDownList.add(downbean)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)

        downRecyclerview = findViewById(R.id.downRecyclerview)
        initData()
        mDownAdapter = DownAdapter(R.layout.item_down,mDownList);

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        downRecyclerview?.layoutManager = layoutManager
        downRecyclerview?.adapter = mDownAdapter

    }
}