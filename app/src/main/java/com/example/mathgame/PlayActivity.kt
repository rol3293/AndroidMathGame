package com.example.mathgame

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.mathgame.helpers.CustomCountDown
import com.example.mathgame.helpers.Mistake

class PlayActivity : AppCompatActivity() {


    private var countDown: CustomCountDown?= null
    private var countDownIsRunning = false

    private var table: Int = 0
    private var timer: Long = 0
    private var mode: Int = 0

    private var equation: TextView? = null
    private var option1: Button? = null
    private var option2: Button? = null
    private var option3: Button? = null
    private var option4: Button? = null
    private var next: Button? = null

    // initialize variable for mistakes, score, answered questions, question, answer
    private var numbers: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var mistakes: ArrayList<Mistake> = ArrayList()
    private var score = 0
    private var answeredQuestions = 0
    private var question = ""
    private var answer = 0

    private var animator: ObjectAnimator? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        // get the mode, table and timer time from intent and store in a variable
        mode = intent.getIntExtra("mode", 0)
        table = intent.getIntExtra("table", 0)
        timer = intent.getLongExtra("timer", 0)

        // set value to equation
        equation = findViewById(R.id.equation)

        // assign value to buttons
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        next = findViewById(R.id.show_result)

        // if timer is not 0
        if (timer != 0L) {
            // start the countDown timer
            countDown =
                CustomCountDown(timer * 1000, this)
            countDown!!.startTimer()
            countDownIsRunning = true
            // start animation to progressbar
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.isVisible = true
            animator = ObjectAnimator.ofInt(progressBar, "progress", 10000, 0)
            animator!!.interpolator = LinearInterpolator()
            animator!!.duration = timer * 1000
            animator!!.start()
        }
        showQuestion()
    }


    private fun showQuestion() {
        if (numbers.size == 0) {
            showResults(true)
            return
        }
        // take a number and remove it from list
        val number = numbers[(0 until numbers.size).random()]
        numbers.remove(number)

        // set the question and answer depending the mode
        when (mode) {
            1 -> { // if mode is addition
                question = "$number + $table"
                answer = number + table
            }
            2 -> { // if mode is subtraction
                question = "${table + number} - $table"
                answer = number
            }
            3 -> { // if mode is multiplication
                question = "$number × $table"
                answer = number * table
            }
            4 -> { // if mode is division
                question = "${table * number} ÷ $table"
                answer = number
            }
        }
        // update the text of equation text field to the new question
        equation!!.text = question

        // set the onclick of button and choose the button with the answer
        when ((0..3).random()) {
            0 -> {
                option1!!.text = (answer).toString()
                option2!!.text = (answer + 1).toString()
                option3!!.text = (answer - 1).toString()
                option4!!.text = (answer + 2).toString()

                option1!!.setOnClickListener { rightAnswer() }
                option2!!.setOnClickListener { wrongAnswer(option2!!.text as String) }
                option3!!.setOnClickListener { wrongAnswer(option3!!.text as String) }
                option4!!.setOnClickListener { wrongAnswer(option4!!.text as String) }
            }
            1 -> {
                option1!!.text = (answer + 1).toString()
                option2!!.text = (answer).toString()
                option3!!.text = (answer - 1).toString()
                option4!!.text = (answer + 2).toString()

                option1!!.setOnClickListener { wrongAnswer(option1!!.text as String) }
                option2!!.setOnClickListener { rightAnswer() }
                option3!!.setOnClickListener { wrongAnswer(option3!!.text as String) }
                option4!!.setOnClickListener { wrongAnswer(option4!!.text as String) }
            }
            2 -> {
                option1!!.text = (answer - 1).toString()
                option2!!.text = (answer + 1).toString()
                option3!!.text = (answer).toString()
                option4!!.text = (answer + 2).toString()

                option1!!.setOnClickListener { wrongAnswer(option1!!.text as String) }
                option2!!.setOnClickListener { wrongAnswer(option2!!.text as String) }
                option3!!.setOnClickListener { rightAnswer() }
                option4!!.setOnClickListener { wrongAnswer(option4!!.text as String) }
            }
            3 -> {
                option1!!.text = (answer + 2).toString()
                option2!!.text = (answer + 1).toString()
                option3!!.text = (answer - 1).toString()
                option4!!.text = (answer).toString()

                option1!!.setOnClickListener { wrongAnswer(option1!!.text as String) }
                option2!!.setOnClickListener { wrongAnswer(option2!!.text as String) }
                option3!!.setOnClickListener { wrongAnswer(option3!!.text as String) }
                option4!!.setOnClickListener { rightAnswer() }
            }
        }
    }
    fun showResults(automaticallyChangeActivity: Boolean) {
        disableButtons()
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("score", score)
            putExtra("answered_questions", answeredQuestions)
            putExtra("wrong", mistakes.toTypedArray())
            putExtra("table", table)
            putExtra("mode", mode)
        }
        if (automaticallyChangeActivity) {
            startActivity(intent)
        }
        else {
            next!!.isVisible = true
            next!!.setOnClickListener { showResults(true) }
        }

    }
    private fun disableButtons() {
        option1!!.isEnabled = false
        option2!!.isEnabled = false
        option3!!.isEnabled = false
        option4!!.isEnabled = false
    }
    private fun rightAnswer() {
        score++
        answeredQuestions++
        // Make sound
        showQuestion()
    }
    private fun wrongAnswer(user_answer: String) {
        answeredQuestions++
        mistakes.add(
            Mistake(
                question,
                user_answer.toInt(),
                answer
            )
        )
        //make sound
        showQuestion()
    }
    override fun onPause() {
        super.onPause()
        // if the countDown is running and that there's a timer, stop the timer and animation
        if (countDownIsRunning && countDown != null) {
            countDown!!.stopTimer()
            countDownIsRunning = false
            animator!!.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        // if the countDown is running and that there's a timer, resume the timer and animation
        if (!countDownIsRunning && countDown != null) {
            countDown!!.continueTimer()
            animator!!.resume()
        }
    }
}