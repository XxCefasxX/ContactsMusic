package com.example.contentprovider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.contentprovider.databinding.ActivityPlayerBinding
import com.example.contentprovider.service.MusicService

private const val TAG = "PlayerActivity"

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            ivMusicControl.setOnClickListener {

                playMusic()
            }
        }
    }

    private fun playMusic() {
        binding.ivMusicControl.setImageResource(R.drawable.ic_baseline_stop_circle_24)
        Log.d(TAG, "playMusic: playing")
        val play = Intent(this, MusicService::class.java)
        startService(play)
        binding.ivMusicControl.setOnClickListener {

            stopMusic()
        }
    }

    private fun stopMusic() {
        binding.ivMusicControl.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        val play = Intent(this, MusicService::class.java)
        stopService(play)
        binding.ivMusicControl.setOnClickListener {
            playMusic()
        }
    }
}