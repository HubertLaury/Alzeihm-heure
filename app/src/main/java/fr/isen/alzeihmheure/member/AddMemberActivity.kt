package fr.isen.alzeihmheure.member

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.databinding.ActivityAddMemberBinding
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

class AddMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMemberBinding
    private lateinit var btnImport: Button
    private lateinit var btnUpload: Button
    private lateinit var picture: ImageView
    private lateinit var btnAdd: Button
    private var filePath: Uri? = null
    private var format: TextView? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    val RESULT_LOAD_IMG = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        format = findViewById(R.id.format)
        btnImport = findViewById(R.id.btnImport)
        btnUpload = findViewById(R.id.btnUpload)
        picture = findViewById(R.id.picture)
        val editText = findViewById<EditText>(R.id.nom)

        binding.btnImport.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val name: String = editText.getText().toString()
                binding.format.setText(name)
                selectPicture()
            }
        })

        binding.btnUpload.setOnClickListener {
            uploadImage()
        }

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
                 val firebaseDatabase = FirebaseDatabase.getInstance().getReference("users/")
                 val uploadId = firebaseDatabase.push().key
                 val firstname: String = binding.firstname.text.toString()
                 val lastname: String = binding.lastname.text.toString()
                 val adresse: String = binding.adresse.text.toString()
                 val email: String = binding.email.text.toString()
                 val telephone: String = binding.telephone.text.toString()
                 val name: String = binding.nom.text.toString()+"."+GetFileExtension(filePath)
                 val ImageStore = storageReference!!.child("uploads/" + name)

                 ImageStore.downloadUrl.addOnSuccessListener { uri ->
                     //Glide.with(this@AddMemberActivity).load(uri).into(picture)
                     val picture = ImageStore.toString()
                 }
                     // we are use add value event listener method
                 // which is called with database reference.
                 firebaseDatabase.addValueEventListener(object : ValueEventListener {
                     override fun onDataChange(snapshot: DataSnapshot) {
                         // inside the method of on Data change we are setting
                         // our object class to our database reference.
                         // data base reference will sends data to firebase.
                         val user = User(firstname, lastname, email, telephone, adresse, picture)
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

    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("uploads/")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
                }
    }

    private fun uploadImage(){
        if(filePath != null){
            val name: String = binding.nom.text.toString()+"."+GetFileExtension(filePath)
            val ref = storageReference?.child("uploads/" + name)
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> AddMemberActivity@{ task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        Log.d("URL", ref.downloadUrl.toString())
                        throw it
                    }
                }
                return@AddMemberActivity ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                }
            }?.addOnFailureListener{}
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    fun GetFileExtension(uri: Uri?): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    private fun selectPicture() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                picture.setImageBitmap(selectedImage)
                filePath = data?.data

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Une erreur s'est produite", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(applicationContext, "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show()
        }
    }
}

