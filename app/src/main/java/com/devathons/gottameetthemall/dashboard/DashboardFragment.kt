package com.devathons.gottameetthemall.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devathons.gottameetthemall.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private val viewBinding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val persons = listOf(1, 2, 3, 4, 5)
        val personsAdapter = PersonsAdapter(persons)
        with(viewBinding.personsRecyclerView) {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = personsAdapter
        }

        return viewBinding.root
    }
}