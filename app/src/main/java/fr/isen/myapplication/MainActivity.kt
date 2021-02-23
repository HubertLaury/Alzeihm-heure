package fr.isen.alzeihmheure

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import fr.isen.alzeihmheure.calendar.CalendarActivity
import fr.isen.alzeihmheure.map.MapsActivity
import fr.isen.alzeihmheure.member.MemberActivity
import fr.isen.alzeihmheure.numero.NumeroActivity
import fr.isen.myapplication.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.calendrier)
        button.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.trajet)
        button1.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.membres)
        button2.setOnClickListener {
            val intent = Intent(this, MemberActivity::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.urgence)
        button3.setOnClickListener {
            val intent = Intent(this, NumeroActivity::class.java)
            startActivity(intent)
        }
    }
}