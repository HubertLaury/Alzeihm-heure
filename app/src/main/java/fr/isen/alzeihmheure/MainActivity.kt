package fr.isen.alzeihmheure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import fr.isen.alzeihmheure.calendar.CalendarActivity
import fr.isen.alzeihmheure.map.MapActivity
import fr.isen.alzeihmheure.member.MemberActivity
import fr.isen.alzeihmheure.numero.NumeroActivity

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
            val intent = Intent(this, MapActivity::class.java)
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