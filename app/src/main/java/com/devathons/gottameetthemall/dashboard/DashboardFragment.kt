package com.devathons.gottameetthemall.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devathons.gottameetthemall.databinding.FragmentDashboardBinding
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
    private val viewBinding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[DashboardViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = viewBinding.root

    override fun onResume() {
        super.onResume()
        val currentUser = viewModel.currentUser
        if (currentUser == null) {
            editMyProfile()
            return
        }
        with(viewBinding) {
            displayQrCodeButton.setOnClickListener { QrCodeDialogFragment().show(parentFragmentManager, null) }
            profileImage.setOnClickListener { editMyProfile() }
            profileName.text = "${currentUser.firstName} ${currentUser.lastName}"
            profileJob.text = currentUser.job
        }

        val users = viewModel.users
        val discovered = users.count { it != null }
        val total = users.size
        viewBinding.score.text = "$discovered/$total users discovered:"

        val personsAdapter = UsersAdapter(users) { position ->
            val user = users[position] ?: return@UsersAdapter
            val action = DashboardFragmentDirections.actionShowUserFromDashboard(user)
            findNavController().navigate(action)
        }
        with(viewBinding.personsRecyclerView) {
            layoutManager = LinearLayoutManager(activity)
            adapter = personsAdapter
        }

        viewBinding.goScanButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToScanFragment()
            findNavController().navigate(action)
        }
    }

    private fun editMyProfile() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToProfileFragment()
        findNavController().navigate(action)
    }
}