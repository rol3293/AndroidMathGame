package com.example.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mathgame.modes.Addition
import com.example.mathgame.modes.Division
import com.example.mathgame.modes.Multiplication
import com.example.mathgame.modes.Subtraction

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val mode = intent.getIntExtra("mode", 0)
        val table = intent.getIntExtra("table", 0)
        val timer = intent.getIntExtra("timer", 0)

        val option1 = findViewById<Button>(R.id.option1)
        val option2 = findViewById<Button>(R.id.option2)
        val option3 = findViewById<Button>(R.id.option3)
        val option4 = findViewById<Button>(R.id.option4)

        when (mode) {
            1 -> Addition(this,table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()
            2 -> Subtraction(this,table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()
            3 -> Multiplication(this,table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()
            4 -> Division(this,table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()
        }
    }
}