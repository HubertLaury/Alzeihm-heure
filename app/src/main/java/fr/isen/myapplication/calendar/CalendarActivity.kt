package fr.isen.alzeihmheure.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.widget.TextView
import fr.isen.myapplication.R

class CalendarActivity : AppCompatActivity() {
    private lateinit var mCalendarView: CalendarView
    private lateinit var thedate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val incoming = intent
        thedate = findViewById<View>(R.id.date) as TextView
        val date = incoming.getStringExtra("date")
        thedate.setText(date)

        mCalendarView = findViewById<CalendarView>(R.id.viewCalender) as CalendarView
        mCalendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener { CalendarView, year, month, dayOfMonth ->
            val date = "$year/$month/$dayOfMonth"
            val intent = Intent(this@CalendarActivity, CalendarActivity::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
        })

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