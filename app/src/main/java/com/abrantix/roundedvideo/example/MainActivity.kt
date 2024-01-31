package com.abrantix.roundedvideo.example

import android.animation.Animator
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abrantix.roundedvideo.VideoSurfaceView
import com.example.mytest.R
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object{

        fun startActivity(context : Context){

            val intent = Intent(context,MainActivity::class.java)
            context.startActivity(intent)
        }
    }



    private val mVideoSurfaceView: Array<VideoSurfaceView?> = arrayOfNulls<VideoSurfaceView>(3)
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)
        val radius: Int = getResources()
            .getDimensionPixelOffset(R.dimen.corner_radius_video)
        val dataSources = arrayOf(
            "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4",
            "http://wdquan-space.b0.upaiyun.com/VIDEO/2018/11/22/ae0645396048_hls_time10.m3u8",
            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        )
        mVideoSurfaceView[0] = findViewById(R.id.video_surface_view1)
        mVideoSurfaceView[1] = findViewById(R.id.video_surface_view2)
        mVideoSurfaceView[2] = findViewById(R.id.video_surface_view3)
        mVideoSurfaceView[0]?.setCornerRadius(radius.toFloat())
        mVideoSurfaceView[1]?.setCornerRadius(radius.toFloat())
        mVideoSurfaceView[2]?.setCornerRadius(radius.toFloat())



//        for (i in mVideoSurfaceView.indices) {
//            val mediaPlayer = MediaPlayer()
//            val surfaceView: VideoSurfaceView = mVideoSurfaceView[i]!!
//            val dataSource = dataSources[i]
            try {
//                mediaPlayer.setDataSource(dataSource)
//                // the video view will take care of calling prepare and attaching the surface once
//                // it becomes available
//                mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
//                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                    override fun onPrepared(mp: MediaPlayer) {
//                        mp.start()
//                        surfaceView.setVideoAspectRatio(
//                            mp.getVideoWidth().toFloat() / mp.getVideoHeight()
//                                .toFloat()
//                        )
//                    }
//                })
//                surfaceView.setMediaPlayer(mediaPlayer)


                val mediaPlayer1  = MediaPlayer();
                val mediaPlayer2  = MediaPlayer();
                val mediaPlayer3  = MediaPlayer();


                //添加播放视频的路径与配置MediaPlayer
                val fileDescriptor : AssetFileDescriptor = resources.openRawResourceFd(R.raw.test);
                mediaPlayer1.reset();
                //给mMediaPlayer添加预览的SurfaceHolder，将播放器和SurfaceView关联起来
                mediaPlayer1.setDataSource(fileDescriptor.fileDescriptor,
                    fileDescriptor.startOffset,
                    fileDescriptor.length
                );
                mediaPlayer1.setOnPreparedListener { mp ->
                    mp.start()
                    mVideoSurfaceView[0]!!.setVideoAspectRatio(
                        mp.videoWidth.toFloat() / mp.videoHeight
                            .toFloat()
                    )
                }

                mVideoSurfaceView[0]!!.setMediaPlayer(mediaPlayer1)

                mediaPlayer2.reset();
                //给mMediaPlayer添加预览的SurfaceHolder，将播放器和SurfaceView关联起来
                mediaPlayer2.setDataSource(fileDescriptor.fileDescriptor,
                    fileDescriptor.startOffset,
                    fileDescriptor.length
                );
                mediaPlayer2.setOnPreparedListener { mp ->
                    mp.start()
                    mVideoSurfaceView[1]!!.setVideoAspectRatio(
                        mp.videoWidth.toFloat() / mp.videoHeight
                            .toFloat()
                    )
                }
                mVideoSurfaceView[1]!!.setMediaPlayer(mediaPlayer2)

                mediaPlayer3.reset();
                //给mMediaPlayer添加预览的SurfaceHolder，将播放器和SurfaceView关联起来
                mediaPlayer3.setDataSource(fileDescriptor.fileDescriptor,
                    fileDescriptor.startOffset,
                    fileDescriptor.length
                );
                mediaPlayer3.setOnPreparedListener { mp ->
                    mp.start()
                    mVideoSurfaceView[2]!!.setVideoAspectRatio(
                        mp.videoWidth.toFloat() / mp.videoHeight
                            .toFloat()
                    )
                }
                mVideoSurfaceView[2]!!.setMediaPlayer(mediaPlayer3)


            } catch (e: IOException) {
                e.printStackTrace()
//                mediaPlayer.release()
            }
//        }

        // Draw a smooth background gradient that is always changing
        getWindow().getDecorView().setBackgroundDrawable(WickedGradientDrawable())

        // Animate the top surface up and down so we're sure animations work
        mVideoSurfaceView[0]!!.animate()
            .translationY(600f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    val targetY: Float =
                        if (mVideoSurfaceView[0]?.getTranslationY() == 0f) 600f else 0f
                    mVideoSurfaceView[0]!!.animate()
                        .translationY(targetY)
                        .setDuration(1999)
                        .setListener(this)
                        .start()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            .start()
    }
}
