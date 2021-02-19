package fr.isen.alzeihmheure.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.databinding.ActivityCalendarBinding


class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var simpleCalendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // si on clique sur le bouton 's inscrire' on exécute le code suivant
        binding.btnEvent.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }

        /*val simpleCalendarView =
            findViewById<View>(R.id.simpleCalendarView) as CalendarView // get the reference of CalendarView

        val selectedDate = simpleCalendarView.date // get selected date in milliseconds*/

    }
}