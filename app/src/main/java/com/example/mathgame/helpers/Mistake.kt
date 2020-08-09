package com.example.mathgame.helpers

import java.io.Serializable

class Mistake (private val equation: String, private val user_answer: Int, private val right_answer: Int) : Serializable{

    fun getEquation(): String {
        return equation
    }
    fun getUserAnswer(): Int {
        return user_answer
    }
    fun getRightAnswer(): Int {
        return right_answer
    }
}
