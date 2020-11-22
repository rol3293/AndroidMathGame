package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mathgame.helpers.CustomAdapter
import com.example.mathgame.helpers.Mistake

@Suppress("UNCHECKED_CAST")
class ResultActivity : AppCompatActivity() {

    private var mode = 0
    private var timer = 0L
    private var table = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // get properties of game
        mode = intent.getIntExtra("mode", 0)
        table = intent.getIntExtra("table", 0)
        timer = intent.getLongExtra("timer", 0L)

        // set onclick listeners to buttons
        findViewById<Button>(R.id.replayBtn).setOnClickListener { replay() }
        findViewById<Button>(R.id.changeTableBtn).setOnClickListener { changeTable() }
        findViewById<Button>(R.id.mainMenuBtn).setOnClickListener { mainMenu() }

        // get and store all errors in variable
        val errors: Array<Mistake> = intent.getSerializableExtra("wrong") as Array<Mistake>
        val score = intent.getIntExtra("score", 0)
        val timer = intent.getLongExtra("timer", 0L)
        val answeredQuestions = intent.getIntExtra("answered_questions", 0)

        // set the text of result text view
        val result = findViewById<TextView>(R.id.result_textview)
        val percentage = score * 100 / 12
        val resultText = "Score: $score/12 ($percentage%)"
        result.text = resultText

        // show how many questions are answered
        if (timer != 0L) {
            val answeredQuestionsText = findViewById<TextView>(R.id.answeredQuestionsText)
            answeredQuestionsText.text = getString(R.string.operation_answered, answeredQuestions)
        }


        // if user made mistakes show them
        if (score < 12) {
            findViewById<TextView>(R.id.mistakesText).apply {
                visibility = View.VISIBLE
                text = getString(R.string.mistakes, answeredQuestions-score)
            }
            // change the adapter of the list view
            val listView = findViewById<ListView>(R.id.error_listview)
            listView.visibility = View.VISIBLE
            listView.adapter = CustomAdapter(this, errors)
        }
        else {
            findViewById<ImageView>(R.id.correct_image).visibility = View.VISIBLE
            findViewById<TextView>(R.id.congrats_text).visibility = View.VISIBLE
        }
    }

    private fun replay() {
        val i = Intent(this, PlayActivity::class.java).apply {
            putExtra("mode", mode)
            putExtra("table", table)
            putExtra("timer", timer)
        }
        startActivity(i)
    }

    private fun changeTable() {
        startActivity(Intent(this, OptionsActivity::class.java).apply { putExtra("mode", mode) })
    }

    private fun mainMenu() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onBackPressed() {

    }
}