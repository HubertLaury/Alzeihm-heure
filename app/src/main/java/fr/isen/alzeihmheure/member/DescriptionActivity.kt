package fr.isen.alzeihmheure.member

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.calendar.CalendarActivity
import fr.isen.alzeihmheure.databinding.ActivityDescriptionBinding
import fr.isen.alzeihmheure.databinding.ActivityMemberBinding

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding
    var call: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("user") as User
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()
        binding.firstname.setText(user.firstname)
        binding.lastname.setText(user.lastname)
        binding.phone.setText(user.telephone)
        binding.email.setText(user.email)
        binding.adress.setText(user.adresse)
        Glide.with(binding.picture.context).load(user.picture).into(binding.picture)

        call = findViewById<Button>(R.id.call) as Button
        call!!.setOnClickListener { // TODO Auto-generated method stub
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:" + user.telephone)
            )
            startActivity(intent)
        }
    }
}