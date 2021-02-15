package fr.isen.alzeihmheure.calendar

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.alzeihmheure.R


class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val spinnerRegion = findViewById<View>(R.id.spinnerRegion) as Spinner
        val lRegion = arrayOf("Médecin", "Patient", "Famille")
        val dataAdapterR =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, lRegion)
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRegion.adapter = dataAdapterR

        spinnerRegion.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View,
                position: Int, id: Long
            ) {
                val myRegion = spinnerRegion.selectedItem.toString()
                Toast.makeText(
                    this@CalendarActivity,
                    """
                        OnClickListener : 
                        Spinner 1 : $myRegion
                        """.trimIndent(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }

    }

}