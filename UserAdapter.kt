package com.example.userlistapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userlistapp.R
import com.example.userlistapp.data.User

class UserAdapter(
    private val users: MutableList<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserVH>() {

    inner class UserVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)

        fun bind(user: User) {
            tvName.text = user.name
            tvEmail.text = user.email
            tvPhone.text = user.phone
            itemView.setOnClickListener { onItemClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserVH(view)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun setItems(newItems: List<User>) {
        users.clear()
        users.addAll(newItems)
        notifyDataSetChanged()
    }
}
