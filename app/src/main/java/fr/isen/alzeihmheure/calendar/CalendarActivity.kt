package fr.isen.alzeihmheure.calendar

import android.content.ContentUris
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.widget.TextView
import fr.isen.alzeihmheure.R

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
        i.putExtra("title", "Nouvelle Evenement")
        startActivity(i)
    }

    fun getEventIdList(view: View) {
        // date en milliseconds
        //val CALENDAR_EVENT_TIME = 1546300800000 // 2019-01-01 00:00:00
        //val CALENDAR_EVENT_TIME = 1546400800000 // 2019-01-02 00:00:00
        //val CALENDAR_EVENT_TIME = 1547300800000 // 2019-01-12 00:00:00
        //val CALENDAR_EVENT_TIME = 1556300800000 // 2019-04-26 00:00:00
        //val CALENDAR_EVENT_TIME = 1646300800000 // 2022-03-03 00:00:00
        val CALENDAR_EVENT_TIME = 1619820000000 // 05-01-2021 00:00:00

        // Part 1: URI construction
        val builder = CalendarContract.CONTENT_URI.buildUpon().appendPath("time")
        ContentUris.appendId(builder, CALENDAR_EVENT_TIME)
        val uri = builder.build() // Log: content://com.android.calendar/time/1619820000000

        // Part 2: Set Intent action to Intent.ACTION_VIEW
        val intent = Intent(Intent.ACTION_VIEW)
                .setData(uri)
        startActivity(intent)
    }
}