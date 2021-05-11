package fr.isen.alzeihmheure.member

import com.google.firebase.storage.StorageReference
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

