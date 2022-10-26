package com.android.emovie.application.ui.videotrailer

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.emovie.application.ui.Constants
import com.android.emovie.application.ui.Constants.Companion.APY_KEY_YOUTUBE
import com.android.emovie.application.ui.Constants.Companion.RECOVERY_REQUEST_VIDEO
import com.android.emovie.databinding.FragmentVideoTrailerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class VideoTrailerActivity : YouTubeBaseActivity(),  YouTubePlayer.OnInitializedListener {

    private lateinit var binding: FragmentVideoTrailerBinding
    private lateinit var videoKey: String

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        binding = FragmentVideoTrailerBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding = FragmentVideoTrailerBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        intent?.extras?.getString(Constants.VIDEO_KEY)?.run {
            videoKey = this
            binding.videoViewContent.initialize(APY_KEY_YOUTUBE, this@VideoTrailerActivity)
        }
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer,
        p2: Boolean
    ) {
        youTubePlayer.loadVideo(videoKey)
        youTubePlayer.play()
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        binding.videoError.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_REQUEST_VIDEO) {
            getYouTubePlayerProvider().initialize(APY_KEY_YOUTUBE, this)
        }
    }

    protected fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return binding.videoViewContent
    }
}
