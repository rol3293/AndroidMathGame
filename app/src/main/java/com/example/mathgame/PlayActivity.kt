package com.example.mathgame

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mathgame.helpers.CustomCountDown
import com.example.mathgame.helpers.Equation

class PlayActivity : AppCompatActivity() {


    private var countDown: CustomCountDown? = null
    private var countDownIsRunning = false
    private var gameIsFinished = false

    private lateinit var rightSoundEffect: MediaPlayer
    private lateinit var wrongSoundEffect: MediaPlayer

    private var table: Int = 0
    private var timer: Long = 0L
    private var mode: Int = 0

    private lateinit var equation: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button
    private lateinit var next: Button

    // initialize variable for mistakes, score, answered questions, question, answer
    private var numbers: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var equations: ArrayList<Equation> = ArrayList()
    private var score = 0
    private var answeredQuestions = 0
    private var question = ""
    private var answer = 0

    private lateinit var animator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        rightSoundEffect = MediaPlayer.create(this, R.raw.correct_sound_effect)
        wrongSoundEffect = MediaPlayer.create(this, R.raw.wrong_sound_effect)
        // get the mode, table and timer time from intent and store in a variable
        mode = intent.getIntExtra("mode", 0)
        table = intent.getIntExtra("table", 0)
        timer = intent.getLongExtra("timer", 0L)

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
            countDown = CustomCountDown(timer * 1000, this)
            countDown!!.startTimer()
            countDownIsRunning = true
            // start animation to progressbar
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = VISIBLE
            animator = ObjectAnimator.ofInt(progressBar, "progress", 10000, 0)
            animator.interpolator = LinearInterpolator()
            animator.duration = timer * 1000
            animator.start()
        }
        showQuestion()
    }

    /**
     * Show the next question and give one button the right answer
     */
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
        equation.text = question

        // set the onclick of button and choose the button with the answer
        when ((0..3).random()) {
            0 -> {
                option1.text = (answer).toString()
                option2.text = (answer + 1).toString()
                option3.text = (answer - 1).toString()
                option4.text = (answer + 2).toString()

                option1.setOnClickListener { rightAnswer(option1.text as String) }
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
                option2.setOnClickListener { rightAnswer(option2.text as String) }
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
                option3.setOnClickListener { rightAnswer(option3.text as String) }
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
                option4.setOnClickListener { rightAnswer(option4.text as String) }
            }
        }
    }

    /**
     * Prepare to load the next activity
     */
    fun showResults(automaticallyChangeActivity: Boolean) {
        releaseSoundEffects()
        disableButtons()
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("score", score)
            putExtra("answered_questions", answeredQuestions)
            putExtra("wrong", equations.toTypedArray())
            putExtra("table", table)
            putExtra("mode", mode)
            putExtra("timer", timer)
        }
        if (automaticallyChangeActivity) {
            startActivity(intent)
        } else {
            fillUnansweredQuestions()
            next.visibility = VISIBLE
            next.setOnClickListener { showResults(true) }
        }
        if (countDownIsRunning && countDown != null) {
            pauseCountDown()
        }
        gameIsFinished = true
    }

    /**
     * Stop and release the sound effects
     */
    private fun releaseSoundEffects() {
//        if (rightSoundEffect.isPlaying)
//            rightSoundEffect.stop()
        rightSoundEffect.release()
//        if (wrongSoundEffect.isPlaying)
//            wrongSoundEffect.stop()
        wrongSoundEffect.release()
    }

    /**
     * Find all the unanswered equations and add it as mistakes
     */
    private fun fillUnansweredQuestions() {
        // take a number and remove it from list
        println(numbers.size)
        for (number in numbers) {
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
            equations.add(Equation(question, answer.toString()))
        }
    }

    /**
     * Disable all 4 buttons
     */
    private fun disableButtons() {
        option1.isEnabled = false
        option2.isEnabled = false
        option3.isEnabled = false
        option4.isEnabled = false
    }

    /**
     * Add point, play a sound, and show the next equation
     * <br>
     *     Call this function when the user gets the right answer
     */
    private fun rightAnswer(userAnswer: String) {
        answeredQuestions++
        score++
        equations.add(Equation(question, userAnswer, answer.toString(), true))
        // play sound
        rightSoundEffect.start()
        // show the next question
        showQuestion()
    }

    /**
     * play a sound, and show the next equation
     * <br>
     *     Call this function when the user gets the wrong answer
     */
    private fun wrongAnswer(userAnswer: String) {
        answeredQuestions++
        equations.add(Equation(question, userAnswer, answer.toString(), false))
        // play sound
        wrongSoundEffect.start()
        // show the next question
        showQuestion()
    }

    override fun onPause() {
        super.onPause()
        // if the countDown is running and that there's a timer, stop the timer and animation
        if (!gameIsFinished) {
            if (countDownIsRunning && countDown != null) {
                pauseCountDown()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!gameIsFinished) {

            // if the countDown is running and that there's a timer, resume the timer and animation
            if (!countDownIsRunning && countDown != null) {
                continueCountDown()
            }
        }
    }

    /**
     * Continue the countdown timer
     */
    private fun continueCountDown() {
        countDown!!.continueTimer()
        animator.resume()
        countDownIsRunning = true
    }

    /**
     * Pause the countdown timer
     */
    private fun pauseCountDown() {
        countDown!!.stopTimer()
        countDownIsRunning = false
        animator.pause()
    }
}