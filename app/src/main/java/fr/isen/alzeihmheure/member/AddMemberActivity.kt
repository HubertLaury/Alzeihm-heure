package fr.isen.alzeihmheure.member

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.databinding.ActivityAddMemberBinding
import java.io.FileNotFoundException
import java.io.InputStream


class AddMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMemberBinding
    private lateinit var btnImport: Button
    private lateinit var picture: ImageView
    private lateinit var btnAdd: Button
    val RESULT_LOAD_IMG = 1

    //lateinit var memberInfo: MemberInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnImport = findViewById(fr.isen.alzeihmheure.R.id.btnImport)
        picture = findViewById(fr.isen.alzeihmheure.R.id.picture)
        binding.btnImport.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                intent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        })

        btnAdd = findViewById(R.id.btnAdd)
        binding.btnAdd.setOnClickListener {
            when {
                //si rien est saisie pour le prenom on affiche un message grace à un toast
                TextUtils.isEmpty(binding.firstname.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@AddMemberActivity,
                            "Veuillez saisir votre prénom",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //s'il manque le nom
                TextUtils.isEmpty(binding.lastname.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@AddMemberActivity,
                            "Veuillez saisir votre nom",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //s'il manque l'adresse mail
                TextUtils.isEmpty(binding.email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@AddMemberActivity,
                            "Veuillez saisir votre email",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //si rien est saisie pour le numéro de téléphone on affiche un message grace à un toast
                TextUtils.isEmpty(binding.telephone.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@AddMemberActivity,
                            "Veuillez saisir votre numéro de téléphone",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //si rien est saisie pour l'adresse on affiche un message grace à un toast
                TextUtils.isEmpty(binding.adresse.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@AddMemberActivity,
                            "Veuillez saisir votre adresse",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                //si rien est saisie pour l'image on affiche un message grace à un toast
                TextUtils.isEmpty(binding.picture.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                            this@AddMemberActivity,
                            "Veuillez saisir votre image",
                            Toast.LENGTH_SHORT
                    ).show()
                }
             else -> {
                // else call the method to add
                // data to our database.
                 // getting text from our edittext fields.
                 val firstname: String = binding.firstname.text.toString()
                 val lastname: String = binding.lastname.text.toString()
                 val adresse: String = binding.adresse.text.toString()
                 val email: String = binding.email.text.toString()
                 val telephone: String = binding.telephone.text.toString()
                 //val picture: String = binding.picture.toString()

                 // below 3 lines of code is used to set
                 // data in our object class.
                 val firebaseDatabase = FirebaseDatabase.getInstance().getReference("users")
                 val uploadId = firebaseDatabase.push().key

                 // we are use add value event listener method
                 // which is called with database reference.
                 firebaseDatabase.addValueEventListener(object : ValueEventListener {
                     override fun onDataChange(snapshot: DataSnapshot) {
                         // inside the method of on Data change we are setting
                         // our object class to our database reference.
                         // data base reference will sends data to firebase.
                         val user = User(firstname, lastname, email, telephone, adresse)
                         firebaseDatabase.child(uploadId!!).setValue(user)

                         // after adding this data we are showing toast message.
                         Toast.makeText(this@AddMemberActivity, "data added", Toast.LENGTH_SHORT).show()
                     }

                     override fun onCancelled(error: DatabaseError) {
                         // if the data is not added or it is cancelled then
                         // we are displaying a failure toast message.
                         Toast.makeText(this@AddMemberActivity, "Fail to add data $error", Toast.LENGTH_SHORT).show()
                     }
                 })
            }
            }
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                picture.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Une erreur s'est produite", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(applicationContext, "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show()
        }
    }
}