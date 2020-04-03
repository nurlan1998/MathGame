package com.example.gamematch.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.gamematch.R
import com.example.gamematch.Utils.Util
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList

class GameActivity : AppCompatActivity() {

    private var max = 10
    private var rightAnswer: Int = 0
    private var arrayList = ArrayList<TextView>()
    private var countOfRightAnswers = 0
    private var countOfQuestion = 1
    private var rightAnswerPosition = 0

    var timer = object : CountDownTimer(20000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            var seconds = millisUntilFinished / 1000
            var minut = seconds / 60
            seconds %= 60
            countDownTimer.text = "$minut:$seconds"
        }
        override fun onFinish() {
            val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
            val max = pref.getInt("max", 0)
            if (countOfRightAnswers >= max) {
                pref.edit().putInt("max", countOfRightAnswers).apply()
            }
            intent = Intent(applicationContext, ScoreActivity::class.java)
            intent.putExtra(Util.COUNT_OF_RIGHTS_ANSWERS, countOfRightAnswers)
            intent.putExtra(Util.COUNT_OF_QUESTION, countOfQuestion)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val actionbar = supportActionBar
        actionbar!!.title = ""
        supportActionBar!!.setIcon(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        playNext()
        timer.start()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private fun playNext() {
        generateQuestion()
        for (x in 0 until 4) {
            //в цикле if проверяем если x равен позицию правильного ответа то добавляем в TextView
            if (x == rightAnswerPosition) {
                arrayList[x].text = "$rightAnswer"
            } else {
                generateWrongAnswers(arrayList[x])
            }
        }
        var score = "$countOfRightAnswers / $countOfQuestion"
        countQuestion.text = score
    }

    private fun generateQuestion() {

        arrayList.add(tvResult)
        arrayList.add(tvResult1)
        arrayList.add(tvResult2)
        arrayList.add(tvResult3)

        val randomB = Random().nextInt(max) + 1
        var randomA = Random().nextInt(max) + 1

        when (Random().nextInt(4)) {
            0 -> {
                tvArithmeticExpressions.text = "$randomA + $randomB"
                rightAnswer = randomA + randomB
            }
            1 -> {
                tvArithmeticExpressions.text = "$randomA - $randomB"
                rightAnswer = randomA - randomB
            }
            2 -> {
                tvArithmeticExpressions.text = "$randomA * $randomB"
                rightAnswer = randomA * randomB
            }
            3 -> {
                rightAnswer = Random().nextInt(max)
                randomA = (rightAnswer) * (randomB)
                tvArithmeticExpressions.text = "$randomA / $randomB"
            }
        }
        //случайно выбираем позицию правильного ответа
        rightAnswerPosition = Random().nextInt(4)
        Log.d("posRightAnswers", "" + rightAnswerPosition)
    }

    private fun generateWrongAnswers(textView: TextView) {

        when (Random().nextBoolean()) {
            true -> textView.text = (rightAnswer + (Random().nextInt(max) + 1)).toString()
            false -> textView.text = (rightAnswer - (Random().nextInt(max) + 1)).toString()
        }
    }

    fun onClick(view: View) {
            val textView = view as TextView
            val pressedText = textView.text.toString().toInt()

            //если нажатый текст равен правильному ответу то выводим тост
            if (pressedText == rightAnswer) {
                countOfRightAnswers++
                Toast.makeText(this, R.string.true_toast, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.false_toast, Toast.LENGTH_SHORT).show()
            }
            playNext()
            countOfQuestion++
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sign_out -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}