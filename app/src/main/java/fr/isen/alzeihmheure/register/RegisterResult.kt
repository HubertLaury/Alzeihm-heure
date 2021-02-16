package fr.isen.alzeihmheure.register

import java.io.Serializable

class RegisterResult(val data: User) {}

class User(val id: Int) : Serializable {}