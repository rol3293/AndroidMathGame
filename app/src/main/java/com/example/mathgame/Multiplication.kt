package com.example.mathgame

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView

class Multiplication (
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
    private var wrong: MutableList<String> = mutableListOf()
    private var answeredQuestions = 0

    fun showQuestion() {
        // if theres is a timer or the timer isn't started, start it
        if (timer != 0 && !timerIsStarted) {
            timerIsStarted = true
            // TODO start timer
        }
        // if showed all 12 question
        if (numbers.size == 0) {
            // TODO start the new activity
            disableButtons()
            showResults()
            return
        }
        // get a random number
        val number = numbers[(0 until numbers.size).random()]
        numbers.remove(number)

        // create question
        val question = "$number × $table"
        val answer = number * table
        equation.text = question

        // set the onclick of button and choose the button with the answer
        val random = (0..3).random()
        when (random) {
            0 -> {
                option1.text = (answer).toString()
                option2.text = (answer + 1).toString()
                option3.text = (answer - 1).toString()
                option4.text = (answer + 2).toString()

                option1.setOnClickListener { rightAnswer() }
                option2.setOnClickListener { wrongAnswer() }
                option3.setOnClickListener { wrongAnswer() }
                option4.setOnClickListener { wrongAnswer() }
            }
            1 -> {
                option1.text = (answer + 1).toString()
                option2.text = (answer).toString()
                option3.text = (answer - 1).toString()
                option4.text = (answer + 2).toString()

                option1.setOnClickListener { wrongAnswer() }
                option2.setOnClickListener { rightAnswer() }
                option3.setOnClickListener { wrongAnswer() }
                option4.setOnClickListener { wrongAnswer() }
            }
            2 -> {
                option1.text = (answer - 1).toString()
                option2.text = (answer + 1).toString()
                option3.text = (answer).toString()
                option4.text = (answer + 2).toString()

                option1.setOnClickListener { wrongAnswer() }
                option2.setOnClickListener { wrongAnswer() }
                option3.setOnClickListener { rightAnswer() }
                option4.setOnClickListener { wrongAnswer() }
            }
            3 -> {
                option1.text = (answer + 2).toString()
                option2.text = (answer + 1).toString()
                option3.text = (answer - 1).toString()
                option4.text = (answer).toString()

                option1.setOnClickListener { wrongAnswer() }
                option2.setOnClickListener { wrongAnswer() }
                option3.setOnClickListener { wrongAnswer() }
                option4.setOnClickListener { rightAnswer() }
            }
        }
    }

    private fun showResults() {
        val intent = Intent(context, ResultActivity::class.java).apply {
            putExtra("wrong", wrong as ArrayList<String>)
            putExtra("table", table)
            putExtra("mode", 2)
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
    private fun wrongAnswer() {
        // Make sound
        answeredQuestions++
        wrong.add(equation.text as String)
        showQuestion()
    }
}

