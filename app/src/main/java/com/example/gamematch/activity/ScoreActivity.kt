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

        val countRightAnswers = intent.getIntExtra(Util.countOfRightsAnswers,0)
        val countOfQuestion = intent.getIntExtra(Util.countOfQuestion,0)
        if(countRightAnswers == 10){
            tvYourWin.text = "You win"
        }else{
            tvYourWin.text = "Game Over"
        }
        var result = String.format("%s/%s",countRightAnswers,countOfQuestion)
        tvResultGame.text = result

        btnNewGame.setOnClickListener {
            intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        btnMainMenu.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
