package fr.isen.alzeihmheure.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import fr.isen.alzeihmheure.MainActivity
import fr.isen.alzeihmheure.databinding.ActivityLoginBinding
import android.widget.Toast

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
                else ->
                {
                    val email : String = binding.email.text.toString().trim{ it <= ' ' }
                    val password : String = binding.password.text.toString().trim{ it <= ' ' }
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
    }
}