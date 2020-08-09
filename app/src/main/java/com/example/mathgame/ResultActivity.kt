package com.example.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.example.mathgame.helpers.CustomAdapter
import com.example.mathgame.helpers.Mistake

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // get and store all errors in variable
        val errors: Array<Mistake> = intent.getSerializableExtra("wrong") as Array<Mistake>
        val score = intent.getIntExtra("score", 0)
        val answered_questtions = intent.getIntExtra("answered_questions", 0)

        val result = findViewById<TextView>(R.id.result_textview)
        val percentage = score * 100 / 12
        result.text = "Score: $score/12 ($percentage%)"


        // if user made mistakes show them
        if (score != 12) {
            // change the adapter of the listview
            val listView = findViewById<ListView>(R.id.error_listview)
            listView.adapter = CustomAdapter(this, errors)
        }
    }
}