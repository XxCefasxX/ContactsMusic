package com.example.contentprovider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contentprovider.databinding.ActivityLoginBinding

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
        Log.d(TAG, "onCreate: ")
        initViews()
    }

    private fun initViews() {
        Log.d(TAG, "initViews: ")
        binding.apply {
            Log.d(TAG, "initViews: apply")
            btnLogin.setOnClickListener {
                Log.d(TAG, "initViews: listener")
                userLogin()
            }
        }
    }

    private fun userLogin() {
        Log.d(TAG, "userLogin: click login")
        if (binding.etEmail.text.trim().toString() != "" && binding.etPassword.text.trim()
                .toString() !== ""
        ){
            val home = Intent(this, MainActivity::class.java)
            startActivity(home)
        }else{
            Toast.makeText(this, "there is empty fields", Toast.LENGTH_SHORT).show()
        }

    }
}