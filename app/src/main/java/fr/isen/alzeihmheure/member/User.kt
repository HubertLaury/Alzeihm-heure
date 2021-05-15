package fr.isen.alzeihmheure.member

import com.google.firebase.storage.StorageReference
import java.io.Serializable

public class User(
        val firstname: String = "",
        val lastname: String = "",
        val adresse: String = "",
        val email: String = "",
        val telephone: String = "",
        val picture: String = "",
        val password: String = ""): Serializable {


}

