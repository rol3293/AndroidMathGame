package com.example.mathgame.helpers

import android.os.CountDownTimer
import com.example.mathgame.PlayActivity

class CustomCountDown(time: Long, private val playActivity: PlayActivity) {
    // remember milliseconds when pausing
    var msUntilFinished = time
    // create timer variable so it can be used in this class
    private var timer: CountDownTimer ?= null

    // this function starts the timer, pretty self explanatory
    fun startTimer() {
        timer = object : CountDownTimer(msUntilFinished, 1000) {
            override fun onFinish() {
                playActivity.showResults(false)
            }

            override fun onTick(millisUntilFinished: Long) {
                // update how much time is left
                msUntilFinished = millisUntilFinished
                println(msUntilFinished / 1000)
                // TODO update text view
            }

        }
        timer!!.start()
    }
    fun stopTimer() {
        timer!!.cancel()
        println("paused, ${msUntilFinished / 1000} seconds left")
    }
    fun continueTimer() {
        startTimer()
        println("continuing, ${msUntilFinished / 1000} seconds left")
    }
}