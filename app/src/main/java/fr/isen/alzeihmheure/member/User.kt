package fr.isen.alzeihmheure.member

import android.net.Uri
import android.widget.ImageView
import com.google.android.gms.tasks.Task
import java.io.Serializable

public class User(
        val lastname: String = "",
        val email: String = "",
        val adresse: String = "",
        val firstname: String = "",
        val telephone: String = "",
        val picture: String = "",
        val password: String = ""): Serializable {
}

