package com.example.mathgame.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mathgame.R

class CustomAdapter(private val context: Activity, private val errors: Array<Mistake>): BaseAdapter() {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // inflate
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_layout, null, true)

        val missedEquation = rowView.findViewById<TextView>(R.id.failed_equation)
        missedEquation.text = errors[position].getEquation()

        val userAnswer = rowView.findViewById<TextView>(R.id.user_answer)
        userAnswer.text = errors[position].getUserAnswer().toString()

        val correctAnswer = rowView.findViewById<TextView>(R.id.correct_answer)
        correctAnswer.text = errors[position].getRightAnswer().toString()

        return rowView
    }

    override fun getItem(position: Int): Any {
        return errors[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return errors.count()
    }
}