package com.julive.adapter_demo.video

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.julive.adapter.animators.findFirstCompletelyVisibleItemPosition
import com.julive.adapter.animators.firstAnimation
import com.julive.adapter.core.*
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelVideoTest
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_list.*
import kotlinx.android.synthetic.main.item_test_video_view.view.*
import org.jetbrains.anko.backgroundColorResource


class VideoListActivity : AppCompatActivity() {

    private val mp4_a = "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4";
    private val mp4_b = "http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4";

    private val gsyVideoOptionBuilder = GSYVideoOptionBuilder().apply {

        setIsTouchWiget(false)
        setVideoTitle("title")
        setCacheWithPlay(true)
        setRotateViewAuto(true)
        setLockLand(false)
        setShowFullAnimation(true)
        setReleaseWhenLossAudio(false)

    }

    @SuppressLint("NewApi")
    private val mListAdapter =
        listAdapter {
            (0..100).forEach { _ ->
                add(

                    layoutViewModelDsl(
                        R.layout.item_test_video_view,
                        ModelVideoTest(mp4_b, mp4_a, -1)
                    ) {

                        val player = getView<StandardGSYVideoPlayer>(R.id.player)
                        val title = getView<TextView>(R.id.tv_title)
                        val subTitle = getView<TextView>(R.id.tv_subTitle)

                        player.fullscreenButton.setOnClickListener {
                            player.startWindowFullscreen(itemView.context, false, true)
                        }

                        player.isAutoFullWithSize = true

                        onViewAttachedToWindow {
                            firstAnimation()
                        }

                        onBindViewHolder {

                            val model = getModel<ModelVideoTest>()

                            if (it.isEmpty()) {

                                title.text = "title$adapterPosition"
                                subTitle.text = "sutTitle"

                                gsyVideoOptionBuilder.apply {
                                    if (adapterPosition % 2 == 0) {
                                        setUrl(model?.url)
                                    } else {
                                        setUrl(model?.url2)
                                    }
                                    setPlayPosition(adapterPosition)
                                    setPlayTag(adapterPosition.toString())
                                    setGSYVideoProgressListener { progress, secProgress, currentPosition, duration ->
                                        model?.seekOnStart = currentPosition.toLong()
                                    }
                                    setThumbImageView(ImageView(itemView.context).apply {
                                        backgroundColorResource = R.color.colorAccent
                                        layoutParams = player.layoutParams
                                    })

                                }.build(player)

                            }

                            it.takeIf {
                                it.isNotEmpty() && it[0] == 1
                            }?.also {
                                player.seekOnStart = model?.seekOnStart ?: -1
                                player.startPlayLogic()
                            }

                        }
                    })
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

//        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
//        exo缓存模式，支持m3u8，只支持exo
//        CacheFactory.setCacheManager(ExoPlayerCacheManager::class.java)
        //切换渲染模式
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL)

        mListAdapter.into(rv_list_video)
        var currentPlayingPosition = 0

        rv_list_video.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    SCROLL_STATE_IDLE -> {
                        val position = rv_list_video.findFirstCompletelyVisibleItemPosition()
                        Log.d("ScrollChange", "position:$position")
                        if (currentPlayingPosition != position) {
                            currentPlayingPosition = position
                            mListAdapter.notifyItemChanged(position, 1)
                        }
                    }
                }
            }
        })

        rv_list_video.postDelayed({
            mListAdapter.notifyItemChanged(currentPlayingPosition, 1)
        }, 1000)

    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

}