package com.example.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.mathgame.helpers.CustomAdapter
import com.example.mathgame.helpers.Mistake
import org.w3c.dom.Text

@Suppress("UNCHECKED_CAST")
class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // get and store all errors in variable
        val errors: Array<Mistake> = intent.getSerializableExtra("wrong") as Array<Mistake>
        val score = intent.getIntExtra("score", 0)
        // val answeredQuestions = intent.getIntExtra("answered_questions", 0)

        val result = findViewById<TextView>(R.id.result_textview)
        val percentage = score * 100 / 12
        val resultText = "Score: $score/12 ($percentage%)"
        result.text = resultText


        // if user made mistakes show them
        if (score < 12) {
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
}