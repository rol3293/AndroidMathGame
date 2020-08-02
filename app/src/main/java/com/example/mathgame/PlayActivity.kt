package com.example.mathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
        println(mode)
        if (mode == 1)
            Addition(table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()

        else if (mode == 2)
            Subtraction(this, table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()
        /*else if (mode == 3)
            Multiplication(table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()
        else if (mode == 4)
            Division(table, timer, findViewById(R.id.equation), option1, option2, option3, option4).showQuestion()*/
        println("finished")
    }
}