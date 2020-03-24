package com.example.gamematch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamematch.R
import com.example.gamematch.Utils.Util
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val countRightAnswers = intent.getIntExtra(Util.COUNT_OF_RIGHTS_ANSWERS, 0)
        val countOfQuestion = intent.getIntExtra(Util.COUNT_OF_QUESTION, 0)
        if (countRightAnswers == GameActivity.MAX_COUNT_QUESTION) {
            tvYourWin.text = "You win"
        } else {
            tvYourWin.text = "Game Over"
        }
        val result = "$countRightAnswers / $countOfQuestion"
        tvResultGame.text = result

        btnNewGame.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnMainMenu.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
