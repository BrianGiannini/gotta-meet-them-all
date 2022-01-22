package com.devathons.gottameetthemall.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devathons.gottameetthemall.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private val viewBinding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[DashboardViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val users = viewModel.users
        val discovered = users.count { it != null }
        val total = users.size
        viewBinding.score.text = "$discovered/$total users discovered:"

        val personsAdapter = UsersAdapter(users)
        with(viewBinding.personsRecyclerView) {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = personsAdapter
        }

        return viewBinding.root
    }
}