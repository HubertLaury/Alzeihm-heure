package fr.isen.alzeihmheure.test

import android.R
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import fr.isen.alzeihmheure.databinding.ActivityTestBinding
import java.util.*


class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun AddCalendarEvent(view: View?) {
        val calendarEvent = Calendar.getInstance()
        val i = Intent(Intent.ACTION_EDIT)
        i.type = "vnd.android.cursor.item/event"
        i.data = CalendarContract.Events.CONTENT_URI
        i.putExtra("beginTime", calendarEvent.timeInMillis)
        i.putExtra("allDay", true)
        i.putExtra("rule", "FREQ=YEARLY")
        i.putExtra("endTime", calendarEvent.timeInMillis + 60 * 60 * 1000)
        i.putExtra("title", "Calendar Event")
        startActivity(i)
    }

    fun getEventIdList(view: View) {
        // 7 derniers chiffres pour l'heure
        // 6 premiers chiffres pour date
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