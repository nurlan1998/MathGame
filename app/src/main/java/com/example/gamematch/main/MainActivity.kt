package com.example.gamematch.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamematch.R
import com.example.gamematch.game.GameActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay.setOnClickListener {
            intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        btnOut.setOnClickListener {
            finish()
        }
    }
}
