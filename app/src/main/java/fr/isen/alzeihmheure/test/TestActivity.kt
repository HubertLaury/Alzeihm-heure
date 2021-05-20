package fr.isen.alzeihmheure.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.alzeihmheure.databinding.ActivityTestBinding
import java.util.*

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val randomGenerator = Random()
        val randomInt: Int = randomGenerator.nextInt(9999)
        binding.code.setText("" + randomInt)
    }
}