package com.example.practiceform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceform.data.User

class UserAdapter(val context: Context, val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(users[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val userID = itemView?.findViewById<TextView>(R.id.tv_item_UserID)
        val firstName = itemView?.findViewById<TextView>(R.id.tv_item_FirstName)
        val lastName = itemView?.findViewById<TextView>(R.id.tv_item_LastName)

        fun bind(user: User) {
            userID?.text = user.uid.toString()
            firstName?.text = user.firstName.toString()
            lastName?.text = user.lastName.toString()
        }
    }
}