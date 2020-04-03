package com.example.gamematch.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamematch.R
import com.example.gamematch.Utils.Util
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        supportActionBar!!.hide()

        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val maxScore = pref.getInt("max",0)

        val countRightAnswers = intent.getIntExtra(Util.COUNT_OF_RIGHTS_ANSWERS, 0)
        val countOfQuestion = intent.getIntExtra(Util.COUNT_OF_QUESTION, 0)
        if (countRightAnswers == 10) {
            tvYourWin.text = "You win"
        } else { 
            tvYourWin.text = "Game Over"
        }
        tvResultGame.text = "$countRightAnswers / $countOfQuestion"
        tvResultMax.text = "$maxScore / $countOfQuestion"

        btnNewGame.setOnClickListener {
            intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnMainMenu.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}
