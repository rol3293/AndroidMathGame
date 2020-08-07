package com.example.mathgame.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mathgame.Mistake
import com.example.mathgame.R

class CustomAdapter(private val context: Activity, private val errors: Array<Mistake>): BaseAdapter() {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // inflate
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_layout, null, true)

        var missedEquation = rowView.findViewById<TextView>(R.id.failed_equation)
        missedEquation.text = errors[position].getEquation()

        var user_answer = rowView.findViewById<TextView>(R.id.user_answer)
        user_answer.text = errors[position].getUserAnswer().toString()

        var correct_answer = rowView.findViewById<TextView>(R.id.correct_answer)
        correct_answer.text = errors[position].getRightAnswer().toString()

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