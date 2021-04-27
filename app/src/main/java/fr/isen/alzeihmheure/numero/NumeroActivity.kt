package fr.isen.alzeihmheure.numero

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.isen.alzeihmheure.R

class NumeroActivity : AppCompatActivity() {
    var call: Button? = null
    var call1: Button? = null
    var call2: Button? = null
    var call3: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numero)

            call = findViewById<Button>(R.id.buttonPolice) as Button
            call!!.setOnClickListener { // TODO Auto-generated method stub
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel: 17")
                )
                startActivity(intent)
            }

            call1 = findViewById<Button>(R.id.buttonPompier) as Button
            call1!!.setOnClickListener { // TODO Auto-generated method stub
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel: 18")
                )
                startActivity(intent)
            }

            call2 = findViewById<Button>(R.id.buttonUrgence) as Button
            call2!!.setOnClickListener { // TODO Auto-generated method stub
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel: 112")
                )
                startActivity(intent)
            }

            call3 = findViewById<Button>(R.id.buttonSamu) as Button
            call3!!.setOnClickListener { // TODO Auto-generated method stub
                val intent = Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel: 15")
                )
                startActivity(intent)
            }
        }
    }