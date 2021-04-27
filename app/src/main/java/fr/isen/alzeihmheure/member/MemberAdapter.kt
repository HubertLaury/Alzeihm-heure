package fr.isen.alzeihmheure.member

import android.content.Intent
import android.net.Uri
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.isen.alzeihmheure.R
import fr.isen.alzeihmheure.databinding.UserCellBinding



class MemberAdapter (private val entries: List<User>,
                     private val entryClickListener: (User) -> Unit): RecyclerView.Adapter<MemberAdapter.UserViewHolder>() {

    class UserViewHolder(userBinding: UserCellBinding): RecyclerView.ViewHolder(userBinding.root) {
        val firstname: TextView = userBinding.firstname
        val lastname: TextView = userBinding.lastname
        val telephone: TextView = userBinding.phone
        val mail: TextView = userBinding.email
        val adress: TextView = userBinding.adress
        val layout = userBinding.root

        fun bind(user: User){
            firstname.text = user.firstname
            firstname.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            lastname.text = user.lastname
            telephone.text = user.telephone
            adress.text = user.adresse
            mail.text = user.email

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return  entries.count()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = entries[position]
        holder.layout.setOnClickListener {
            entryClickListener.invoke(user)
        }
        holder.bind(user)
    }
}

