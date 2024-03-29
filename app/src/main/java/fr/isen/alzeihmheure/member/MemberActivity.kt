package fr.isen.alzeihmheure.member

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.databinding.ActivityMemberBinding


class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list: MutableList<User> = mutableListOf()
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (ds in dataSnapshot.children) {
                    list.add(ds.getValue(User::class.java)!!)
                }
                Log.d("success", "nice")
                loadList(list)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.d("fail", "failed")
            }
        })

        val button = findViewById<Button>(R.id.add2)
        button.setOnClickListener {
            val intent = Intent(this, AddMemberActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadList(users: List<User>?)
    {
        users?.let {
            val adapter = MemberAdapter(it) { user ->
                //Log.d("user", "selected dish ${user.lastname}${user.firstname}${user.email}${user.telephone}${user.adresse}${user.picture}")
                val intent = Intent(this, DescriptionActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }
}