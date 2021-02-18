package fr.isen.alzeihmheure.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import fr.isen.alzeihmheure.MainActivity
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{
            when
            {
                TextUtils.isEmpty(binding.email.text.toString().trim{ it <= ' ' }) ->
                {
                    Toast.makeText(this, "Veuillez entrer un email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.password.text.toString().trim{ it <= ' ' }) ->
                {
                    Toast.makeText(this, "Veuillez entrer un Mot de passe", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.code.text.toString().trim { it <= ' ' }) ->
                {
                    Toast.makeText(this, "Veuillez entrer un Mot de passe", Toast.LENGTH_SHORT).show()
                }
                else ->
                {
                    val email : String = binding.email.text.toString().trim{ it <= ' ' }
                    val password : String = binding.password.text.toString().trim{ it <= ' ' }
                    val code : String = binding.code.text.toString().trim{ it <= ' ' }
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful)
                        {
                            Toast.makeText(this, "Vous êtes bien connecté",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this, task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        val spinner = findViewById<View>(fr.isen.alzeihmheure.R.id.spinner) as Spinner
        val lRegion = arrayOf("Médecin", "Patient", "Famille", "Aide soignant", "Patient")
        val dataAdapterR =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, lRegion)
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapterR

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?, view: View,
                    position: Int, id: Long
            ) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
    }
}