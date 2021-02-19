package fr.isen.alzeihmheure.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.databinding.ActivityCalendarBinding
import java.util.*


class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

    }
    fun AddCalendarEvent(view: View) {
        val calendarEvent: Calendar = Calendar.getInstance()
        val i = Intent(Intent.ACTION_EDIT)
        i.type = "vnd.android.cursor.item/event"
        i.putExtra("beginTime", calendarEvent.getTimeInMillis())
        i.putExtra("allDay", true)
        i.putExtra("rule", "FREQ=YEARLY")
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000)
        i.putExtra("title", "Calendar Event")
        startActivity(i)
    }
}