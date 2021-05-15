package fr.isen.alzeihmheure.register

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import fr.isen.alzeihmheure.MainActivity
import fr.isen.alzeihmheure.databinding.ActivityRegisterBinding
import fr.isen.alzeihmheure.login.LoginActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // si on clique sur le bouton 's inscrire' on exécute le code suivant
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            //s'il manque le prenom
            when {
                //si rien est saisie pour le prenom on affiche un message grace à un toast
                TextUtils.isEmpty(binding.firstname.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Veuillez saisir votre prénom",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //s'il manque le nom
                TextUtils.isEmpty(binding.lastname.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Veuillez saisir votre nom",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //s'il manque l'adresse mail
                TextUtils.isEmpty(binding.email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Veuillez saisir votre email",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //si rien est saisie pour le numéro de téléphone on affiche un message grace à un toast
                TextUtils.isEmpty(binding.telephone.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Veuillez saisir votre numéro de téléphone",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //si rien est saisie pour l'adresse on affiche un message grace à un toast
                TextUtils.isEmpty(binding.adresse.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Veuillez saisir votre adresse",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //s'il manque le mot de passe
                TextUtils.isEmpty(binding.password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Veuillez saisir votre mot de passe",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //s'il manque le code
                TextUtils.isEmpty(binding.code.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Veuillez saisir votre mot de passe",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //si rien n'est vide
                else -> {
                    //on l'utilisateur à mit un espace en trop on l'enlève
                    val firstname: String = binding.firstname.text.toString().trim { it <= ' ' }
                    val lastname: String = binding.lastname.text.toString().trim { it <= ' ' }
                    val email: String = binding.email.text.toString().trim { it <= ' ' }
                    val telephone: String = binding.telephone.text.toString().trim { it <= ' ' }
                    val adresse: String = binding.adresse.text.toString().trim { it <= ' ' }
                    val password: String = binding.password.text.toString().trim { it <= ' ' }
                    val randomGenerator = Random()
                    val randomint: Int = randomGenerator.nextInt(9999)
                    val code : String = binding.code.setText(""+randomint).toString().trim { it <= ' ' }

                    //utilisation de firebase
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                            //si l'inscription est validée
                            if (task.isSuccessful) {

                                //inscription utilisateur sur firebase
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                //on prévient l'utilisateur que l'inscription est validée
                                Toast.makeText(
                                        this@RegisterActivity,
                                        "Inscription réussi",
                                        Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                        Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                intent.putExtra("firstname_id", firstname)
                                intent.putExtra("lastname_id", lastname)
                                intent.putExtra("telephone_id", telephone)
                                intent.putExtra("adresse_id", adresse)
                                intent.putExtra("password_id", password)
                                intent.putExtra("code_id", code)
                                startActivity(intent)
                                finish()
                            } else {
                                //si l'inscription n'est pas validée on affiche un message d'erreur
                                Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            }
        }

        val spinner = findViewById<View>(fr.isen.alzeihmheure.R.id.spinner) as Spinner
        val lRegion = arrayOf("Médecin", "Patient", "Famille", "Aide soignant", "Patient")
        val dataAdapterR =
            ArrayAdapter(this, R.layout.simple_spinner_item, lRegion)
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
