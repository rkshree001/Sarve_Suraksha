package com.bnet.sarvesuraksha.view.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.viewpager.widget.PagerAdapter
import com.bnet.savresuraksha.R

class ExplorePagerAdapter(private val context: Context) : PagerAdapter() {

    private val videoResources = arrayOf(
        R.raw.sv2,
        R.raw.sv2,
        R.raw.sv2
    )

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view: View
        val videoPath = "android.resource://" + context.packageName + "/" + videoResources[position]

        if (position == 0) {
            view = inflater.inflate(R.layout.health_mp4, container, false)
        } else if (position == 1) {
            view = inflater.inflate(R.layout.travel_mp4, container, false)
        } else {
            view = inflater.inflate(R.layout.term_life_mp4, container, false)
        }

        val videoView = view.findViewById<VideoView>(R.id.videoView)


        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setOnInfoListener { mp, what, extra ->
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {

                    videoView.postDelayed({
                        val mediaController = MediaController(context)
                        mediaController.setMediaPlayer(videoView)
                        mediaController.setAnchorView(videoView)
                        mediaController.visibility = View.GONE
                    }, 100)
                }
                true
            }
        }

        val videoUri = Uri.parse(videoPath)
        videoView.setVideoURI(videoUri)
        videoView.start()

        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return videoResources.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
