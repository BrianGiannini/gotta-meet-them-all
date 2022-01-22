package com.devathons.gottameetthemall.dashboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devathons.gottameetthemall.R
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.databinding.PersonLayoutBinding

class UsersAdapter(private val users: List<User?>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PersonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.viewBinding) {
        val user = users[position]
        if (user == null) {
            val context = root.context
            nameTextView.setTextColor(Color.GRAY)
            nameTextView.text = context.getString(R.string.unknown_user)
            jobTextView.setTextColor(Color.GRAY)
            jobTextView.text = context.getString(R.string.scan_user_hint)
        } else {
            nameTextView.text = "${user.firstName} ${user.lastName}"
            jobTextView.text = user.job
        }
    }

    override fun getItemCount(): Int = users.size

    class ViewHolder(val viewBinding: PersonLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root)
}

