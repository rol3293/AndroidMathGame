package com.example.mathgame.helpers

import java.io.Serializable

class Equation(
    private val equation: String,
    private val userAnswer: String,
    private val right_answer: String,
    private val answered: Boolean,
    private val userIsCorrect: Boolean
) : Serializable {
    constructor(
        equation: String,
        user_answer: String,
        right_answer: String,
        userIsCorrect: Boolean
    ) : this(equation, user_answer, right_answer, true, userIsCorrect)

    constructor(
        equation: String,
        right_answer: String,
    ) : this(equation, "", right_answer, false, false)

    /**
     * Get the equation
     */
    fun getEquation(): String {
        return equation
    }

    /**
     * Get the user's answer
     */
    fun getUserAnswer(): String {
        return userAnswer
    }

    /**
     * Get the correct answer
     */
    fun getRightAnswer(): String {
        return right_answer
    }

    /**
     * Check if the question was answered or not
     * @return <b>true</b> if this mistake was answered. Otherwise, <b>false</b>
     */
    fun answered(): Boolean {
        return answered
    }

    /**
     * Check if the user's answer is correct
     * @return <b>true</b> if the user's answer is correct. Otherwise, <b>false</b>
     */
    fun userIsCorrect(): Boolean {
        return userIsCorrect
    }
}
