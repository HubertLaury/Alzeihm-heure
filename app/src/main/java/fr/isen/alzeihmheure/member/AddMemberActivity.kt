package fr.isen.alzeihmheure.member

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.StorageReference
import fr.isen.alzeihmheure.databinding.ActivityAddMemberBinding
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*


class AddMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMemberBinding
    private var btnImport: Button? = null
    private var selectedImg: ImageView? = null
    val RESULT_LOAD_IMG = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnImport = findViewById(fr.isen.alzeihmheure.R.id.btnImport)
        selectedImg = findViewById(fr.isen.alzeihmheure.R.id.selectedImg)
        binding.btnImport?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                intent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
            }
        })
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                selectedImg!!.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Une erreur s'est produite", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(applicationContext, "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show()
        }
    }
}