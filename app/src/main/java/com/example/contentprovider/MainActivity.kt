package com.example.contentprovider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contentprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            btnMusic.setOnClickListener {
                goPlay()
            }
            btnContacts.setOnClickListener {
                goContacts()
            }
        }
    }

    private fun goContacts() {
        val contacts = Intent(this, ContactsActivity::class.java)
        startActivity(contacts)
    }

    private fun goPlay() {
        val playMusic = Intent(this, PlayerActivity::class.java)
        startActivity(playMusic)
    }

}