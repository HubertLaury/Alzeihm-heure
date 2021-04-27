package fr.isen.alzeihmheure.member

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.alzeihmheure.databinding.ActivityAddMemberBinding
import fr.isen.alzeihmheure.databinding.ActivityDescriptionBinding

class AddMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}