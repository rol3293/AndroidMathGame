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

        val timer_text_view = findViewById<TextView>(R.id.timer_text_view)

        val time_seekBar = findViewById<SeekBar>(R.id.time_seekBar)
        time_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 0)
                    timer_text_view.setText("Timer (" + progress * 10 + "s)")
                else
                    timer_text_view.setText("No Timer")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            intent = Intent(this, PlayActivity::class.java).apply {
                putExtra("mode", mode)
                putExtra("table", spinner.selectedItem.toString().toInt())
                putExtra("timer", time_seekBar.progress)
            }
            startActivity(intent)
        }
    }
}