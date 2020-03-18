package com.example.gamematch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList

class GameActivity : AppCompatActivity() {

    var max = 30
    var rightAnswer: Int = 0
    var resultWrongAnswer: Int = 0
    var arrayList = ArrayList<TextView>()
    var maxCountQuestion = 10
    var countOfRightAnswers = 0
    var countOfQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        generateQuestion()
    }

    fun generateQuestion() {

        arrayList.add(tvResult)
        arrayList.add(tvResult1)
        arrayList.add(tvResult2)
        arrayList.add(tvResult3)

        val randomA = Random().nextInt(max) + 1
        val randomB = Random().nextInt(max) + 1
        var randomC = Random().nextInt(4) + 1

        //random set in TextView: tvArithmeticExpression mark
        val mark = when (randomC) {
            1 -> String.format("%s + %s", randomA, randomB)
            2 -> String.format("%s - %s", randomA, randomB)
            3 -> String.format("%s * %s", randomA, randomB)
            else -> String.format("%s / %s", randomA, randomB)
        }
        tvArithmeticExpressions.text = mark

        rightAnswer = when (randomC) {
            1 -> randomA + randomB
            2 -> randomA - randomB
            3 -> randomA * randomB
            else -> randomA / randomB
        }
        //случайно выбираем позицию правильного ответа
        var rightAnswerPosition = Random().nextInt(3) + 1
        Log.d("MyResultRight", "" + rightAnswer)

        //в цикле do while проверяем до тех пор пока resultWrongAnswer не равен rightAnswer
        do {
            resultWrongAnswer = Random().nextInt(max)
            Log.d("MyResultWrong", "" + resultWrongAnswer)

        } while (resultWrongAnswer == rightAnswer)

        for (x in 0 until 4) {
            Log.i("position", "" + rightAnswerPosition)
            //в цикле if проверяем если x равен позицию правильного ответа то добавляем в TextView
            if (x == rightAnswerPosition) {
                arrayList.get(x).setText(Integer.toString(rightAnswer))
                Log.d("rightAnswer","" + rightAnswer)
                Log.d("positionRightAnswer", "" + x)
            } else {
                resultWrongAnswer = Random().nextInt(max) + 1
                arrayList.get(x).setText(Integer.toString(resultWrongAnswer))

                resultWrongAnswer = Random().nextInt(max) + 1
                arrayList.get(x).setText(Integer.toString(resultWrongAnswer))

                    Log.i("resultWrong", "" + resultWrongAnswer)
                    Log.d("positionResultWrong", "" + x)
            }
        }
        var score = String.format("%s/%s",countOfRightAnswers,countOfQuestion)
        countQuestion.text = score
    }

    fun onClick(view: View) {

        val textView = view as TextView
        val pressedText = textView.text.toString().toInt()

        //если нажатый текст равен правильному ответу то выводим тост
        if(pressedText == rightAnswer){
            Toast.makeText(this,"Верно",Toast.LENGTH_SHORT).show()
            countOfRightAnswers++
        }else{
            Toast.makeText(this,"Не верно",Toast.LENGTH_SHORT).show()
        }
        countOfQuestion++

        //если число вопрсов равен maxCountQuestion то переходим ScoreActivity
        if(countOfQuestion == maxCountQuestion){
            intent = Intent(this,ScoreActivity::class.java)
            intent.putExtra("countOfRightsAnswers",countOfRightAnswers)
            intent.putExtra("countOfQuestion",countOfQuestion)
            startActivity(intent)
            finish()
        }

        generateQuestion()
    }
}