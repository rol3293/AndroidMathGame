package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addition = findViewById<Button>(R.id.addition)
        addition.setOnClickListener {
            showActivity(1)
        }

        val subtraction = findViewById<Button>(R.id.subtraction)
        subtraction.setOnClickListener{
            showActivity(2)
        }

        val multiplication = findViewById<Button>(R.id.multiplication)
        multiplication.setOnClickListener {
            showActivity(3)
        }

        val division = findViewById<Button>(R.id.division)
        division.setOnClickListener {
            showActivity(4)
        }
    }
    fun showActivity(mode: Int){
        val intent = Intent(this, OptionsActivity::class.java).apply {
            putExtra("mode", mode)
        }
        startActivity(intent)
    }

}
