package com.example.mytest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DownAdapter(var layoutId : Int, var mutableList: MutableList<DownBean>) : RecyclerView.Adapter<DownAdapter.DownViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return DownViewHolder(view)

    }

    override fun getItemCount(): Int {

        return mutableList.size
    }

    override fun onBindViewHolder(holder: DownViewHolder, position: Int) {

        val data = mutableList[position]

        holder.timeDown?.setDowmTime(data.time.toLong())

    }



    public class DownViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var timeDown : DownTextView? = null

        init {

            timeDown = itemView.findViewById(R.id.timeDown)

        }

    }


}