package com.example.mathgame

import java.io.Serializable

class Mistake (private val equation: String, private val user_answer: Int, private val right_answer: Int) : Serializable{

    public fun getEquation(): String {
        return equation
    }
    public fun getUserAnswer(): Int {
        return user_answer
    }
    public fun getRightAnswer(): Int {
        return right_answer
    }
}
