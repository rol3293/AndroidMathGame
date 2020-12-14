package com.example.mathgame.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mathgame.R


class CustomAdapter(private val context: Activity, private val equation: Array<Equation>) :
    BaseAdapter() {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // inflate
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_layout, null, true)

        // show the equation
        val question = rowView.findViewById<TextView>(R.id.result_equation)
        question.text = equation[position].getEquation()

        val correctAnswer = rowView.findViewById<TextView>(R.id.correct_answer)

        val userAnswer = rowView.findViewById<TextView>(R.id.user_answer)

        if (equation[position].answered()) {
            userAnswer.text = equation[position].getUserAnswer()
            if (equation[position].userIsCorrect()) {
                userAnswer.setTextColor(Color.parseColor("#ff669900"))
                correctAnswer.visibility = View.GONE
                //rowView.findViewById<TextView>(R.id.textView5).layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                rowView.findViewById<TextView>(R.id.textView6).visibility = View.GONE
                rowView.findViewById<ImageView>(R.id.imageView3).visibility = View.VISIBLE
            } else {
                correctAnswer.text = equation[position].getRightAnswer()
            }
        } // Otherwise, show that it was unanswered
        else {
            // change the weight, size and text
            val param1 = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.7f)
            userAnswer.layoutParams = param1
            userAnswer.textSize = 16f
            userAnswer.text = context.getString(R.string.unanswered)
            // hide "user answer"
            rowView.findViewById<TextView>(R.id.textView5).visibility = View.GONE
            // change the weight and text of textView6
            val textView6 = rowView.findViewById<TextView>(R.id.textView6)
            val param2 = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            textView6.layoutParams = param2
            textView6.text = context.getString(R.string.answer)
            correctAnswer.text = equation[position].getRightAnswer()
        }

        return rowView
    }

    override fun getItem(position: Int): Any {
        return equation[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return equation.count()
    }
}