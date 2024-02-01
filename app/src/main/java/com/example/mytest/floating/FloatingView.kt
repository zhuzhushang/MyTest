package com.example.mytest.floating

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Outline
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.abrantix.roundedvideo.VideoSurfaceView
import com.example.mytest.R

class FloatingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,defaultAttr : Int = 0) : ConstraintLayout(context,attributeSet,defaultAttr){

    private var floatSurfaceView : VideoSurfaceView? = null
    private var close : AppCompatImageView? = null
    private var mMediaPlayer : MediaPlayer? = null

    init {

        LayoutInflater.from(context).inflate(R.layout.view_floating,this,true)

        floatSurfaceView = findViewById(R.id.floatSurfaceView)
        close = findViewById(R.id.close)

        val radius: Int = getResources()
            .getDimensionPixelOffset(R.dimen.corner_radius_video)
        floatSurfaceView?.setCornerRadius(radius.toFloat())

//        floatSurfaceView?.outlineProvider = object : ViewOutlineProvider(){
//
//            override fun getOutline(p0: View, p1: Outline) {
//
//                p1.setRoundRect(0,0,p0.width,p0.height,radius.toFloat())
//            }
//        }
//        floatSurfaceView?.clipToOutline = true



        close?.setOnClickListener {



        }

    }


    override fun onFinishInflate() {
        super.onFinishInflate()



    }

    fun startPlay(){
        mMediaPlayer = MediaPlayer()
        //添加播放视频的路径与配置MediaPlayer
        val fileDescriptor : AssetFileDescriptor = resources.openRawResourceFd(R.raw.test);
        mMediaPlayer?.reset();
        //给mMediaPlayer添加预览的SurfaceHolder，将播放器和SurfaceView关联起来
        mMediaPlayer?.setDataSource(fileDescriptor.fileDescriptor,
            fileDescriptor.startOffset,
            fileDescriptor.length
        );
        mMediaPlayer?.setOnPreparedListener { mp ->
            mp.start()
            floatSurfaceView!!.setVideoAspectRatio(
                mp.videoWidth.toFloat() / mp.videoHeight
                    .toFloat()
            )
        }

        floatSurfaceView!!.setMediaPlayer(mMediaPlayer)
    }

    fun release(){


        mMediaPlayer?.stop()
        mMediaPlayer?.reset()
        mMediaPlayer?.release()
    }

    fun stopPlay() {

        mMediaPlayer?.stop()
        mMediaPlayer?.reset()
        mMediaPlayer?.release()

    }


}