package com.devathons.gottameetthemall.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devathons.gottameetthemall.databinding.PersonLayoutBinding

class PersonsAdapter(private val persons: List<Int>) : RecyclerView.Adapter<PersonsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PersonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        holder.viewBinding.personNameTextView.text = person.toString()
    }

    override fun getItemCount(): Int = persons.size

    class ViewHolder(val viewBinding: PersonLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root)
}

