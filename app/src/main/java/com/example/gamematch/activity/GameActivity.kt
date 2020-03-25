package com.example.gamematch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.gamematch.R
import com.example.gamematch.Utils.Util
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList

class GameActivity : AppCompatActivity() {

    companion object {
        const val MAX_COUNT_QUESTION = 10
    }

    private var max = 10
    private var rightAnswer: Int = 0
    private var resultWrongAnswer: Int = 0
    private var arrayList = ArrayList<TextView>()
    private var countOfRightAnswers = 0
    private var countOfQuestion = 0
    private var rightAnswerPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        generateQuestion()
    }

    private fun generateQuestion() {

        arrayList.add(tvResult)
        arrayList.add(tvResult1)
        arrayList.add(tvResult2)
        arrayList.add(tvResult3)

        val randomB = Random().nextInt(max)
        val randomA = Random().nextInt(max) * randomB
        val randomC = Random().nextInt(4) + 1

        //random set in TextView: tvArithmeticExpression mark
        val mark = when (randomC) {
            1 -> "$randomA + $randomB"
            2 -> "$randomA - $randomB"
            3 -> "$randomA * $randomB"
            else -> "$randomA / $randomB"
        }
        tvArithmeticExpressions.text = mark

        rightAnswer = when (randomC) {
            1 -> randomA + randomB
            2 -> randomA - randomB
            3 -> randomA * randomB
            else -> randomA / randomB
        }
        //случайно выбираем позицию правильного ответа
        rightAnswerPosition = Random().nextInt(4)
        Log.d("posRightAnswers", "" + rightAnswerPosition)

        for (x in 0 until 4) {
            //в цикле if проверяем если x равен позицию правильного ответа то добавляем в TextView
            if (x == rightAnswerPosition) {
                arrayList[x].text = "$rightAnswer"
                Log.d("positionRightAnswer", "" + x)
            } else {

                resultWrongAnswer = Random().nextInt(max) + rightAnswer
                Log.d("resultWrongAnswer", "" + resultWrongAnswer)

                arrayList[x].text = "$resultWrongAnswer"
                Log.i("positionWrongAnswer", "" + x)
            }
        }
        var score = "$countOfRightAnswers / $countOfQuestion"
        countQuestion.text = score
    }

    fun onClick(view: View) {

        val textView = view as TextView
        val pressedText = textView.text.toString().toInt()

        //если нажатый текст равен правильному ответу то выводим тост
        if (pressedText == rightAnswer) {
            Toast.makeText(this, R.string.true_toast, Toast.LENGTH_SHORT).show()
            countOfRightAnswers++
        } else {
            Toast.makeText(this, R.string.false_toast, Toast.LENGTH_SHORT).show()
        }
        countOfQuestion++

        //если число вопрсов равен maxCountQuestion то переходим ScoreActivity
        if (countOfQuestion == MAX_COUNT_QUESTION) {
            intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra(Util.COUNT_OF_RIGHTS_ANSWERS, countOfRightAnswers)
            intent.putExtra(Util.COUNT_OF_QUESTION, countOfQuestion)
            startActivity(intent)
            finish()
        }

        generateQuestion()
    }
}