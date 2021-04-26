package fr.isen.alzeihmheure.member

import java.io.Serializable

class User(val lastname: String = "",
           val email: String = "",
           val adresse: String = "",
           val firstname: String = "",
           val telephone: String = "",
           val picture: String = "",
           val password: String = ""): Serializable {
}