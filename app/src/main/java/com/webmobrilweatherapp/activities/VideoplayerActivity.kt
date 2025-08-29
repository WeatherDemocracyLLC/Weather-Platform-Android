package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.*
import com.webmobrilweatherapp.R



class VideoplayerActivity : AppCompatActivity() {

    var VideoUrl=""
    lateinit var player: ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.webmobrilweatherapp.R.layout.activity_videoplayer)
        var videoPlayer: StyledPlayerView=findViewById(R.id.video_player)

        VideoUrl= getIntent().getExtras()?.getString("Videolink").toString()

       //Toast.makeText(getApplicationContext(),VideoUrl, Toast.LENGTH_SHORT).show();
         player = ExoPlayer.Builder(this).build()
        videoPlayer.setPlayer(player);
        var mediaItem: MediaItem = MediaItem.fromUri(VideoUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady

    }

    override fun onBackPressed() {
        player.stop()
        super.onBackPressed()
    }

    override fun onDestroy() {
        player.stop()
        super.onDestroy()
    }


    override fun onPause() {
        player.stop()
        super.onPause()
    }



}