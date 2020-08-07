package com.example.mathgame.modes

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.example.mathgame.Mistake
import com.example.mathgame.ResultActivity

class Addition(
    private val context: Context,
    private val table: Int,
    private val timer: Int,
    private val equation: TextView,
    private val option1: Button,
    private val option2: Button,
    private val option3: Button,
    private val option4: Button
) {

    private var timerIsStarted = false
    private var numbers: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var score = 0
    private var mistakes: ArrayList<Mistake> = ArrayList()
    private var answeredQuestions = 0

    private var question = ""
    private var answer = 0

    fun showQuestion() {
        if (timer != 0 && !timerIsStarted) {
            timerIsStarted = true
            // TODO start timer
        }
        if (numbers.size == 0) {
            //TODO start the new activity
            disableButtons()
            showResults()
            return
        }
        val number = numbers[(0 until numbers.size).random()]
        numbers.remove(number)

        question = "$number + $table"
        equation.text = question
        answer = number + table

        when ((0..3).random()) {
            0 -> {
                option1.text = (answer).toString()
                option2.text = (answer + 1).toString()
                option3.text = (answer - 1).toString()
                option4.text = (answer + 2).toString()

                option1.setOnClickListener { rightAnswer() }
                option2.setOnClickListener { wrongAnswer(option2.text as String) }
                option3.setOnClickListener { wrongAnswer(option3.text as String) }
                option4.setOnClickListener { wrongAnswer(option4.text as String) }
            }
            1 -> {
                option1.text = (answer + 1).toString()
                option2.text = (answer).toString()
                option3.text = (answer - 1).toString()
                option4.text = (answer + 2).toString()

                option1.setOnClickListener { wrongAnswer(option1.text as String) }
                option2.setOnClickListener { rightAnswer() }
                option3.setOnClickListener { wrongAnswer(option3.text as String) }
                option4.setOnClickListener { wrongAnswer(option4.text as String) }
            }
            2 -> {
                option1.text = (answer - 1).toString()
                option2.text = (answer + 1).toString()
                option3.text = (answer).toString()
                option4.text = (answer + 2).toString()

                option1.setOnClickListener { wrongAnswer(option1.text as String) }
                option2.setOnClickListener { wrongAnswer(option2.text as String) }
                option3.setOnClickListener { rightAnswer() }
                option4.setOnClickListener { wrongAnswer(option4.text as String) }
            }
            3 -> {
                option1.text = (answer + 2).toString()
                option2.text = (answer + 1).toString()
                option3.text = (answer - 1).toString()
                option4.text = (answer).toString()

                option1.setOnClickListener { wrongAnswer(option1.text as String) }
                option2.setOnClickListener { wrongAnswer(option2.text as String) }
                option3.setOnClickListener { wrongAnswer(option3.text as String) }
                option4.setOnClickListener { rightAnswer() }
            }
        }
    }
    private fun showResults() {
        val intent = Intent(context, ResultActivity::class.java).apply {
            putExtra("score", score)
            putExtra("answered_questions", answeredQuestions)
            putExtra("wrong", mistakes.toTypedArray())
            putExtra("table", table)
            putExtra("mode", 3)
        }
        context.startActivity(intent)
    }

    private fun disableButtons() {
        option1.isEnabled = false
        option2.isEnabled = false
        option3.isEnabled = false
        option4.isEnabled = false
    }
    private fun rightAnswer() {
        score++
        answeredQuestions++
        // Make sound
        showQuestion()
    }
    private fun wrongAnswer(user_answer: String) {
        answeredQuestions++
        mistakes.add(Mistake(question, user_answer.toInt(), answer))
        //make sound
        showQuestion()
    }
}