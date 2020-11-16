package com.example.mathgame


import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val mode =  intent.getIntExtra("mode", 0)

        val modeTxt = findViewById<TextView>(R.id.modeTxt)
        when (mode) {
            1 -> { // if mode is addition
                modeTxt.text = getText(R.string.mode1)
            }
            2 -> { // if mode is subtraction
                modeTxt.text = getText(R.string.mode2)
            }
            3 -> { // if mode is multiplication
                modeTxt.text = getText(R.string.mode3)
            }
            4 -> { // if mode is division
                modeTxt.text = getText(R.string.mode4)
            }
        }

        val spinner = findViewById<Spinner>(R.id.tables_spinner)
        try {
            val popup = Spinner::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val popupWindow = popup[spinner] as ListPopupWindow
            popupWindow.height = 650
        } catch (e: NoClassDefFoundError) {
        } catch (e: ClassCastException) {
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalAccessException) {}

        val timerTextView = findViewById<TextView>(R.id.timer_text_view)

        val timeSeekbar = findViewById<SeekBar>(R.id.time_seekBar)
        timeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 0) {
                    val text = "Timer (" + progress * 10 + "s)"
                    timerTextView.text = text
                }
                else {
                    timerTextView.text = getString(R.string.no_timer)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            intent = Intent(this, PlayActivity::class.java).apply {
                putExtra("mode", mode)
                putExtra("table", spinner.selectedItem.toString().toInt())
                putExtra("timer", (timeSeekbar.progress * 10).toLong())
            }
            startActivity(intent)
        }
    }
}